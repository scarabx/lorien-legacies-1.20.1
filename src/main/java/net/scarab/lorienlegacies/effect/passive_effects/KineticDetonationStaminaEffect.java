package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class KineticDetonationStaminaEffect extends StatusEffect {

    public KineticDetonationStaminaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (!entity.getWorld().isClient() && entity instanceof PlayerEntity player && player.hasStatusEffect(ModEffects.KINETIC_DETONATION)) {
            // Apply Kinetic Detonation Cooldown effect when Kinetic Detonation Stamina effect ends
            player.addStatusEffect(new StatusEffectInstance(ModEffects.KINETIC_DETONATION_COOLDOWN, 6000, 0, false, false));
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
