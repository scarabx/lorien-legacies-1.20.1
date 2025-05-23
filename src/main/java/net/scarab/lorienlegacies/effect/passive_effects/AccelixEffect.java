package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.scarab.lorienlegacies.effect.ModEffects;

public class AccelixEffect extends StatusEffect {

    public AccelixEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Reapply invisibly if needed
        StatusEffectInstance current = entity.getStatusEffect(this);
        if (current != null && (current.shouldShowParticles() || current.shouldShowIcon())) {
            entity.removeStatusEffect(this);
            entity.addStatusEffect(new StatusEffectInstance(
                    this,
                    current.getDuration(),
                    current.getAmplifier(),
                    false,
                    false,
                    false
            ));
        }

        if (!entity.getWorld().isClient()) {

            // SPEED: sprinting and not in water
            if (entity.isSprinting() && !entity.isSubmergedInWater() && !entity.hasStatusEffect(ModEffects.TIRED)) {
                entity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SPEED,
                        400,
                        4,
                        false,
                        false,
                        false
                ));
            }
        } else {
            entity.removeStatusEffect(StatusEffects.SPEED);
        }

        // DOLPHIN'S GRACE: sprinting and submerged
        if (entity.isSprinting() && entity.isSubmergedInWater() && !entity.hasStatusEffect(ModEffects.TIRED)) {
            entity.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.DOLPHINS_GRACE,
                    400,
                    4,
                    false,
                    false,
                    false
            ));
        } else {
            entity.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
