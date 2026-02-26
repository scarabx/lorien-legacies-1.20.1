package net.scarab.lorienlegacies.effect.passive_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.PONDUS_COOLDOWN;
import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class PondusCooldownEffect extends StatusEffect {

    public PondusCooldownEffect(StatusEffectCategory category, int color) {
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
        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(PONDUS) && player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
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

            if (!player.hasStatusEffect(PONDUS_STAMINA) && !player.hasStatusEffect(PONDUS_COOLDOWN) && player.hasStatusEffect(PONDUS)) {
                player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 1200, 0, false, false));
            }
            if (player.hasStatusEffect(PONDUS) && player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {
                player.getAbilities().flying = true;
                player.getAbilities().allowFlying = true;
            }
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
