package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class RedShieldItem extends Item {

    public RedShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player) || world.isClient()) return;

        ServerWorld serverWorld = (ServerWorld) world;

        // Check for nearby hostiles and projectiles
        boolean hasNearbyHostiles = !serverWorld.getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(3),
                e -> true
        ).isEmpty();

        boolean hasNearbyProjectiles = !serverWorld.getOtherEntities(
                player,
                player.getBoundingBox().expand(3),
                e -> e instanceof ProjectileEntity && e.isAlive()
        ).isEmpty();

        // NEW: Deflect projectiles only if coming from the front
        boolean shouldDeflect = false;
        if (hasNearbyProjectiles) {
            List<Entity> projectiles = serverWorld.getOtherEntities(
                    player,
                    player.getBoundingBox().expand(3),
                    e -> e instanceof ProjectileEntity && e.isAlive()
            );

            for (Entity projectile : projectiles) {
                // Calculate direction from player to projectile
                Vec3d playerPos = player.getPos();
                Vec3d projectilePos = projectile.getPos();
                Vec3d toProjectile = projectilePos.subtract(playerPos).normalize();

                // Get player's look direction
                Vec3d playerLook = player.getRotationVec(1.0F);

                // Calculate angle between player's look direction and projectile direction
                double dotProduct = playerLook.dotProduct(toProjectile);

                // Only deflect if projectile is coming from front (positive dot product)
                if (dotProduct > 0) {
                    shouldDeflect = true;

                    // Deflect the projectile
                    double dx = projectile.getX() - player.getX();
                    double dy = projectile.getY() - player.getY();
                    double dz = projectile.getZ() - player.getZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance > 0) {
                        projectile.setVelocity(dx / distance * 2.0, dy / distance * 2.0, dz / distance * 2.0);
                        projectile.velocityModified = true;
                    }
                }
                // Projectiles from other directions are ignored - no deflection
            }
        }

        // NEW: Check if there are any hostiles in front that would keep shield active
        boolean hasHostilesInFront = false;
        if (hasNearbyHostiles) {
            List<HostileEntity> hostiles = serverWorld.getEntitiesByClass(
                    HostileEntity.class,
                    player.getBoundingBox().expand(3),
                    e -> true
            );

            for (HostileEntity hostile : hostiles) {
                Vec3d playerPos = player.getPos();
                Vec3d hostilePos = hostile.getPos();
                Vec3d toHostile = hostilePos.subtract(playerPos).normalize();
                Vec3d playerLook = player.getRotationVec(1.0F);

                double dotProduct = playerLook.dotProduct(toHostile);
                if (dotProduct > 0) {
                    hasHostilesInFront = true;
                    break;
                }
            }
        }

        // Revert only if no hostiles in front and no projectiles in front
        if (!hasHostilesInFront && !shouldDeflect) {
            boolean inOffhand = player.getOffHandStack() == stack;
            if (stack.getItem() == ModItems.RED_SHIELD) {
                stack.decrement(1);
                ItemStack bracelet = new ItemStack(ModItems.RED_BRACELET);
                if (inOffhand) {
                    player.setStackInHand(Hand.OFF_HAND, bracelet);
                } else {
                    player.getInventory().insertStack(slot, bracelet);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}