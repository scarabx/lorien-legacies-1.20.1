package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.entity.IceballProjectileEntity;
import net.scarab.lorienlegacies.entity.IciclesEntity;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.item.ModItems;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class GlacenEffect extends StatusEffect {

    public GlacenEffect(StatusEffectCategory category, int color) {
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

        if (entity instanceof ServerPlayerEntity player) {
            if (player.hasStatusEffect(TOGGLE_FREEZE_WATER) && !player.hasStatusEffect(TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
                freezeWater(player);
                removeFrozen(player);
            }
            removeFrozen(player);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // Method for shooting fireballs
    public static void shootIceball(LivingEntity entity) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(ModEffects.GLACEN)
                && entity.hasStatusEffect(TOGGLE_SHOOT_ICEBALL)
                && !entity.hasStatusEffect(TIRED)
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity) {
                ServerWorld world = (ServerWorld) entity.getWorld();

                IceballProjectileEntity iceballProjectile = new IceballProjectileEntity(entity, world);
                iceballProjectile.setItem(new ItemStack(ModItems.ICEBALL)); // Optional: visually shows item in flight
                iceballProjectile.setVelocity(entity, entity.getPitch(), entity.getYaw(), 0.0f, 1.5f, 1.0f);

                world.spawnEntity(iceballProjectile);
            }
        }
    }

    public static void icicles(LivingEntity user, Entity target) {

        if (user instanceof PlayerEntity player) {
            if (!user.getWorld().isClient()
                    && player.hasStatusEffect(ModEffects.GLACEN)
                    && player.hasStatusEffect(TOGGLE_ICICLES)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                World world = player.getWorld();

                IciclesEntity icicles = new IciclesEntity(ModEntities.ICICLES, world);
                icicles.setPos(target.getX(), target.getY(), target.getZ());
                world.spawnEntity(icicles);

                target.damage(target.getWorld().getDamageSources().thrown(user, target), 10.0F);

                if (target instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 6, false, false, false)); // 100 ticks (5 seconds) with level 6 slowness
                }
            }
        }
    }

    public static void iceHands(LivingEntity user, Entity target) {

        if (user instanceof PlayerEntity player) {
            if (!user.getWorld().isClient()
                    && player.hasStatusEffect(GLACEN)
                    && player.hasStatusEffect(TOGGLE_ICE_HANDS)
                    && !player.hasStatusEffect(TIRED)
                    && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                if (target instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 6, false, false, false));
                }
            }
        }
    }

    public static void freezeWater(LivingEntity entity) {

        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity) {
            ServerWorld world = (ServerWorld) entity.getWorld();

            // 1. Freeze the water block under the player's feet
            BlockPos feetPos = entity.getBlockPos().down();
            BlockState underState = world.getBlockState(feetPos);

            if (underState.getBlock() == Blocks.WATER) {
                world.setBlockState(feetPos, Blocks.ICE.getDefaultState());
            }

            // 2. Freeze the water block the player is looking at (up to 5 blocks away)
            Vec3d start = entity.getCameraPosVec(1.0F);
            Vec3d end = start.add(entity.getRotationVec(1.0F).multiply(5)); // 5 block range

            BlockHitResult hitResult = world.raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.ANY,
                    entity
            ));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos lookPos = hitResult.getBlockPos();
                BlockState lookState = world.getBlockState(lookPos);

                if (lookState.getBlock() == Blocks.WATER) {
                    world.setBlockState(lookPos, Blocks.ICE.getDefaultState());
                }
            }
        }
    }

    public static void removeFrozen(PlayerEntity player) {

        if (player.isFrozen()) {
            player.setFrozenTicks(0);
        }
    }
}
