package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class ToggleShootFireballEffect extends StatusEffect {

    public ToggleShootFireballEffect(StatusEffectCategory category, int color) {
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
                    false, false, false
            ));
        }
    }

    public static void toggleShootFireball(ServerPlayerEntity player) {

        if (player.hasStatusEffect(ModEffects.TOGGLE_SHOOT_FIREBALL)) {
            // Mark that the next stamina removal should not cause TIRED
            //if (player.hasStatusEffect(ModEffects.STAMINA)) {
                //player.getDataTracker().set(ModDataTrackers.SKIP_STAMINA_REMOVAL, true);
            //}
            // Remove the toggle only
            player.removeStatusEffect(ModEffects.TOGGLE_SHOOT_FIREBALL);
        } else {
            player.addStatusEffect(new StatusEffectInstance(
                    ModEffects.TOGGLE_SHOOT_FIREBALL,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
