package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class XimicEffect extends StatusEffect {

    public XimicEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // Reapply invisibly if needed
        StatusEffectInstance current = entity.getStatusEffect(this);
        if (current != null && (current.shouldShowParticles() || current.shouldShowIcon())) {
            entity.removeStatusEffect(this);
            entity.addStatusEffect(new StatusEffectInstance(
                    this,
                    current.getDuration(),
                    current.getAmplifier(),
                    false,
                    false,
                    false
            ));
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void applyXimicAccelix (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_ACCELIX)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(ACCELIX, -1, 0, false, false, false));
        }
    }

    public static void applyXimicAvex (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_AVEX)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(AVEX, -1, 0, false, false, false));
        }
    }

    public static void applyXimicGlacen (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_GLACEN)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(GLACEN, -1, 0, false, false, false));
        }
    }

    public static void applyXimicLumen (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_LUMEN)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(LUMEN, -1, 0, false, false, false));
        }
    }

    public static void applyXimicNovis (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_NOVIS)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(NOVIS, -1, 0, false, false, false));
        }
    }

    public static void applyXimicNoxen (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_NOXEN)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(NOXEN, -1, 0, false, false, false));
        }
    }

    public static void applyXimicPondus (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_PONDUS)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(PONDUS, -1, 0, false, false, false));
        }
    }

    public static void applyXimicRegeneras (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_REGENERAS)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(REGENERAS, -1, 0, false, false, false));
        }
    }

    public static void applyXimicSturma(PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_STURMA)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(STURMA, -1, 0, false, false, false));
        }
    }

    public static void applyXimicSubmari (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_SUBMARI)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(SUBMARI, -1, 0, false, false, false));
        }
    }

    public static void applyXimicTelekinesis (PlayerEntity player) {

        if (!player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_XIMIC_TELEKINESIS)
            && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            player.addStatusEffect(new StatusEffectInstance(TELEKINESIS, -1, 0, false, false, false));
        }
    }
}
