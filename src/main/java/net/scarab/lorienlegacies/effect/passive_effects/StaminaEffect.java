package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.util.ModDataTrackers;

public class StaminaEffect extends StatusEffect {

    public StaminaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            if (player.getDataTracker().get(ModDataTrackers.SKIP_STAMINA_REMOVAL)) {
                player.getDataTracker().set(ModDataTrackers.SKIP_STAMINA_REMOVAL, false);
                System.out.println("Reset SKIP_STAMINA_REMOVAL to false during stamina update");
            }
        }

        StatusEffectInstance current = entity.getStatusEffect(this);
        if (current != null && (current.shouldShowParticles() || current.shouldShowIcon())) {
            entity.removeStatusEffect(this);
            entity.addStatusEffect(new StatusEffectInstance(
                    this,
                    current.getDuration(),
                    current.getAmplifier(),
                    false, false, false
            ));
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
            boolean skip = player.getDataTracker().get(ModDataTrackers.SKIP_STAMINA_REMOVAL);
            if (!skip) {
                player.addStatusEffect(new StatusEffectInstance(ModEffects.TIRED, 100, 0, false, false));
            } else {
                player.getDataTracker().set(ModDataTrackers.SKIP_STAMINA_REMOVAL, false);
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
