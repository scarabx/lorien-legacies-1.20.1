package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.*;
import java.util.function.Predicate;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class TactileConsciousnessTransfer extends StatusEffect {

    private static final Map<UUID, AttachedEntityData> attachedEntities = new HashMap<>();

    public static final Map<UUID, BlockPos> originalPositions = new HashMap<>();

    // At class level
    private static final Map<UUID, List<StatusEffectInstance>> copiedEffects = new HashMap<>();

    private static class AttachedEntityData {
        public final Entity entity;
        public final double baseY;

        public AttachedEntityData(Entity entity) {
            this.entity = entity;
            this.baseY = entity.getY();
        }
    }

    public TactileConsciousnessTransfer(StatusEffectCategory category, int color) {
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

        if (entity instanceof PlayerEntity player) {
            // Trigger possession transfer if toggles are active and no inhibition
            if (player.hasStatusEffect(TACTILE_CONSCIOUSNESS_TRANSFER)
                    && player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                transferConsciousness(player);
            }

            // While possessing...
            if (player.hasStatusEffect(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER)) {
                AttachedEntityData data = attachedEntities.get(player.getUuid());

                // Auto-deactivate if possessed mob died
                if (data != null && !data.entity.isAlive()) {
                    deactivateTCT(player);
                    return;
                }

                // Sync effects if the ridden entity is a PlayerEntity
                if (data != null) {
                    if (data.entity instanceof PlayerEntity riddenPlayer) {
                        List<StatusEffectInstance> copied = new ArrayList<>();
                        for (StatusEffectInstance effect : riddenPlayer.getStatusEffects()) {
                            if (!effect.getEffectType().isInstant()) {
                                player.addStatusEffect(new StatusEffectInstance(effect));
                                copied.add(effect);
                            }
                        }
                        copiedEffects.put(player.getUuid(), copied);
                    }
                } else {
                    deactivateTCT(player); // failsafe
                }

                // Deactivate if inhibition effects appear
                if (player.hasStatusEffect(TIRED) || player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                    deactivateTCT(player);
                } else if (attachedEntities.containsKey(player.getUuid())) {
                    attachSingleEntityBelow(player, player.getPos());
                    makeRiddenHostileMobAggroAllEntities(player);
                } else {
                    deactivateTCT(player); // Failsafe to clean up dangling effects
                }
            }
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void activateTCT(PlayerEntity player) {
        // Save original player position before possession
        originalPositions.put(player.getUuid(), player.getBlockPos());
    }

    public static void transferConsciousness(PlayerEntity player) {

        if (!player.getWorld().isClient()
                && player.hasStatusEffect(TACTILE_CONSCIOUSNESS_TRANSFER)
                && player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            // Prevent double activation if already attached
            if (attachedEntities.containsKey(player.getUuid())) return;

            double maxDistance = 3.0;
            Vec3d eyePos = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F);
            Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
            World world = player.getWorld();
            Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);

            Entity lookedAtEntity = null;
            double closestDistance = maxDistance * maxDistance;

            for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof LivingEntity && e.isAlive())) {
                Box entityBox = entity.getBoundingBox();
                Vec3d entityTopCenter = new Vec3d(
                        entityBox.getCenter().x,
                        entityBox.maxY,
                        entityBox.getCenter().z
                );

                Optional<Vec3d> optional = rayIntersectsSegment(eyePos, reachVec, entityTopCenter, 0.4);
                if (optional.isPresent()) {
                    double distance = eyePos.squaredDistanceTo(optional.get());
                    if (distance < closestDistance) {
                        lookedAtEntity = entity;
                        closestDistance = distance;
                    }
                }
            }

            if (lookedAtEntity != null) {
                // Store original position before teleporting
                activateTCT(player);

                Vec3d entityTopCenter = new Vec3d(
                        lookedAtEntity.getX(),
                        lookedAtEntity.getBoundingBox().maxY,
                        lookedAtEntity.getZ()
                );

                // Move player above the entity
                player.requestTeleport(entityTopCenter.x, entityTopCenter.y, entityTopCenter.z);
                player.setVelocity(0, 0, 0);
                player.setNoGravity(true);
                player.getAbilities().allowFlying = true;
                player.getAbilities().flying = true;
                player.sendAbilitiesUpdate();

                player.removeStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, Integer.MAX_VALUE, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, Integer.MAX_VALUE, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER, Integer.MAX_VALUE, 0, false, false, false));

                // Store the lookedAt entity and base Y position
                attachedEntities.put(player.getUuid(), new AttachedEntityData(lookedAtEntity));
            }
        }
    }

    private static void attachSingleEntityBelow(PlayerEntity player, Vec3d baseFeetPos) {
        AttachedEntityData data = attachedEntities.get(player.getUuid());
        if (data == null || !(data.entity instanceof LivingEntity target) || !target.isAlive()) {
            attachedEntities.remove(player.getUuid());
            return;
        }

        World world = player.getWorld();
        Vec3d followTarget = baseFeetPos.subtract(0, 1.5, 0);

        Vec3d current = target.getPos();
        Vec3d moveVec = followTarget.subtract(current).multiply(0.4);

        // Clamp vertical rise to max 5 blocks above original Y (changed from 2.0 to 5.0)
        double newY = current.y + moveVec.y;
        double maxY = data.baseY + 5.0;
        if (newY > maxY) {
            moveVec = new Vec3d(moveVec.x, maxY - current.y, moveVec.z);
        }

        // Check for collisions and apply partial movement if needed
        Box newBox = target.getBoundingBox().offset(moveVec);
        if (!world.isSpaceEmpty(target, newBox)) {
            Vec3d partialMove = Vec3d.ZERO;
            Box boxX = target.getBoundingBox().offset(moveVec.x, 0, 0);
            if (world.isSpaceEmpty(target, boxX)) {
                partialMove = partialMove.add(moveVec.x, 0, 0);
            }
            Box boxZ = target.getBoundingBox().offset(0, 0, moveVec.z);
            if (world.isSpaceEmpty(target, boxZ)) {
                partialMove = partialMove.add(0, 0, moveVec.z);
            }
            double clampedY = MathHelper.clamp(moveVec.y, -1.0, 1.0);
            Box boxY = target.getBoundingBox().offset(0, clampedY, 0);
            if (world.isSpaceEmpty(target, boxY)) {
                partialMove = partialMove.add(0, clampedY, 0);
            }
            moveVec = partialMove;
        }

        target.setVelocity(Vec3d.ZERO);
        target.teleport(current.x + moveVec.x, current.y + moveVec.y, current.z + moveVec.z);
        target.setNoGravity(true);
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
        t = MathHelper.clamp(t, 0, 1);
        return a.add(ab.multiply(t));
    }

    public static void deactivateTCT(PlayerEntity player) {
        // Cancel any attached entity behavior
        AttachedEntityData data = attachedEntities.remove(player.getUuid());
        if (data != null && data.entity != null) {
            data.entity.setNoGravity(false);

            // Clear forced aggro target
            if (data.entity instanceof MobEntity mob) {
                mob.setTarget(null);

                // If the mob is angerable, clear anger
                if (mob instanceof Angerable angerable) {
                    angerable.stopAnger();
                }
            }
        }

        // Reset player properties
        player.setNoGravity(false);
        player.getAbilities().flying = false;
        player.getAbilities().allowFlying = false;
        player.sendAbilitiesUpdate();

        // Remove invisibility and weakness if they were applied via TCT
        player.removeStatusEffect(StatusEffects.INVISIBILITY);
        player.removeStatusEffect(StatusEffects.WEAKNESS);
        //player.removeStatusEffect(StatusEffects.WATER_BREATHING);

        // Remove ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER flag
        player.removeStatusEffect(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER);
        List<StatusEffectInstance> copied = copiedEffects.remove(player.getUuid());
        if (copied != null) {
            for (StatusEffectInstance effect : copied) {
                if (effect != null && effect.getEffectType() != null) {
                    player.removeStatusEffect(effect.getEffectType());
                }
            }
        }

        // Teleport player back to original position if stored
        BlockPos originalPos = originalPositions.remove(player.getUuid());
        if (originalPos != null) {
            player.requestTeleport(originalPos.getX() + 0.5, originalPos.getY(), originalPos.getZ() + 0.5);
        }
    }

    public static void makeRiddenHostileMobAggroAllEntities(PlayerEntity player) {

            AttachedEntityData data = attachedEntities.get(player.getUuid());
            if (data == null) return;

            Entity entity = data.entity;
            if (!(entity instanceof HostileEntity mob)) return;

            World world = mob.getWorld();
            double radius = 16.0;

            Predicate<Entity> validTarget = e -> {
                if (!(e instanceof LivingEntity living)) return false;
                if (!living.isAlive()) return false;
                if (e == mob) return false;
                if (e == player) return false; // Prevent targeting rider
                return true;
            };

            List<Entity> entitiesInRange = world.getEntitiesByClass(Entity.class, mob.getBoundingBox().expand(radius), validTarget);

            Entity closestTarget = null;
            double closestDistanceSq = radius * radius;

            for (Entity candidate : entitiesInRange) {
                double distSq = mob.squaredDistanceTo(candidate);
                if (distSq < closestDistanceSq) {
                    closestTarget = candidate;
                    closestDistanceSq = distSq;
                }
            }

            if (closestTarget instanceof LivingEntity targetLiving) {
                mob.setTarget(targetLiving);
            }

            // Forcefully prevent mob from targeting the player
            if (mob.getTarget() == player) {
                mob.setTarget(null);
            }
        }
    }
