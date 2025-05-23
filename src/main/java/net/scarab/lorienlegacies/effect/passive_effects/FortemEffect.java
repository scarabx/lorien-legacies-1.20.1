package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class FortemEffect extends StatusEffect {

    public FortemEffect(StatusEffectCategory category, int color) {
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

        applyStrengthEffect(entity);

        super.applyUpdateEffect(entity, amplifier);
    }

    public static void applyStrengthEffect(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(FORTEM)
                && entity.hasStatusEffect(TOGGLE_FORTEM)
                && !entity.hasStatusEffect(ModEffects.TIRED)) {

            entity.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.STRENGTH,
                    100,
                    4,
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








