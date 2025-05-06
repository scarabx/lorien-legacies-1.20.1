package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_AVEX;

public class ToggleAvexEffect extends StatusEffect {

    public ToggleAvexEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
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

    public static void toggleAvex(ServerPlayerEntity player) {
        if (player.hasStatusEffect(TOGGLE_AVEX)) {
            player.removeStatusEffect(TOGGLE_AVEX);
        } else {
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_AVEX,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
