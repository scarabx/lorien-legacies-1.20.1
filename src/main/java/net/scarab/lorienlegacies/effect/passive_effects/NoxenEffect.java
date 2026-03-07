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

                World world = player.getWorld();

                BlockPos pos = player.getBlockPos();

                int blockLight = world.getLightLevel(LightType.BLOCK, pos);

                int skyLight = world.getLightLevel(LightType.SKY, pos);

                boolean isDark = (blockLight + skyLight) < 8;

                boolean shouldApplyNightVision = (world.isNight() || player.isSubmergedInWater() || isDark || world.getBlockCollisions(entity, entity.getBoundingBox()).iterator().hasNext()) && !entity.hasStatusEffect(TIRED);

                if (shouldApplyNightVision) {

                    StatusEffectInstance nightVision = player.getStatusEffect(StatusEffects.NIGHT_VISION);

                    if (nightVision == null || nightVision.getDuration() < 1) { // Less than 1 tick left

                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false));

                    }

                } else {

                    player.removeStatusEffect(StatusEffects.NIGHT_VISION);

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








