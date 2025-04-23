package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.scarab.lorienlegacies.entity.IceballProjectileEntity;
import net.scarab.lorienlegacies.item.ModItems;

public class GlacenEffect extends StatusEffect {
    protected GlacenEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // Method for shooting fireballs
    public static void shootIceball(LivingEntity entity) {
        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity) {
            ServerWorld world = (ServerWorld) entity.getWorld();

            IceballProjectileEntity iceballProjectile = new IceballProjectileEntity(entity, world);
            iceballProjectile.setItem(new ItemStack(ModItems.ICEBALL)); // Optional: visually shows item in flight
            iceballProjectile.setVelocity(entity, entity.getPitch(), entity.getYaw(), 0.0f, 1.5f, 1.0f);

            world.spawnEntity(iceballProjectile);
        }
    }
}
