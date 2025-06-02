package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

import static net.scarab.lorienlegacies.effect.ModEffects.*;
import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;

public class TactileConsciousnessTransfer extends StatusEffect {

    public TactileConsciousnessTransfer(StatusEffectCategory category, int color) {
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
            transferConsciousness(player);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void transferConsciousness(PlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(TACTILE_CONSCIOUSNESS_TRANSFER)
                && player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)
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

            for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof LivingEntity && e.isAlive())) {
                // Get the top center position of the entity's bounding box
                Box entityBox = entity.getBoundingBox();
                Vec3d entityTopCenter = new Vec3d(
                        entityBox.getCenter().x,
                        entityBox.maxY,
                        entityBox.getCenter().z
                );

                // Check if the ray intersects near the top of the entity
                Optional<Vec3d> optional = rayIntersectsSegment(eyePos, reachVec, entityTopCenter, 0.4); // tolerance radius

                if (optional.isPresent()) {
                    double distance = eyePos.squaredDistanceTo(optional.get());
                    if (distance < closestDistance) {
                        lookedAtEntity = entity;
                        closestDistance = distance;
                    }
                }
            }

            if (lookedAtEntity != null) {
                Vec3d entityTopCenter = new Vec3d(
                        lookedAtEntity.getX(),
                        lookedAtEntity.getBoundingBox().maxY,
                        lookedAtEntity.getZ()
                );

                // Calculate final teleport position (feet at top of entity)
                double playerHeight = player.getHeight();
                Vec3d teleportPos = new Vec3d(
                        entityTopCenter.x,
                        entityTopCenter.y,
                        entityTopCenter.z
                );

                // Teleport the player so their feet are placed at that position
                player.requestTeleport(teleportPos.x, teleportPos.y, teleportPos.z);
                player.removeStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER);

                // Optional: Send feedback
                player.sendMessage(Text.literal("Teleported to top of " + lookedAtEntity.getName().getString() + " at " + teleportPos), true);
            }
        }
    }

    public static Optional<Vec3d> rayIntersectsSegment(Vec3d rayStart, Vec3d rayEnd, Vec3d point, double tolerance) {
        Vec3d nearest = closestPointOnLine(rayStart, rayEnd, point);
        if (nearest.squaredDistanceTo(point) <= tolerance * tolerance &&
                rayStart.squaredDistanceTo(nearest) <= rayStart.squaredDistanceTo(rayEnd)) {
            return Optional.of(nearest);
        }
        return Optional.empty();
    }

    public static Vec3d closestPointOnLine(Vec3d a, Vec3d b, Vec3d p) {
        Vec3d ab = b.subtract(a);
        double t = p.subtract(a).dotProduct(ab) / ab.lengthSquared();
        t = MathHelper.clamp(t, 0.0, 1.0);
        return a.add(ab.multiply(t));
    }
}