package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    @Final
    public PlayerScreenHandler playerScreenHandler;

    @Shadow
    public abstract boolean damage(DamageSource source, float amount);

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Universal Intangibility Protection — blocks all damage except /kill
        if (player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {

            // Allow the kill command to work
            if (source.isOf(DamageTypes.GENERIC_KILL)) {
                return; // Don't cancel /kill
            }

            cir.setReturnValue(false); // Cancel all other damage
            return;
        }

        // Impenetrable Skin Protection — blocks all damage except those explicitly excluded
        if (player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {

            // Exclusions: Allow only these through
            boolean isVoid = source.isOf(DamageTypes.OUT_OF_WORLD);
            boolean isStarve = source.isOf(DamageTypes.STARVE);
            boolean isKillCommand = source.isOf(DamageTypes.GENERIC_KILL);
            boolean isMagic = source.isOf(DamageTypes.MAGIC);
            boolean isWither = source.isOf(DamageTypes.WITHER);
            boolean isDragonBreath = source.isOf(DamageTypes.DRAGON_BREATH);
            boolean isSonicBoom = source.isOf(DamageTypes.SONIC_BOOM);
            boolean isSuffocate = source.isOf(DamageTypes.IN_WALL);
            boolean isThorns = source.isOf(DamageTypes.THORNS);
            boolean isEnderDragonBreath = source.isOf(DamageTypes.DRAGON_BREATH);
            boolean isDrown = source.isOf(DamageTypes.DROWN);

            // Let those types go through, block everything else
            if (!(isVoid || isStarve || isKillCommand || isMagic || isWither || isDragonBreath || isSonicBoom || isSuffocate || isThorns || isEnderDragonBreath || isDrown)) {
                cir.setReturnValue(false);
            }
        }
    }

    // Inject into the tick method to simulate fall flying without Elytra
    @Inject(method = "tick", at = @At("HEAD"))
    private void simulateFallFlyingWithoutElytra(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.AVEX) && player.hasStatusEffect(ModEffects.TOGGLE_AVEX)) {
            // Manually activate fall flying state without requiring Elytra
            if (!player.isFallFlying()) {
                player.startFallFlying(); // Start fall flying manually
            }
        }
    }
}
