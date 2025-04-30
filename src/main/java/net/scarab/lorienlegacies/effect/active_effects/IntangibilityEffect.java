package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

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
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void intangibility(PlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(INTANGIBILITY)
                && player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {

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
}

