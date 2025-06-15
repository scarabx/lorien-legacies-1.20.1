package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.item.DiamondDaggerItem;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    @Final
    public PlayerScreenHandler playerScreenHandler;

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            at = @At("HEAD"), cancellable = true)
    private void preventItemDrop(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<?> cir) {
        PlayerEntity player = (PlayerEntity)(Object) this;

        // Only prevent drop if it's a Diamond Dagger with WristWrap enabled
        if (stack.getItem() instanceof DiamondDaggerItem dagger && dagger.isWristWrapped(stack)) {
            if (!player.getWorld().isClient) {
                ItemStack currentStack = stack.copy();

                // Try restoring it to the selected hotbar slot
                int selectedSlot = player.getInventory().selectedSlot;
                ItemStack existing = player.getInventory().getStack(selectedSlot);

                if (existing.isEmpty()) {
                    player.getInventory().setStack(selectedSlot, currentStack);
                } else {
                    // If the slot is occupied, try inserting into inventory
                    if (!player.getInventory().insertStack(currentStack)) {
                        // If all else fails, drop it
                        player.dropItem(currentStack, false, retainOwnership);
                    }
                }
            }

            cir.setReturnValue(null);
            cir.cancel();
        }
    }

    // Stress gain when damaged by hostile mobs
    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamagedByHostile(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ServerPlayerEntity player) {
            if (source.getAttacker() instanceof LivingEntity attacker && attacker instanceof net.minecraft.entity.mob.HostileEntity) {
                int currentStress = LegacyBestowalHandler.getStress(player);
                LegacyBestowalHandler.setStress(player, currentStress + 1);
            }
        }
    }

    // Stress gain when attacking hostile mobs
    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttackHostile(Entity target, CallbackInfo ci) {
        if ((Object) this instanceof ServerPlayerEntity player) {
            if (target instanceof net.minecraft.entity.mob.HostileEntity) {
                int currentStress = LegacyBestowalHandler.getStress(player);
                LegacyBestowalHandler.setStress(player, currentStress + 1);
            }
        }
    }

    // Intangibility and Impenetrable Skin damage immunity logic
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        boolean inhibited = player.hasStatusEffect(ModEffects.TIRED) || player.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION) || player.hasStatusEffect(ModEffects.PONDUS_COOLDOWN);

        boolean hasIntangibility = player.hasStatusEffect(ModEffects.PONDUS)
                && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)
                && !inhibited;

        boolean hasTCTIntangibility = player.hasStatusEffect(ModEffects.TACTILE_CONSCIOUSNESS_TRANSFER)
                && player.hasStatusEffect(ModEffects.ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER)
                && !inhibited;

        if ((hasIntangibility || hasTCTIntangibility) && !source.isOf(DamageTypes.GENERIC_KILL)) {
            cir.setReturnValue(false);
            return;
        }

        if (player.hasStatusEffect(ModEffects.PONDUS)
                && player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)
                && !inhibited) {

            boolean allowed = source.isOf(DamageTypes.OUT_OF_WORLD)
                    || source.isOf(DamageTypes.STARVE)
                    || source.isOf(DamageTypes.GENERIC_KILL)
                    || source.isOf(DamageTypes.MAGIC)
                    || source.isOf(DamageTypes.WITHER)
                    || source.isOf(DamageTypes.DRAGON_BREATH)
                    || source.isOf(DamageTypes.SONIC_BOOM)
                    || source.isOf(DamageTypes.IN_WALL)
                    || source.isOf(DamageTypes.THORNS)
                    || source.isOf(DamageTypes.DROWN);

            if (!allowed) {
                cir.setReturnValue(false);
            }
        }
    }

    // Simulate Avex fall flying without Elytra
    @Inject(method = "tick", at = @At("HEAD"))
    private void simulateFallFlyingWithoutElytra(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.AVEX)) {
            if (!player.isFallFlying() && !player.isOnGround() && player.getVelocity().y > 0 && player.isSneaking()) {
                player.startFallFlying();
            }
        }
    }
}
