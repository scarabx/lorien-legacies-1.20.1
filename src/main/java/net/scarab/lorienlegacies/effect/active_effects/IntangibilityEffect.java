package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class IntangibilityEffect extends StatusEffect {

    public IntangibilityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            intangibility(player);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            player.noClip = false;
            player.getAbilities().flying = false;
            player.getAbilities().allowFlying = false;
            player.sendAbilitiesUpdate();
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void intangibility(PlayerEntity player) {

            player.noClip = true;

            boolean isInsideBlock = player.getWorld().getBlockCollisions(player, player.getBoundingBox()).iterator().hasNext();

            if (isInsideBlock) {
                player.getAbilities().flying = true;
                player.getAbilities().allowFlying = true;
            } else {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }

            player.sendAbilitiesUpdate();
        }
    }


