package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.server.network.ServerPlayerEntity;

import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.item.ModItems;
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

    @Shadow @Final
    public PlayerScreenHandler playerScreenHandler;

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

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

    // Cancel diamond dagger attack if cooldown active
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void cancelDaggerAttackIfCooldownActive(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        ItemStack mainHandStack = player.getStackInHand(Hand.MAIN_HAND);

        if (mainHandStack.getItem() == ModItems.DIAMOND_DAGGER &&
                player.getItemCooldownManager().isCoolingDown(mainHandStack.getItem())) {
            ci.cancel();
        }
    }

    // Intangibility and Impenetrable Skin damage immunity logic
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        boolean inhibited = player.hasStatusEffect(ModEffects.TIRED) || player.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION);

        if (player.hasStatusEffect(ModEffects.PONDUS) && !inhibited) {
            if (player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {
                if (!source.isOf(DamageTypes.GENERIC_KILL)) {
                    cir.setReturnValue(false);
                    return;
                }
            }

            if (player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {
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
    }

    // Simulate Avex fall flying without Elytra
    @Inject(method = "tick", at = @At("HEAD"))
    private void simulateFallFlyingWithoutElytra(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (player.hasStatusEffect(ModEffects.AVEX)) {
            if (!player.isFallFlying() && !player.isOnGround() && player.getVelocity().y > 0 && player.isSneaking()) {
                player.startFallFlying();
            }
        }
    }
}
