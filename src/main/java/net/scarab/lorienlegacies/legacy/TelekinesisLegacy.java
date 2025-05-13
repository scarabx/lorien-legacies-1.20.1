package net.scarab.lorienlegacies.legacy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.LorienLegaciesMod;

import java.util.List;

public class TelekinesisLegacy {

    private static final double FORCE = 1.5;
    private static final double RANGE = 6.0;

    private boolean hasTelekinesisLegacy;

    public TelekinesisLegacy(boolean hasTelekinesisLegacy) {
        this.hasTelekinesisLegacy = hasTelekinesisLegacy;
    }

    public boolean HasTelekinesisLegacy() {
        return hasTelekinesisLegacy;
    }

    public void setHasTelekinesisLegacy(boolean hasLegacy) {
        this.hasTelekinesisLegacy = hasLegacy;
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

        double baseGripDistance = 4.0;
        double minGrip = 2.0;
        double maxGrip = 6.0;

        double pitch = player.getPitch(); // positive = looking down, negative = up
        double gripAdjustment = pitch / 90.0 * 2.0; // maps -90..90 to -2..2
        double gripDistance = Math.max(minGrip, Math.min(maxGrip, baseGripDistance - gripAdjustment));

        Entity target = null;
        double closest = maxGrip;

        for (Entity entity : world.getOtherEntities(player, player.getBoundingBox().expand(maxGrip))) {
            if (entity instanceof LivingEntity) {
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

    public static void giveTelekinesis(World world, PlayerEntity player) {

        if (!world.isClient) {  // Only run on the server
            TelekinesisLegacy telekinesisLegacy = new TelekinesisLegacy(true); // Default to true or false as needed

            // Toggle the legacy state
            telekinesisLegacy.setHasTelekinesisLegacy(!telekinesisLegacy.HasTelekinesisLegacy());

            // Send a message based on the new state
            String message = telekinesisLegacy.HasTelekinesisLegacy() ? "You now have Telekinesis!" : "Telekinesis removed.";
            player.sendMessage(Text.of(message), false);
        }
    }

    public static void registerTelekinesisLegacy() {
        LorienLegaciesMod.LOGGER.info("Registering Legacies for " + LorienLegaciesMod.MOD_ID);
    }
}

