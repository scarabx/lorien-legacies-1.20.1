package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class LumenEffect extends StatusEffect {

    public LumenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // Refresh fire resistance
        //StatusEffectInstance fireResistance = entity.getStatusEffect(StatusEffects.FIRE_RESISTANCE);
        //if (fireResistance == null || fireResistance.getDuration() < 200) {
        if (!entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, false));
        }

        if (entity instanceof ServerPlayerEntity player) {
            // Apply AOE fire logic while on fire and toggle is active
            if (player.isOnFire()
                    && player.hasStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                humanFireballAOE(player, 1, 20);
            }
            // Only remove effects if player has TIRED or ACTIVE_LEGACY_INHIBITION
            if (player.hasStatusEffect(TIRED) || player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                if (player.hasStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE)) {
                    player.removeStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE);
                }
                if (player.isOnFire()) {
                    player.extinguish();
                }
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // Method for shooting fireballs
    public static void shootFireball(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(ModEffects.LUMEN)
                && entity.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)
                && !entity.hasStatusEffect(TIRED)
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (entity instanceof ServerPlayerEntity player) {
                ServerWorld world = player.getServerWorld();

                Vec3d lookVec = player.getRotationVec(1.0f);
                Vec3d handPos = player.getPos().add(0, 1.2, 0);
                Vec3d rightOffset = player.getRotationVec(1.0f).rotateY((float) Math.toRadians(-30));
                handPos = handPos.add(rightOffset.multiply(0.4));
                Vec3d spawnPos = handPos.add(lookVec.multiply(0.8));

                SmallFireballEntity fireball = new SmallFireballEntity(world, player, lookVec.x, lookVec.y, lookVec.z);
                fireball.setPosition(spawnPos.x, spawnPos.y, spawnPos.z);

                world.spawnEntity(fireball);
            }
        }
    }

    // Toggle the AOE fire effect (enable or disable)
    public static void humanFireball(LivingEntity entity) {

        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity player) {
            if (player.isOnFire()) {
                player.extinguish();
                // Disable AOE fire when human fireball is turned off
                if (player.hasStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE)) {
                    player.removeStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE);
                }
            } else {
                player.setOnFireFor(100);
                // Enable AOE fire when human fireball is turned on
                if (!player.hasStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE)) {
                    player.addStatusEffect(new StatusEffectInstance(TOGGLE_HUMAN_FIREBALL_AOE, Integer.MAX_VALUE, 0, false, false, false));
                }
            }
        }
    }

    // AOE Fire effect: Burn mobs, animals, and create fire trail behind player
    public static void humanFireballAOE(ServerPlayerEntity player, int radius, int fireSeconds) {

        if (!player.getWorld().isClient()
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            World world = player.getWorld();

            // --- 1. Set mobs/animals on fire ---
            Box box = new Box(
                    player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                    player.getX() + radius, player.getY() + radius, player.getZ() + radius
            );

            for (Entity entity : world.getOtherEntities(player, box)) {
                if (entity instanceof MobEntity || entity instanceof AnimalEntity) {
                    entity.setOnFireFor(fireSeconds);
                    entity.damage(player.getDamageSources().generic(), 20.0f);
                }
            }

            // --- 2. Set fire trail - only at player's feet (creates trail as they walk) ---
            BlockPos feetPos = player.getBlockPos();

            // Check if the block at feet is air and has solid block below
            if (world.isAir(feetPos)) {
                BlockPos belowPos = feetPos.down();
                if (world.getBlockState(belowPos).isSolidBlock(world, belowPos)) {
                    world.setBlockState(feetPos, Blocks.FIRE.getDefaultState());
                }
            }
        }
    }
    
    public static void burnOnHit(LivingEntity user, Entity target) {

        if (user instanceof PlayerEntity player) {
            if (!player.getWorld().isClient() && player.isOnFire()) {
                target.setOnFireFor(5);
                target.damage(player.getDamageSources().generic(), 18.0f);
            }
        }
    }

    public static void flamingHands(LivingEntity user, Entity target) {

        if (user instanceof PlayerEntity player) {
            if (!user.getWorld().isClient()
                    && player.hasStatusEffect(ModEffects.LUMEN)
                    && player.hasStatusEffect(TOGGLE_FLAMING_HANDS)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                target.setOnFireFor(5);
                target.damage(player.getDamageSources().generic(), 18.0f);
            }
        }
    }
}








