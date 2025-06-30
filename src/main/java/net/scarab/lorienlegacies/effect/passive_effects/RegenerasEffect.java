package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class RegenerasEffect extends StatusEffect {

    public RegenerasEffect(StatusEffectCategory category, int color) {
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

        if (entity instanceof PlayerEntity player) {
            // Don't apply regeneration if the entity is tired
            if (player.hasStatusEffect(TIRED)
                    || player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION) || player.hasStatusEffect(REGENERAS_COOLDOWN)) {
                player.removeStatusEffect(StatusEffects.REGENERATION);
                return;
            }

            // Apply regeneration if health is low
            if (!player.getWorld().isClient() && entity.getHealth() <= 10) {
                StatusEffectInstance regen = player.getStatusEffect(StatusEffects.REGENERATION);
                if (regen == null || regen.getAmplifier() < 4) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            Integer.MAX_VALUE,
                            4,
                            false,
                            false,
                            false
                    ));
                }
            } else if (player.getHealth() == player.getMaxHealth()) {
                player.removeStatusEffect(StatusEffects.REGENERATION);
                player.addStatusEffect(new StatusEffectInstance(REGENERAS_COOLDOWN, 200, 0, false, false, false));
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}







