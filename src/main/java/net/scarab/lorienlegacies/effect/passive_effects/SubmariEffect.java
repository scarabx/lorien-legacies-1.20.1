package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class SubmariEffect extends StatusEffect {

    public SubmariEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            if (!player.getWorld().isClient() && player.isSubmergedInWater() && !player.hasStatusEffect(ModEffects.TIRED) && !player.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION)) {
                // Only reapply if not already active or about to expire
                StatusEffectInstance waterBreathing = player.getStatusEffect(StatusEffects.WATER_BREATHING);
                if (waterBreathing == null || waterBreathing.getDuration() < 200) { // Less than 10.5s left
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false, false));
                }
            } else {
                player.removeStatusEffect(StatusEffects.WATER_BREATHING);
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
