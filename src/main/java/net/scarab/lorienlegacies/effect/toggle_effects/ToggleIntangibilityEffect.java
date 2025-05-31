package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.Map;
import java.util.WeakHashMap;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class ToggleIntangibilityEffect extends StatusEffect {

    private static final Map<PlayerEntity, Integer> flightGrace = new WeakHashMap<>();

    public ToggleIntangibilityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player)) return;

        // Reapply invisibly if needed
        StatusEffectInstance current = player.getStatusEffect(this);
        if (current != null && (current.shouldShowParticles() || current.shouldShowIcon())) {
            player.removeStatusEffect(this);
            player.addStatusEffect(new StatusEffectInstance(this, current.getDuration(), current.getAmplifier(), false, false, false));
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.noClip = false;

            // Only remove creative flight if Avex isn’t active
            if (!player.hasStatusEffect(ModEffects.INTANGIFLY)) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }

            player.sendAbilitiesUpdate();
            flightGrace.remove(player);
        }

        super.onRemoved(entity, attributes, amplifier);
    }

    // Toggle helper method for safely enabling/disabling the effect invisibly
    public static void toggleIntangibility(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {
            player.removeStatusEffect(TOGGLE_INTANGIBILITY);
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_INTANGIBILITY,
                    -1,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}

