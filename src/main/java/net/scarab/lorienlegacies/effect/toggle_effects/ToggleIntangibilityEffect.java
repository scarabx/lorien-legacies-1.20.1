package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.Map;
import java.util.WeakHashMap;

public class ToggleIntangibilityEffect extends StatusEffect {

    private static final Map<PlayerEntity, Integer> flightGrace = new WeakHashMap<>();

    public ToggleIntangibilityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.noClip = false;

            // Only remove creative flight if Avex isn’t active
            if (!player.hasStatusEffect(ModEffects.INTANGIFLY)) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }

            player.sendAbilitiesUpdate();
            flightGrace.remove(player);
        }

        super.onRemoved(entity, attributes, amplifier);
    }
}

