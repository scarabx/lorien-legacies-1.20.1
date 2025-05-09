package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

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

        applySpeedEffect(entity);

        super.applyUpdateEffect(entity, amplifier);
    }

    public static void applySpeedEffect(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(ACCELIX)
                && entity.hasStatusEffect(TOGGLE_ACCELIX)) {

            entity.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED,
                    200,
                    4,
                    false,
                    false,
                    false
            ));

            if (entity.isSubmergedInWater()) {
                entity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.DOLPHINS_GRACE,
                        200,
                        4,
                        false,
                        false,
                        false
                ));
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect ( int duration, int amplifier){
        return true;
    }
}








