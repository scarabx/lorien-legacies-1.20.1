package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class DeflectStaminaEffect extends StatusEffect {

    public DeflectStaminaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(DEFLECT_COOLDOWN, 100, 0, false, false, false));
            if (player.hasStatusEffect(BESTOWED_PONDUS)) {
                player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 200, 0, false, false, false));
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
