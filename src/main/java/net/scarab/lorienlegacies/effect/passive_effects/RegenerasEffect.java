package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class RegenerasEffect extends StatusEffect {

    public RegenerasEffect(StatusEffectCategory category, int color) {
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

        // Apply regeneration if health is low
        if (!entity.getWorld().isClient() && entity.getHealth() <= 10) {
            StatusEffectInstance regen = entity.getStatusEffect(StatusEffects.REGENERATION);
            if (regen == null || regen.getAmplifier() < 4) {
                entity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.REGENERATION,
                        100,
                        4,
                        false,
                        false,
                        false
                ));
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}







