package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;
import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;

public class AccelixEffect extends StatusEffect {

    public AccelixEffect(StatusEffectCategory category, int color) {
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

        // Don't apply speed if the entity is tired
        if (entity.hasStatusEffect(TIRED)
            || entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
            entity.removeStatusEffect(StatusEffects.SPEED);
            return;
        }

        if (entity instanceof PlayerEntity player) {
            if (!player.getWorld().isClient()) {

                // SPEED: sprinting and not in water
                if (player.isSprinting() && !player.isSubmergedInWater()) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SPEED,
                            Integer.MAX_VALUE,
                            4,
                            false,
                            false,
                            false
                    ));
                }
            } else {
                player.removeStatusEffect(StatusEffects.SPEED);
            }

            // DOLPHIN'S GRACE: sprinting and submerged
            if (player.isSprinting() && player.isSubmergedInWater()) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.DOLPHINS_GRACE,
                        Integer.MAX_VALUE,
                        4,
                        false,
                        false,
                        false
                ));
            } else {
                player.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
