package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class PondusStaminaEffect extends StatusEffect {

    public PondusStaminaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private boolean hasPondusAndSkinAmp99(PlayerEntity player) {
        StatusEffectInstance pondus = player.getStatusEffect(PONDUS);
        StatusEffectInstance skin = player.getStatusEffect(TOGGLE_IMPENETRABLE_SKIN);
        return pondus != null && skin != null && pondus.getAmplifier() == 99 && skin.getAmplifier() == 99;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (hasPondusAndSkinAmp99(player)) {
                // Skip applying/reapplying effect if both have amp 99
                return;
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (hasPondusAndSkinAmp99(player)) {
                // Skip onRemoved logic if both have amp 99
                return;
            }

            if (!player.hasStatusEffect(PONDUS_COOLDOWN) && !player.hasStatusEffect(PONDUS_STAMINA) && player.hasStatusEffect(PONDUS)) {
                player.addStatusEffect(new StatusEffectInstance(PONDUS_COOLDOWN, 6000, 0, false, false, false));
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
