package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class TeletrasEffect extends StatusEffect {

    public TeletrasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public static void teleport(LivingEntity entity) {

        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity player && player.hasStatusEffect(TOGGLE_TELETRAS) && !player.hasStatusEffect(TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {
            ServerWorld world = (ServerWorld) entity.getWorld();

            Vec3d start = entity.getCameraPosVec(1.0F);
            Vec3d end = start.add(entity.getRotationVec(1.0F).multiply(10)); // 10 block range

            BlockHitResult hitResult = world.raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.ANY,
                    entity
            ));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos targetPos = hitResult.getBlockPos().up(); // One block above the hit block

                BlockState targetState = world.getBlockState(targetPos);

                // Only teleport if the target position is safe (air)
                if (targetState.isAir()) {
                    player.requestTeleport(
                            targetPos.getX() + 0.5,
                            targetPos.getY(),
                            targetPos.getZ() + 0.5
                    );

                    // Teleport visuals
                    world.playSound(null, targetPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    world.spawnParticles(ParticleTypes.PORTAL,
                            targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5,
                            32, 0.5, 0.5, 0.5, 0.1
                    );
                }
            }
        }
    }
}