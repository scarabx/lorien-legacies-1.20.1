package net.scarab.lorienlegacies.effect.chimaera_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.*;

public class ChimaeraEssenceEffect extends StatusEffect {

    public ChimaeraEssenceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // Only apply to tamed wolves, parrots, horses, or axolotls
        boolean allowed = (entity instanceof WolfEntity wolf && wolf.isTamed())
                || (entity instanceof ParrotEntity parrot && parrot.isTamed())
                || (entity instanceof HorseEntity horse && horse.isTame())
                || (entity instanceof AxolotlEntity);

        if (!allowed) {
            entity.removeStatusEffect(this);
            return;
        }

        if(entity instanceof WolfEntity wolf) {
            wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0, false, false, false));
            wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 2, false, false, false));
            wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 0, false, false, false));
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
