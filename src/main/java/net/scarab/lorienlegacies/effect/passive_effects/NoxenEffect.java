package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;

public class NoxenEffect extends StatusEffect {

    public NoxenEffect(StatusEffectCategory category, int color) {
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
            // Apply or remove Night Vision based on time
            if (!player.getWorld().isClient()) {
                if ((player.getWorld().isNight() || player.isSubmergedInWater() || player.getWorld().getBlockCollisions(entity, entity.getBoundingBox()).iterator().hasNext()) && !entity.hasStatusEffect(TIRED)) {
                    StatusEffectInstance nightVision = player.getStatusEffect(StatusEffects.NIGHT_VISION);
                    if (nightVision == null || nightVision.getDuration() < 210) { // Less than 10.5s left
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
                    // Remove Night Vision when it's no longer night
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








