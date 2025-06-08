package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetPredicate.class)
public class TargetPredicateMixin {

    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    private void preventTargetingPlayerWithConsciousnessTransfer(LivingEntity baseEntity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.TACTILE_CONSCIOUSNESS_TRANSFER)
                    && player.hasStatusEffect(ModEffects.ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER)) {
                cir.setReturnValue(false); // Do not target the player
            }
        }
    }
}
