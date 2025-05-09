package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Optional;

public class ChimaeraStaffItem extends Item {

    public ChimaeraStaffItem(Settings settings) {
        super(settings);
    }

    public static void chimaeraMorph(PlayerEntity player) {

        double maxDistance = 10.0;
        Vec3d eyePos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
        World world = player.getWorld();
        Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);
        Entity lookedAtEntity = null;
        double closestDistance = maxDistance * maxDistance;

        // Morph from wolf to parrot
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof WolfEntity wolf && wolf.isTamed() && e.isAlive())) {
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

        if (lookedAtEntity != null) {
            // Get the original entity's position
            Vec3d originalPos = lookedAtEntity.getPos();
            // Despawn wolf
            lookedAtEntity.discard();
            // Spawn parrot
            ParrotEntity parrot = EntityType.PARROT.create(world);
            if (parrot != null) {
                parrot.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                // Tame the parrot and set the player as the owner
                parrot.setOwner(player);
                parrot.setTamed(true);
                world.spawnEntity(parrot); // Spawn the parrot in the world
            }
            return;
        }

        // Morph from parrot to axolotl
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof ParrotEntity parrot && parrot.isTamed() && e.isAlive())) {
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

        if (lookedAtEntity != null) {
            // Get the original entity's position
            Vec3d originalPos = lookedAtEntity.getPos();
            // Despawn parrot
            lookedAtEntity.discard();
            // Spawn axolotl
            AxolotlEntity axolotl = EntityType.AXOLOTL.create(world);
            if (axolotl != null) {
                axolotl.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                world.spawnEntity(axolotl); // Spawn the axolotl in the world
            }
            return;
        }

        // Morph from axolotl to horse
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof AxolotlEntity && e.isAlive())) {
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

        if (lookedAtEntity != null) {
            // Get the original entity's position
            Vec3d originalPos = lookedAtEntity.getPos();
            // Despawn axolotl
            lookedAtEntity.discard();
            // Spawn horse
            HorseEntity horse = EntityType.HORSE.create(world);
            if (horse != null) {
                horse.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                horse.setTame(true);
                horse.setOwnerUuid(player.getUuid());
                world.spawnEntity(horse); // Spawn the horse in the world
                // Give the player a saddle
                player.getInventory().insertStack(new ItemStack(Items.SADDLE));
            }
            return;
        }

        // Morph from horse to wolf
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof HorseEntity horse && horse.isTame() && e.isAlive())) {
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

        if (lookedAtEntity != null) {
            // Get the original entity's position
            Vec3d originalPos = lookedAtEntity.getPos();
            // Despawn horse
            lookedAtEntity.discard();
            // Spawn wolf
            WolfEntity wolf = EntityType.WOLF.create(world);
            if (wolf != null) {
                wolf.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                // Tame the wolf and set the player as the owner
                wolf.setOwner(player);
                wolf.setTamed(true);
                world.spawnEntity(wolf); // Spawn the wolf in the world
                // Remove one saddle from the inventory
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (stack.getItem() == Items.SADDLE) {
                        stack.decrement(1);
                        break;
                    }
                }
            }
            return;
        }
    }
}

