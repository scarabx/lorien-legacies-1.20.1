package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class NoxenEffect extends StatusEffect {

    public NoxenEffect(StatusEffectCategory category, int color) {
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

        applyNightVisionEffect(entity);

        super.applyUpdateEffect(entity, amplifier);
    }

    public static void applyNightVisionEffect(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(NOXEN)
                && entity.hasStatusEffect(TOGGLE_NOXEN)) {

            entity.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.NIGHT_VISION,
                    400,
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







