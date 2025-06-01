package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_FLAMING_HANDS;

public class ToggleFlamingHandsEffect extends StatusEffect {
    public ToggleFlamingHandsEffect(StatusEffectCategory category, int color) {
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
    public static void toggleFlamingHands(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_FLAMING_HANDS)) {
            // Mark that the next stamina removal should not cause TIRED
            //if (player.hasStatusEffect(ModEffects.STAMINA)) {
                //player.getDataTracker().set(ModDataTrackers.SKIP_STAMINA_REMOVAL, true);
            //}
            // Remove the toggle only
            player.removeStatusEffect(TOGGLE_FLAMING_HANDS);
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_FLAMING_HANDS,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
