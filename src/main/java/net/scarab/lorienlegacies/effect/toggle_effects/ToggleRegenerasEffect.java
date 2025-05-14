package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_REGENERAS;
import static net.scarab.lorienlegacies.effect.ModEffects.REGENERAS;

public class ToggleRegenerasEffect extends StatusEffect {

    public ToggleRegenerasEffect(StatusEffectCategory category, int color) {
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

        // Apply regeneration if the base effect is present
        if (!entity.getWorld().isClient() && entity.hasStatusEffect(REGENERAS)) {
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

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // Toggle helper method for enabling/disabling the effect invisibly
    public static void toggleRegeneras(ServerPlayerEntity player) {
        if (player.hasStatusEffect(TOGGLE_REGENERAS)) {
            player.removeStatusEffect(TOGGLE_REGENERAS);
        } else {
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_REGENERAS,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
