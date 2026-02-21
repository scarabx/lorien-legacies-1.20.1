package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class RegenerasEffect extends StatusEffect {

    public RegenerasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            // Don't apply regeneration if the entity is tired
            if (player.hasStatusEffect(TIRED)
                    || player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION) || player.hasStatusEffect(REGENERAS_COOLDOWN)) {
                player.removeStatusEffect(StatusEffects.REGENERATION);
                return;
            }

            // Apply regeneration if health is low
            if (!player.getWorld().isClient() && entity.getHealth() < entity.getMaxHealth()) {
                StatusEffectInstance regen = player.getStatusEffect(StatusEffects.REGENERATION);
                if (regen == null || regen.getAmplifier() < 4) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            200,
                            4,
                            false,
                            false,
                            false
                    ));
                }
            } else if (player.getHealth() == player.getMaxHealth()) {
                player.removeStatusEffect(StatusEffects.REGENERATION);
                player.addStatusEffect(new StatusEffectInstance(REGENERAS_COOLDOWN, 200, 0, false, false, false));
            } else if (player.hasStatusEffect(StatusEffects.HEALTH_BOOST) && player.getHealth() == 40) {
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







