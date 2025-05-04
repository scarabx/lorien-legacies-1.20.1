package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class PondusEffect extends StatusEffect {

    public PondusEffect(StatusEffectCategory category, int color) {
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

        if (entity.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 4, false, false, false));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void applyIntangibility(PlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {
            player.removeStatusEffect(ModEffects.TOGGLE_INTANGIBILITY);
        } else {
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TOGGLE_INTANGIBILITY, Integer.MAX_VALUE, 0, false, false, false));
        }
    }
}
