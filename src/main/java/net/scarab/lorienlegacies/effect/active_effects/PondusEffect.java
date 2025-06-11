package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.item.ModItems;

import java.util.Map;
import java.util.WeakHashMap;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class PondusEffect extends StatusEffect {

    private static final Map<PlayerEntity, Integer> flightGrace = new WeakHashMap<>();
    private static final int GRACE_TICKS = 100;

    public PondusEffect(StatusEffectCategory category, int color) {
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

        if (entity instanceof PlayerEntity player) {

            // Apply intangibility behavior
            if (player.hasStatusEffect(TOGGLE_INTANGIBILITY)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                applyIntangibility(player);
            }
        }
        if (entity instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION) && !player.getMainHandStack().isOf(ModItems.JOUST_STAFF)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 4, false, false, false));
            } else {
                player.removeStatusEffect(StatusEffects.STRENGTH);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void applyIntangibility(PlayerEntity player) {

        player.noClip = true;

        boolean insideBlock = player.getWorld().getBlockCollisions(player, player.getBoundingBox()).iterator().hasNext();
        boolean hasAvex = player.hasStatusEffect(ModEffects.INTANGIFLY);

        if (hasAvex) {
            // If Avex is also active, disable creative flying
            player.getAbilities().flying = false;
            player.getAbilities().allowFlying = false;
            flightGrace.remove(player);
        } else if (insideBlock) {
            // If not Avex and inside block, enable creative flight and reset grace
            player.getAbilities().flying = true;
            player.getAbilities().allowFlying = true;
            flightGrace.put(player, GRACE_TICKS);
        } else {
            // Outside block, rely on grace period
            int ticksLeft = flightGrace.getOrDefault(player, 0);
            if (ticksLeft > 0) {
                player.getAbilities().flying = true;
                player.getAbilities().allowFlying = true;
                flightGrace.put(player, ticksLeft - 1);
            } else {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }
        }
        player.sendAbilitiesUpdate();
    }
}
