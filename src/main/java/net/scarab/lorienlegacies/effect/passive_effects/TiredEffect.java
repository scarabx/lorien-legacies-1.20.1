package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class TiredEffect extends StatusEffect {

    public TiredEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.AVEX) && player.isFallFlying()) {
                player.stopFallFlying();
            }
            if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof PlayerEntity player && player.getHealth() <= 5) {
            // Apply STAMINA when Tired ends if still low on health
            player.addStatusEffect(new StatusEffectInstance(ModEffects.STAMINA, 200, 0, false, false));
            if (player.hasStatusEffect(ModEffects.PONDUS) && player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)) {
                player.getAbilities().flying = true;
                player.getAbilities().allowFlying = true;
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
