package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.Optional;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class SturmaEffect extends StatusEffect {

    public SturmaEffect(StatusEffectCategory category, int color) {
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

    public static void conjureRain(PlayerEntity player, ServerWorld serverWorld) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(STURMA)
                && player.hasStatusEffect(TOGGLE_CONJURE_RAIN)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (!player.getWorld().isClient() && !player.getWorld().isRaining() && !player.getWorld().isThundering()) {
                serverWorld.setWeather(0, 1200, true, false);
            }
        }
    }

    public static void conjureThunder(PlayerEntity player, ServerWorld serverWorld) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(STURMA)
                && player.hasStatusEffect(TOGGLE_CONJURE_THUNDER)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (!player.getWorld().isClient() && player.getWorld().isRaining() && !player.getWorld().isThundering()) {
                serverWorld.setWeather(0, 3600, true, true);
            }
        }
    }

    public static void conjureClearWeather(PlayerEntity player, ServerWorld serverWorld) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(STURMA)
                && player.hasStatusEffect(TOGGLE_CONJURE_CLEAR_WEATHER)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (!player.getWorld().isClient() && player.getWorld().isRaining() && player.getWorld().isThundering()) {
                serverWorld.setWeather(24000, 0, false, false);
            }
        }
    }

    public static void lightningStrike(PlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(STURMA)
                && player.hasStatusEffect(TOGGLE_LIGHTNING_STRIKE)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            double maxDistance = 10.0;
            Vec3d eyePos = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F);
            Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
            World world = player.getWorld();
            Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);

            Entity lookedAtEntity = null;
            double closestDistance = maxDistance * maxDistance;

            // Check all living entities the player is looking at
            for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof LivingEntity && e.isAlive())) {
                Box entityBox = entity.getBoundingBox().expand(0.3);
                Optional<Vec3d> optional = entityBox.raycast(eyePos, reachVec);
                if (optional.isPresent()) {
                    double distance = eyePos.squaredDistanceTo(optional.get());
                    if (distance < closestDistance) {
                        lookedAtEntity = entity;
                        closestDistance = distance;
                    }
                }
            }

            // Optionally do something with the entity (e.g., summon lightning)
            if (lookedAtEntity != null && !world.isClient()) {
                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(lookedAtEntity.getX(), lookedAtEntity.getY(), lookedAtEntity.getZ());
                    world.spawnEntity(lightning);
                }
            }
        }
    }
}
