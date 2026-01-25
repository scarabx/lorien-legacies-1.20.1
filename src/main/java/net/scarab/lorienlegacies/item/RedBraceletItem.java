package net.scarab.lorienlegacies.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedBraceletItem extends Item {

    public RedBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player) || world.isClient()) return;

        ServerWorld serverWorld = (ServerWorld) world;

        // Check for nearby hostiles
        boolean hasNearbyHostiles = !serverWorld.getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(3),
                e -> true
        ).isEmpty();

        // Check for nearby projectile entities
        boolean hasNearbyProjectiles = !serverWorld.getOtherEntities(
                player,
                player.getBoundingBox().expand(3),
                e -> e instanceof ProjectileEntity && e.isAlive()
        ).isEmpty();

        // Only deploy shield if there's a threat DIRECTLY IN FRONT
        boolean threatInFront = false;

        // First check hostiles in front
        if (hasNearbyHostiles) {
            List<HostileEntity> hostiles = serverWorld.getEntitiesByClass(
                    HostileEntity.class,
                    player.getBoundingBox().expand(3),
                    e -> true
            );

            for (HostileEntity hostile : hostiles) {
                if (isInFront(player, hostile.getPos())) {
                    threatInFront = true;
                    break;
                }
            }
        }

        // If no hostile in front, check projectiles coming from front
        if (!threatInFront && hasNearbyProjectiles) {
            List<Entity> projectiles = serverWorld.getOtherEntities(
                    player,
                    player.getBoundingBox().expand(3),
                    e -> e instanceof ProjectileEntity && e.isAlive()
            );

            for (Entity projectile : projectiles) {
                // Check if projectile is coming from front
                Vec3d projectilePos = projectile.getPos();
                Vec3d playerPos = player.getPos();

                // Get projectile velocity to determine direction it's coming FROM
                Vec3d projectileVel = projectile.getVelocity();
                if (projectileVel.lengthSquared() > 0.01) {
                    // Projectile is moving, check if it's coming toward player from front
                    Vec3d fromProjectile = projectileVel.normalize().multiply(-1); // Reverse direction
                    if (isDirectionInFront(player, fromProjectile)) {
                        threatInFront = true;
                        break;
                    }
                } else {
                    // If projectile has no velocity, use position-based check
                    if (isInFront(player, projectilePos)) {
                        threatInFront = true;
                        break;
                    }
                }
            }
        }

        // ONLY deploy shield if threat is in front
        if (threatInFront) {
            boolean inOffhand = player.getOffHandStack() == stack;
            if (stack.getItem() == ModItems.RED_BRACELET) {
                stack.decrement(1);
                ItemStack shield = new ItemStack(ModItems.RED_SHIELD);
                if (inOffhand) {
                    player.setStackInHand(Hand.OFF_HAND, shield);
                } else {
                    player.getInventory().insertStack(slot, shield);
                }
            }
        }
        // Threats from other directions: DO NOTHING - shield stays as bracelet

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // Helper method to check if an entity position is in front of the player
    private boolean isInFront(PlayerEntity player, Vec3d targetPos) {
        Vec3d playerPos = player.getPos();
        Vec3d toTarget = targetPos.subtract(playerPos).normalize();
        Vec3d playerLook = player.getRotationVec(1.0F);

        double dotProduct = playerLook.dotProduct(toTarget);
        return dotProduct > 0; // Positive means in front
    }

    // Helper method to check if a direction vector is in front of the player
    private boolean isDirectionInFront(PlayerEntity player, Vec3d direction) {
        Vec3d playerLook = player.getRotationVec(1.0F);
        double dotProduct = playerLook.dotProduct(direction);
        return dotProduct > 0; // Positive means direction is in front
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.red_bracelet.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.red_bracelet.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.red_bracelet.tooltip.shift.3"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.red_bracelet.tooltip.shift.4"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}