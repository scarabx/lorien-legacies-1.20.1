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

        // NEW: Deflect projectiles from any direction
        boolean shouldDeflect = false;

        if (hasNearbyProjectiles) {

            List<Entity> projectiles = serverWorld.getOtherEntities(
                    player,
                    player.getBoundingBox().expand(3),
                    e -> e instanceof ProjectileEntity && e.isAlive()
            );

            for (Entity projectile : projectiles) {

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

        }

        // NEW: Check if there are any hostiles nearby that would keep shield active

        // Revert only if no hostiles nearby and no projectiles nearby
        if (!hasNearbyHostiles && !shouldDeflect) {

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