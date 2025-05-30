package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.scarab.lorienlegacies.effect.ModEffects;

public class SubmariEffect extends StatusEffect {

    public SubmariEffect(StatusEffectCategory category, int color) {
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
        if (!entity.getWorld().isClient() && entity.isSubmergedInWater() && !entity.hasStatusEffect(ModEffects.TIRED) && !entity.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION)) {
            // Only reapply if not already active or about to expire
            StatusEffectInstance waterBreathing = entity.getStatusEffect(StatusEffects.WATER_BREATHING);
            if (waterBreathing == null || waterBreathing.getDuration() < 200) { // Less than 10.5s left
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 400, 0, false, false, false));
            }
        } else {
            entity.removeStatusEffect(StatusEffects.WATER_BREATHING);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
