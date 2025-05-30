package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class NovisEffect extends StatusEffect {

    public NovisEffect(StatusEffectCategory category, int color) {
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

        applyInvisibilityEffect(entity);

        super.applyUpdateEffect(entity, amplifier);
    }

    public static void applyInvisibilityEffect(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(NOVIS)
                && entity.hasStatusEffect(TOGGLE_NOVIS)
                && !entity.hasStatusEffect(ModEffects.TIRED)
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            // Only reapply if not already active or about to expire
            StatusEffectInstance invisibility = entity.getStatusEffect(StatusEffects.INVISIBILITY);
            if (invisibility == null || invisibility.getDuration() < 200) { // Less than 10.5s left
                entity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.INVISIBILITY,
                        400,
                        0,
                        false,
                        false,
                        false
                ));
            }
        } else {
            entity.removeStatusEffect(StatusEffects.INVISIBILITY);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}







