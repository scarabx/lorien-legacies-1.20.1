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
import static net.scarab.lorienlegacies.effect.toggle_effects.ToggleHumanFireballAOEEffect.toggleHumanFireballAOE;

public class LumenEffect extends StatusEffect {

    public LumenEffect(StatusEffectCategory category, int color) {
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

        // Refresh fire resistance
        //StatusEffectInstance fireResistance = entity.getStatusEffect(StatusEffects.FIRE_RESISTANCE);
        //if (fireResistance == null || fireResistance.getDuration() < 200) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false, false), entity);

        // Apply AOE fire logic while on fire and toggle is active
        if (entity instanceof ServerPlayerEntity player
                && player.isOnFire()
                && player.hasStatusEffect(TOGGLE_HUMAN_FIREBALL_AOE)
                && !player.hasStatusEffect(TIRED)
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
            humanFireballAOE(player, 5, 20); // radius 5, fire for 5 seconds
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
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION))

            if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity) {
                ServerWorld world = ((ServerWorld) entity.getWorld());

                Vec3d direction = entity.getRotationVec(1.0f);
                double x = entity.getX() + direction.x;
                double y = entity.getEyeY();
                double z = entity.getZ() + direction.z;

                SmallFireballEntity fireball = new SmallFireballEntity(world, entity, direction.x, direction.y, direction.z);
                fireball.setPosition(x, y, z);

                world.spawnEntity(fireball);
            }
    }

    // Toggle the AOE fire effect (enable or disable)
    public static void humanFireball(LivingEntity entity) {

        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity player) {
            if (player.isOnFire()) {
                player.extinguish();
                // Disable AOE fire when human fireball is turned off
                toggleHumanFireballAOE(player, false);

            } else {
                player.setOnFireFor(100);
                // Enable AOE fire when human fireball is turned on
                toggleHumanFireballAOE(player, true);
            }
        }
    }

    // AOE Fire effect: Burn mobs, animals, and blocks around the player
    public static void humanFireballAOE(ServerPlayerEntity player, int radius, int fireSeconds) {

        if (!player.getWorld().isClient()
                && !player.hasStatusEffect(TIRED)
                && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            World world = player.getWorld();
            BlockPos playerPos = player.getBlockPos();

            // --- 1. Set mobs/animals on fire ---
            Box box = new Box(
                    player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                    player.getX() + radius, player.getY() + radius, player.getZ() + radius
            );

            for (Entity entity : world.getOtherEntities(player, box)) {
                if (entity instanceof MobEntity || entity instanceof AnimalEntity) {
                    entity.setOnFireFor(fireSeconds);
                }
            }

            // --- 2. Set air blocks on fire if block below is solid ---
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos targetPos = playerPos.add(x, y, z);
                        if (world.isAir(targetPos)) {
                            BlockPos belowPos = targetPos.down();
                            if (world.getBlockState(belowPos).isSolidBlock(world, belowPos)) {
                                world.setBlockState(targetPos, Blocks.FIRE.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void burnOnHit(LivingEntity user, Entity target) {

        if (user instanceof PlayerEntity player) {
            if (!player.getWorld().isClient() && player.isOnFire()) {
                target.setOnFireFor(20);
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

                target.setOnFireFor(20);
            }
        }
    }
}








