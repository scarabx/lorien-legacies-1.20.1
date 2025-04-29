package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_SHOOT_FIREBALL;

public class ToggleShootFireballEffect extends StatusEffect {

    public ToggleShootFireballEffect(StatusEffectCategory category, int color) {
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
    public static void toggleShootFireball(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)) {
            player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_SHOOT_FIREBALL,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
