package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

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
                && entity.hasStatusEffect(TOGGLE_NOVIS)) {

            entity.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.INVISIBILITY,
                    200,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}







