package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.List;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class TelekinesisEffect extends StatusEffect {

    private static final double FORCE = 1.5;
    private static final double RANGE = 6.0;

    public TelekinesisEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
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

        if (entity instanceof ServerPlayerEntity player) {
            if (player.hasStatusEffect(TOGGLE_TELEKINESIS_MOVE))
                move(player);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void push(ServerPlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(ModEffects.TELEKINESIS)
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) {

            Vec3d look = player.getRotationVec(1.0F);
            World world = player.getWorld();

            List<Entity> targets = world.getOtherEntities(player, player.getBoundingBox().expand(RANGE));
            for (Entity target : targets) {
                if (target instanceof LivingEntity) {
                    target.addVelocity(look.x * FORCE, look.y * FORCE, look.z * FORCE);
                    target.velocityModified = true;
                }
            }
        }
    }

    public static void pull(ServerPlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(ModEffects.TELEKINESIS)
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) {

            Vec3d playerPos = player.getPos();
            World world = player.getWorld();

            List<Entity> targets = world.getOtherEntities(player, player.getBoundingBox().expand(RANGE));
            for (Entity target : targets) {
                if (target instanceof LivingEntity) {
                    Vec3d direction = playerPos.subtract(target.getPos()).normalize();
                    target.addVelocity(direction.x * FORCE, direction.y * FORCE, direction.z * FORCE);
                    target.velocityModified = true;
                }
            }
        }
    }

    public static void move(ServerPlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(ModEffects.TELEKINESIS)
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_MOVE)) {

            Vec3d look = player.getRotationVec(1.0F);
            Vec3d eyePos = player.getEyePos();
            World world = player.getWorld();
            double closest = RANGE;
            Entity target = null;

            // Find the closest living entity to the player within the range
            for (Entity entity : world.getOtherEntities(player, player.getBoundingBox().expand(RANGE))) {
                if (entity instanceof LivingEntity) {
                    double dist = eyePos.distanceTo(entity.getPos());
                    if (dist < closest) {
                        closest = dist;
                        target = entity;
                    }
                }
            }

            if (target != null) {
                // Calculate the new position to maintain a fixed distance in front of the player
                Vec3d targetPos = eyePos.add(look.x * closest, look.y * closest, look.z * closest);

                // Adjust the vertical position based on the player's pitch (looking up or down)
                double pitchFactor = Math.sin(Math.toRadians(player.getPitch())); // Use sin of the pitch for vertical influence
                targetPos = targetPos.add(0, pitchFactor * FORCE * 0.5, 0); // Modify Y position based on pitch

                // Move the entity smoothly towards the target position
                Vec3d currentPos = target.getPos();
                Vec3d delta = targetPos.subtract(currentPos).normalize().multiply(FORCE * 0.2); // Smooth movement

                // Apply the new position with a smooth movement effect
                target.setPosition(currentPos.x + delta.x, currentPos.y + delta.y, currentPos.z + delta.z);
            }
        }
    }
}


