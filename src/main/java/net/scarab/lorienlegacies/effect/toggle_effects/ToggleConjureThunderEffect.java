package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_CONJURE_RAIN;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_CONJURE_THUNDER;

public class ToggleConjureThunderEffect extends StatusEffect {

    public ToggleConjureThunderEffect(StatusEffectCategory category, int color) {
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
    public static void toggleConjureThunder(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_CONJURE_THUNDER)) {
            player.removeStatusEffect(TOGGLE_CONJURE_THUNDER);
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_CONJURE_THUNDER,
                    -1,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
