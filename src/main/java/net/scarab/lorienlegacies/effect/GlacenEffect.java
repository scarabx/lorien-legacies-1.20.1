package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.entity.IceballProjectileEntity;
import net.scarab.lorienlegacies.entity.IciclesEntity;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.item.ModItems;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class GlacenEffect extends StatusEffect {
    protected GlacenEffect(StatusEffectCategory category, int color) {
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
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
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

    public static void icicles(LivingEntity user, Entity target) {
        if (!user.getWorld().isClient()
                && user.hasStatusEffect(ModEffects.GlACEN)
                && user.hasStatusEffect(TOGGLE_ICICLES)) {

            World world = user.getWorld();

            IciclesEntity icicles = new IciclesEntity(ModEntities.ICICLES, world);
            icicles.setPos(target.getX(), target.getY(), target.getZ());
            world.spawnEntity(icicles);

            target.damage(target.getWorld().getDamageSources().thrown(user, target), 10.0F);

            if (target instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 3)); // 100 ticks (5 seconds) with level 4 slowness
            }
        }
    }

    public static void iceHands(LivingEntity user, Entity target) {

        if (!user.getWorld().isClient()
                && user.hasStatusEffect(GlACEN)
                && user.hasStatusEffect(TOGGLE_ICE_HANDS)) {
            if (target instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 3));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 100, 3));
            }
        }
    }
}
