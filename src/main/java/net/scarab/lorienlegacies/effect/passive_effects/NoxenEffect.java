package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;

public class NoxenEffect extends StatusEffect {

    public NoxenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            if (!player.getWorld().isClient()) {
                World world = player.getWorld();
                BlockPos pos = player.getBlockPos();

                int blockLight = world.getLightLevel(LightType.BLOCK, pos);
                int skyLight = world.getLightLevel(LightType.SKY, pos);
                boolean isDark = (blockLight + skyLight) < 8;

                boolean shouldApplyNightVision = (
                        world.isNight() ||
                                player.isSubmergedInWater() ||
                                isDark ||
                                world.getBlockCollisions(entity, entity.getBoundingBox()).iterator().hasNext()
                ) && !entity.hasStatusEffect(TIRED);

                if (shouldApplyNightVision) {
                    StatusEffectInstance nightVision = player.getStatusEffect(StatusEffects.NIGHT_VISION);
                    if (nightVision == null || nightVision.getDuration() < 210) {
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.NIGHT_VISION,
                                Integer.MAX_VALUE,
                                0,
                                false,
                                false,
                                false
                        ));
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








