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
            if (player.hasStatusEffect(TOGGLE_TELEKINESIS_MOVE)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION))
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
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

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
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

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
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_MOVE)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            Vec3d look = player.getRotationVec(1.0F);
            Vec3d eyePos = player.getEyePos();
            World world = player.getWorld();

            double baseGripDistance = 4.0;
            double minGrip = 2.0;
            double maxGrip = 6.0;

            double pitch = player.getPitch(); // positive = looking down, negative = up
            double gripAdjustment = pitch / 90.0 * 2.0; // maps -90..90 to -2..2
            double gripDistance = Math.max(minGrip, Math.min(maxGrip, baseGripDistance - gripAdjustment));

            Entity target = null;
            double closest = maxGrip;

            for (Entity entity : world.getOtherEntities(player, player.getBoundingBox().expand(maxGrip))) {
                if (entity instanceof LivingEntity && !((LivingEntity) entity).hasStatusEffect(CHIMAERA_ESSENCE)) {
                    double dist = eyePos.distanceTo(entity.getPos());
                    if (dist < closest) {
                        closest = dist;
                        target = entity;
                    }
                }
            }

            if (target != null) {
                Vec3d currentPos = target.getPos();
                Vec3d targetPos = eyePos.add(look.normalize().multiply(gripDistance));

                // Prevent mob from going below player's feet (or some safe Y level)
                double minY = Math.floor(player.getY()); // floor level
                if (targetPos.y < minY) {
                    targetPos = new Vec3d(targetPos.x, minY, targetPos.z);
                }

                Vec3d moveVec = targetPos.subtract(currentPos).multiply(0.4); // smooth movement

                target.setVelocity(Vec3d.ZERO);
                target.teleport(currentPos.x + moveVec.x, currentPos.y + moveVec.y, currentPos.z + moveVec.z);
                target.setNoGravity(true);
            }
        }
    }

    public static void deflect(ServerPlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(ModEffects.TELEKINESIS)
                && player.hasStatusEffect(TOGGLE_TELEKINESIS_DEFLECT)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)
                && !player.hasStatusEffect(DEFLECT_COOLDOWN)) {

            player.addStatusEffect(new StatusEffectInstance(PONDUS, 20, 99, false, false, false));
            player.addStatusEffect(new StatusEffectInstance(TOGGLE_IMPENETRABLE_SKIN, 20, 99, false, false, false));
        }
    }
}