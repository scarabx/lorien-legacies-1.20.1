package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class KineticDetonationCooldownEffect extends StatusEffect {

    public KineticDetonationCooldownEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            // Apply Kinetic Detonation Stamina effect when Kinetic Detonation Cooldown effect ends
            player.addStatusEffect(new StatusEffectInstance(ModEffects.KINETIC_DETONATION_STAMINA, 12000, 0, false, false));
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
