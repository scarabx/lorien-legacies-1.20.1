package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class ToggleTelekinesisDeflectEffect extends StatusEffect {

    public ToggleTelekinesisDeflectEffect(StatusEffectCategory category, int color) {
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

    public static void toggleTelekinesisDeflect(ServerPlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.TOGGLE_TELEKINESIS_DEFLECT)) {
            player.removeStatusEffect(ModEffects.TOGGLE_TELEKINESIS_DEFLECT);
        } else {
            player.addStatusEffect(new StatusEffectInstance(
                    ModEffects.TOGGLE_TELEKINESIS_DEFLECT,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
