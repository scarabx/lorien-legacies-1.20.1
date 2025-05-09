package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

public class ToggleTelekinesisMoveEffect extends StatusEffect {

    public ToggleTelekinesisMoveEffect(StatusEffectCategory category, int color) {
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

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!(entity instanceof ServerPlayerEntity player)) return;

        World world = player.getWorld();
        double range = 6.0;

        for (Entity target : world.getOtherEntities(player, player.getBoundingBox().expand(range))) {
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.setNoGravity(false); // Reset gravity when toggle is turned off
            }
        }
    }

    public static void toggleTelekinesisMove(ServerPlayerEntity player) {
        if (player.hasStatusEffect(ModEffects.TOGGLE_TELEKINESIS_MOVE)) {
            player.removeStatusEffect(ModEffects.TOGGLE_TELEKINESIS_MOVE);
        } else {
            player.addStatusEffect(new StatusEffectInstance(
                    ModEffects.TOGGLE_TELEKINESIS_MOVE,
                    Integer.MAX_VALUE,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
