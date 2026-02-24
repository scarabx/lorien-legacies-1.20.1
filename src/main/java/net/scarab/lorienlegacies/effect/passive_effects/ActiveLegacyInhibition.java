package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class ActiveLegacyInhibition extends StatusEffect {

    public ActiveLegacyInhibition(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            if (!player.getWorld().isClient()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 6, false, false, false));
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

        if (entity instanceof PlayerEntity player) {
            player.removeStatusEffect(StatusEffects.BLINDNESS);
            player.removeStatusEffect(StatusEffects.WEAKNESS);
            player.removeStatusEffect(StatusEffects.SLOWNESS);
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
