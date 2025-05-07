package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

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
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void push(ServerPlayerEntity player) {
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

    public static void pull(ServerPlayerEntity player) {
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

    public static void move(ServerPlayerEntity player) {
        Vec3d look = player.getRotationVec(1.0F);
        Vec3d eyePos = player.getEyePos();
        World world = player.getWorld();
        double closest = RANGE;
        Entity target = null;

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
            target.addVelocity(look.x * FORCE, look.y * FORCE, look.z * FORCE);
            target.velocityModified = true;
        }
    }
}
