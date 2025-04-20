package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class LumenEffect extends StatusEffect {
    protected LumenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 1, false, false), entity);
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void shootFireball(LivingEntity entity) {
        if(!entity.getWorld().isClient()  && entity instanceof net.minecraft.server.network.ServerPlayerEntity) {
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

    public static void humanFireball(LivingEntity entity) {
        if(!entity.getWorld().isClient()  && entity instanceof net.minecraft.server.network.ServerPlayerEntity) {
            if (entity.isOnFire()) {
                entity.extinguish();
            } else {
                entity.setOnFireFor(100);
            }
        }
    }
}

