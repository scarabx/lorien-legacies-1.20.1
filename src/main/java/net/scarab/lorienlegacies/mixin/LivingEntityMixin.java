package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "damage", at = @At("TAIL"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player) {
            if (player.getHealth() <= 5.0F &&
                    !player.hasStatusEffect(ModEffects.TIRED) &&
                    !player.hasStatusEffect(ModEffects.STAMINA)) {

                // Start the loop
                player.addStatusEffect(new StatusEffectInstance(ModEffects.TIRED, 100, 0, false, false, false));
            }
        }
    }
}
