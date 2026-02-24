package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

public class StaminaEffect extends StatusEffect {

    public StaminaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (!entity.getWorld().isClient() && entity instanceof PlayerEntity player && player.getHealth() <= 5) {
            // Apply TIRED when Stamina ends if still low on health
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TIRED, 100, 0, false, false));
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
