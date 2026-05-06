package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class FloatEffect extends StatusEffect {

    public FloatEffect(StatusEffectCategory category, int color) {

        super(category, color);

    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {

            player.getAbilities().flying = true;

            player.getAbilities().allowFlying = true;

            player.sendAbilitiesUpdate();

            if (!player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {

                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false ,false));

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

            player.getAbilities().flying = false;

            player.getAbilities().allowFlying = false;

            player.sendAbilitiesUpdate();

            if (player.hasStatusEffect(StatusEffects.SLOW_FALLING) && player.isOnGround()) {

                player.removeStatusEffect(StatusEffects.SLOW_FALLING);

            }
        }

        super.onRemoved(entity, attributes, amplifier);

    }
}
