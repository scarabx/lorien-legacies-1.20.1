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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class TactileConsciousnessTransfer extends StatusEffect {

    // Knockback resistance UUID for player modifier
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("c56f392f-6597-4b8e-8f35-7a63a88ad36f");

    private static final Map<UUID, AttachedEntityData> attachedEntities = new HashMap<>();

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
            if (player.hasStatusEffect(TACTILE_CONSCIOUSNESS_TRANSFER)
                    && player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                transferConsciousness(player);
            }

            if (player.hasStatusEffect(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER)
                    && attachedEntities.containsKey(player.getUuid())) {
                attachSingleEntityBelow(player, player.getPos());
            } else {
                deactivateTCT(player); // Failsafe to clean up dangling effects
            }
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

            // Prevent double activation if already attached
            if (attachedEntities.containsKey(player.getUuid())) return;

            double maxDistance = 10.0;
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
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 4, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, Integer.MAX_VALUE, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER, Integer.MAX_VALUE, 0, false, false, false));

                EntityAttributeInstance knockbackResistAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
                if (knockbackResistAttribute != null && knockbackResistAttribute.getModifier(KNOCKBACK_RESISTANCE_UUID) == null) {
                    EntityAttributeModifier knockbackResist = new EntityAttributeModifier(
                            KNOCKBACK_RESISTANCE_UUID,
                            "TCT knockback resistance",
                            1.0,
                            EntityAttributeModifier.Operation.ADDITION
                    );
                    knockbackResistAttribute.addPersistentModifier(knockbackResist);
                }
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

        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2, 10, false, false, false));

        Vec3d current = target.getPos();
        Vec3d moveVec = followTarget.subtract(current).multiply(0.4);

        // Clamp vertical rise to max 2 blocks above original Y
        double newY = current.y + moveVec.y;
        double maxY = data.baseY + 2.0;
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
        t = MathHelper.clamp(t, 0.0, 1.0);
        return a.add(ab.multiply(t));
    }

    public static void deactivateTCT(PlayerEntity player) {
        // Cancel any attached entity behavior
        AttachedEntityData data = attachedEntities.remove(player.getUuid());
        if (data != null && data.entity != null) {
            data.entity.setNoGravity(false);

            if (data.entity instanceof LivingEntity livingEntity) {
                livingEntity.removeStatusEffect(StatusEffects.SLOWNESS);
            }
        }

        // Reset player properties
        player.setNoGravity(false);
        player.getAbilities().flying = false;
        player.getAbilities().allowFlying = false;
        player.sendAbilitiesUpdate();

        // Remove invisibility and weakness if they were applied via TCT
        if (player.hasStatusEffect(StatusEffects.INVISIBILITY)
                && player.hasStatusEffect(StatusEffects.WEAKNESS)
                && player.hasStatusEffect(StatusEffects.RESISTANCE)
                && player.hasStatusEffect(StatusEffects.WATER_BREATHING)) {
            player.removeStatusEffect(StatusEffects.INVISIBILITY);
            player.removeStatusEffect(StatusEffects.RESISTANCE);
            player.removeStatusEffect(StatusEffects.WEAKNESS);
            player.removeStatusEffect(StatusEffects.WATER_BREATHING);

            EntityAttributeInstance knockbackResistAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if (knockbackResistAttribute != null) {
                EntityAttributeModifier modifier = knockbackResistAttribute.getModifier(KNOCKBACK_RESISTANCE_UUID);
                if (modifier != null) {
                    knockbackResistAttribute.removeModifier(modifier);
                }
            }
        }
        // Remove ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER flag
        player.removeStatusEffect(ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER);
    }
}
