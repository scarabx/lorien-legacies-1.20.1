package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.tick_throttling.TickThrottlerHandler;

import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;
import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;

public class NoxenEffect extends StatusEffect {

    public NoxenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // TICK-BASED THROTTLING - Run every 5 ticks

        TickThrottlerHandler.ticksSinceLastUpdate++;

        TickThrottlerHandler.TICK_UPDATE_INTERVAL = 5;

        if (TickThrottlerHandler.ticksSinceLastUpdate < TickThrottlerHandler.TICK_UPDATE_INTERVAL) {

            return; // Skip night vision application this tick

        }

        TickThrottlerHandler.ticksSinceLastUpdate = 0;

        if (entity instanceof PlayerEntity player) {

            if (!player.getWorld().isClient()) {

                if (player.hasStatusEffect(TIRED) || player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);

                    return;

                }

                if (!player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));

                }
            }
        }

        super.applyUpdateEffect(entity, amplifier);

    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {

        return true;

    }
}








