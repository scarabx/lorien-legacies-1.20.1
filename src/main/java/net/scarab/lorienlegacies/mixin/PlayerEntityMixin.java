package net.scarab.lorienlegacies.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.item.DiamondDaggerItem;
import net.scarab.lorienlegacies.item.ModItems;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;

import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            at = @At("HEAD"), cancellable = true)
    private void preventItemDrop(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<?> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

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

        // NEW: Check for Red Shield item in hand
        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();
        boolean hasRedShield = mainHand.getItem() == ModItems.RED_SHIELD || offHand.getItem() == ModItems.RED_SHIELD;

        if (hasRedShield) {

            // Block damage from mobs from any direction
            if (source.getAttacker() != null) {

                cir.setReturnValue(false);

                return;

            }

            // For projectiles: Check if it's a projectile source
            if (source.getSource() != null && source.getSource() instanceof ProjectileEntity) {

                cir.setReturnValue(false);

                return;

            }

        }

        boolean inhibited = player.hasStatusEffect(ModEffects.TIRED) || player.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION) || player.hasStatusEffect(ModEffects.PONDUS_COOLDOWN);

        boolean hasIntangibility = player.hasStatusEffect(ModEffects.PONDUS)
                && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)
                && !inhibited;

        if ((hasIntangibility) && !source.isOf(DamageTypes.GENERIC_KILL)) {
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

    @Inject(method = "tick", at = @At("HEAD"))
    private void avexFlight(CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.AVEX) && !player.hasStatusEffect(ModEffects.FLOAT)) {

            if (!player.hasStatusEffect(ModEffects.TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                if (!player.isOnGround() && player.isSneaking()) {

                    ClientPlayNetworking.send(LorienLegaciesModNetworking.START_AVEX_FLIGHT_PACKET, PacketByteBufs.empty());

                    if (!player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {

                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false ,false));

                    }
                }

                if (player.isFallFlying()) {

                    Vec3d look = player.getRotationVec(1.0F);

                    Vec3d boosted = player.getVelocity().add(look.multiply(0.05));

                    double maxSpeed = 1.5;

                    if (boosted.length() > maxSpeed) {

                        boosted = boosted.normalize().multiply(maxSpeed);

                    }

                    player.setVelocity(boosted);

                    if (player.isOnGround()) {

                        player.stopFallFlying();

                        if (player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {

                            player.removeStatusEffect(StatusEffects.SLOW_FALLING);

                        }
                    }
                }

                if (player.isOnGround() && player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {

                    player.removeStatusEffect(StatusEffects.SLOW_FALLING);

                }
            }
        }
    }
}
