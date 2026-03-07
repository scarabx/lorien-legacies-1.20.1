package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.tick_throttling.TickThrottlerHandler;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class NovisEffect extends StatusEffect {

    public NovisEffect(StatusEffectCategory category, int color) {

        super(category, color);

    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // TICK-BASED THROTTLING - Run every 5 ticks
        TickThrottlerHandler.ticksSinceLastUpdate++;

        TickThrottlerHandler.TICK_UPDATE_INTERVAL = 5;

        if (TickThrottlerHandler.ticksSinceLastUpdate < TickThrottlerHandler.TICK_UPDATE_INTERVAL) {

            return; // Skip invisibility application this tick

        }

        TickThrottlerHandler.ticksSinceLastUpdate = 0;

        if (entity instanceof PlayerEntity player) {

            applyInvisibilityEffect(player);
        }

        super.applyUpdateEffect(entity, amplifier);

    }

    public static void applyInvisibilityEffect(PlayerEntity player) {

        if (!player.getWorld().isClient() && player.hasStatusEffect(NOVIS) && player.hasStatusEffect(TOGGLE_NOVIS) && !player.hasStatusEffect(ModEffects.TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            // Only reapply if not already active or about to expire

            StatusEffectInstance invisibility = player.getStatusEffect(StatusEffects.INVISIBILITY);

            if (invisibility == null || invisibility.getDuration() < 1) { // Less than 1 tick left

                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, false));

            }

        } else {

            player.removeStatusEffect(StatusEffects.INVISIBILITY);

        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {

        return true;

    }
}







