package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_IMPENETRABLE_SKIN;

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

    // Intangibility and Impenetrable Skin damage immunity logic
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY) && !player.hasStatusEffect(ModEffects.TIRED)) {
            if (source.isOf(DamageTypes.GENERIC_KILL)) return;
            cir.setReturnValue(false);
            return;
        }

        if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED)) {
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
            if (!player.isFallFlying()) {
                if (!player.isOnGround() && player.getVelocity().y > 0 && player.isSneaking()) {
                    player.startFallFlying();
                }
            }
        }
    }
}
