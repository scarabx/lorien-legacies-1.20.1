package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_XIMIC_ACCELIX;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_XIMIC_STURMA;

public class ToggleXimicSturmaEffect extends StatusEffect {

    public ToggleXimicSturmaEffect(StatusEffectCategory category, int color) {
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
    }

    // Toggle helper method for safely enabling/disabling the effect invisibly
    public static void toggleXimicSturma(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_XIMIC_STURMA)) {
            player.removeStatusEffect(TOGGLE_XIMIC_STURMA);
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_XIMIC_STURMA,
                    -1,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
