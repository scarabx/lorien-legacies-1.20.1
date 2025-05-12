package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.List;
import java.util.Objects;
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
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof WolfEntity wolf && wolf.isTamed() && wolf.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            float healthLost = 0.0F;
            if (lookedAtEntity instanceof LivingEntity le) {
                healthLost = le.getMaxHealth() - le.getHealth();
            }
            Vec3d originalPos = lookedAtEntity.getPos();
            lookedAtEntity.discard();
            ParrotEntity parrot = EntityType.PARROT.create(world);
            if (parrot != null) {
                parrot.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                parrot.setOwner(player);
                parrot.setTamed(true);
                parrot.addStatusEffect(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0, false, false, false));
                parrot.setHealth(Math.max(1.0F, parrot.getMaxHealth() - healthLost));
                world.spawnEntity(parrot);
            }
            return;
        }

        // Morph from parrot to axolotl
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof ParrotEntity parrot && parrot.isTamed() && parrot.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            float healthLost = 0.0F;
            if (lookedAtEntity instanceof LivingEntity le) {
                healthLost = le.getMaxHealth() - le.getHealth();
            }
            Vec3d originalPos = lookedAtEntity.getPos();
            lookedAtEntity.discard();
            AxolotlEntity axolotl = EntityType.AXOLOTL.create(world);
            if (axolotl != null) {
                axolotl.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                axolotl.addStatusEffect(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0, false, false, false));
                axolotl.setHealth(Math.max(1.0F, axolotl.getMaxHealth() - healthLost));
                world.spawnEntity(axolotl);
                ItemStack tropicalFishBucket = new ItemStack(Items.TROPICAL_FISH_BUCKET);
                ItemStack mainHand = player.getMainHandStack();
                if (mainHand.isEmpty()) {
                    player.setStackInHand(player.getActiveHand(), tropicalFishBucket);
                } else {
                    boolean itemAdded = false;
                    for (int i = 0; i < player.getInventory().size(); i++) {
                        ItemStack slot = player.getInventory().getStack(i);
                        if (slot.isEmpty()) {
                            player.getInventory().setStack(i, mainHand.split(mainHand.getCount()));
                            itemAdded = true;
                            break;
                        }
                    }
                    if (!itemAdded) {
                        mainHand.setCount(0);
                    }
                    player.setStackInHand(player.getActiveHand(), tropicalFishBucket);
                }
            }
            return;
        }

        // Morph from axolotl to horse
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof AxolotlEntity axolotl && axolotl.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            float healthLost = 0.0F;
            if (lookedAtEntity instanceof LivingEntity le) {
                healthLost = le.getMaxHealth() - le.getHealth();
            }
            Vec3d originalPos = lookedAtEntity.getPos();
            lookedAtEntity.discard();
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == Items.TROPICAL_FISH_BUCKET) {
                    stack.decrement(1);
                    break;
                }
            }
            HorseEntity horse = EntityType.HORSE.create(world);
            if (horse != null) {
                horse.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                horse.setTame(true);
                horse.setOwnerUuid(player.getUuid());
                horse.addStatusEffect(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0, false, false, false));
                horse.setHealth(Math.max(1.0F, horse.getMaxHealth() - healthLost));
                world.spawnEntity(horse);
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
            float healthLost = 0.0F;
            if (lookedAtEntity instanceof LivingEntity le) {
                healthLost = le.getMaxHealth() - le.getHealth();
            }
            Vec3d originalPos = lookedAtEntity.getPos();
            lookedAtEntity.discard();
            WolfEntity wolf = EntityType.WOLF.create(world);
            if (wolf != null) {
                wolf.refreshPositionAndAngles(originalPos.x, originalPos.y, originalPos.z, lookedAtEntity.getYaw(), lookedAtEntity.getPitch());
                wolf.setOwner(player);
                wolf.setTamed(true);
                wolf.addStatusEffect(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0, false, false, false));
                wolf.setHealth(Math.max(1.0F, wolf.getMaxHealth() - healthLost));
                world.spawnEntity(wolf);
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (stack.getItem() == Items.SADDLE) {
                        stack.decrement(1);
                        break;
                    }
                }
            }
        }
    }

    public static void teleportAllWithChimaeraEssenceToPlayer(PlayerEntity player) {
        World world = player.getWorld();
        Vec3d playerPos = player.getPos();

        // Huge bounding box that covers the entire Minecraft world
        Box globalBox = new Box(
                -30000000, -30000000, -30000000,
                30000000, 30000000, 30000000
        );

        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, globalBox,
                e -> e.isAlive() && e.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE));

        for (LivingEntity entity : entities) {
            entity.refreshPositionAndAngles(playerPos.x, playerPos.y, playerPos.z, entity.getYaw(), entity.getPitch());
        }
    }

    public static void travelModeActivate(PlayerEntity player) {

        double maxDistance = 10.0;
        Vec3d eyePos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
        World world = player.getWorld();
        Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);
        Entity lookedAtEntity = null;
        double closestDistance = maxDistance * maxDistance;

        // Discard wolf
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof WolfEntity wolf && wolf.isTamed() && wolf.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            lookedAtEntity.discard();
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TOGGLE_TRAVEL_MODE, Integer.MAX_VALUE, 0, false, false, false));
            return;
        }

        // Discard horse
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof HorseEntity horse && horse.isTame() && horse.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            lookedAtEntity.discard();
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TOGGLE_TRAVEL_MODE, Integer.MAX_VALUE, 0, false, false, false));
            return;
        }

        // Discard parrot
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof ParrotEntity parrot && parrot.isTamed() && parrot.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            lookedAtEntity.discard();
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TOGGLE_TRAVEL_MODE, Integer.MAX_VALUE, 0, false, false, false));
            return;
        }

        // Discard axolotl
        for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof AxolotlEntity axolotl && axolotl.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE) && e.isAlive())) {
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
            lookedAtEntity.discard();
            player.addStatusEffect(new StatusEffectInstance(ModEffects.TOGGLE_TRAVEL_MODE, Integer.MAX_VALUE, 0, false, false, false));
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == Items.TROPICAL_FISH_BUCKET) {
                    stack.decrement(1);
                    break;
                }
            }
        }
    }

    public static void travelModeDeactivate (PlayerEntity player) {

        double maxDistance = 10.0;
        Vec3d eyePos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
        World world = player.getWorld();

        // Raycast to find the block the player is looking at
        BlockHitResult hitResult = world.raycast(new RaycastContext(
                eyePos,
                reachVec,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = hitResult.getBlockPos().offset(hitResult.getSide()); // spawn just outside the face hit
            Vec3d spawnPos = Vec3d.ofCenter(blockPos);

            WolfEntity wolf = EntityType.WOLF.create(world);
            if (wolf != null) {
                wolf.refreshPositionAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, player.getYaw(), 0);
                wolf.setOwner(player);
                wolf.setTamed(true);
                wolf.addStatusEffect(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0, false, false, false));
                world.spawnEntity(wolf);
                player.removeStatusEffect(ModEffects.TOGGLE_TRAVEL_MODE);
            }
        }
    }

    public static void markTargetForWolf (PlayerEntity player) {

        double maxDistance = 10.0;
        Vec3d eyePos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
        World world = player.getWorld();

        // Raycast to find the hostile entity being looked at
        Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);
        Entity target = null;
        double closestDistance = maxDistance * maxDistance;

        for (Entity entity : world.getOtherEntities(player, box, e ->
                e instanceof Entity && !(e instanceof WolfEntity wolf && wolf.isTamed() && wolf.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE)) && e.isAlive() && !e.isSpectator())) {
            Box entityBox = entity.getBoundingBox().expand(0.3);
            Optional<Vec3d> optional = entityBox.raycast(eyePos, reachVec);
            if (optional.isPresent()) {
                double distance = eyePos.squaredDistanceTo(optional.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    target = entity;
                }
            }
        }

        // If we found a target, assign it to the tamed Chimaera wolf
        if (target != null) {
            for (Entity entity : world.getOtherEntities(player, player.getBoundingBox().expand(32), e ->
                    e instanceof WolfEntity wolf && wolf.isTamed() && Objects.equals(wolf.getOwnerUuid(), player.getUuid())
                            && wolf.hasStatusEffect(ModEffects.CHIMAERA_ESSENCE))) {
                ((WolfEntity) entity).setTarget((LivingEntity) target);
            }
        }
    }
}
