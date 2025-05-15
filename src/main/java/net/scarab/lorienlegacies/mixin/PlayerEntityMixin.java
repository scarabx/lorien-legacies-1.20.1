package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.util.ModDataTrackers;
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

    // Register custom data trackers
    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initTrackedData(CallbackInfo ci) {
        this.dataTracker.startTracking(ModDataTrackers.SKIP_STAMINA_REMOVAL, false);
    }

    // Intangibility and Impenetrable Skin damage immunity logic
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {
            if (source.isOf(DamageTypes.GENERIC_KILL)) return;
            cir.setReturnValue(false);
            return;
        }

        if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {
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

        if (player.hasStatusEffect(ModEffects.AVEX) && player.hasStatusEffect(ModEffects.TOGGLE_AVEX)) {
            if (!player.isFallFlying()) {
                player.startFallFlying();
            }
        }
    }
}
