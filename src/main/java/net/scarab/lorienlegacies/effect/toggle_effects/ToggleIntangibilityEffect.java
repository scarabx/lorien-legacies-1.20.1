package net.scarab.lorienlegacies.effect.toggle_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;
import java.util.WeakHashMap;

public class ToggleIntangibilityEffect extends StatusEffect {

    private static final Map<PlayerEntity, Integer> flightGrace = new WeakHashMap<>();
    private static final int GRACE_TICKS = 100; // 5 seconds

    public ToggleIntangibilityEffect(StatusEffectCategory category, int color) {
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
            intangibility(player);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            player.noClip = false;
            player.getAbilities().flying = false;
            player.getAbilities().allowFlying = false;
            player.sendAbilitiesUpdate();
            flightGrace.remove(player);
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    public static void intangibility(PlayerEntity player) {

        player.noClip = true;
        boolean isInsideBlock = player.getWorld().getBlockCollisions(player, player.getBoundingBox()).iterator().hasNext();
        if (isInsideBlock) {
            player.getAbilities().flying = true;
            player.getAbilities().allowFlying = true;
            flightGrace.put(player, GRACE_TICKS);
        } else {
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


