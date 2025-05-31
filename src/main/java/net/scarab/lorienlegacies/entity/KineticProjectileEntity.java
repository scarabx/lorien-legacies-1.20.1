package net.scarab.lorienlegacies.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KineticProjectileEntity extends ThrownItemEntity {
    public KineticProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public KineticProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.THROWN_KINETIC_PROJECTILE, livingEntity, world);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.getWorld().isClient()) {
            // Create explosion at the projectile's position
            this.getWorld().createExplosion(
                    this,
                    this.getX(), this.getY(), this.getZ(),
                    3.0f,
                    World.ExplosionSourceType.MOB
            );
            this.discard(); // Remove the projectile after the explosion
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        // Get the world and block position
        if (!this.getWorld().isClient()) {
            BlockPos pos = blockHitResult.getBlockPos();
            this.getWorld().createExplosion(
                    this,                  // Entity responsible for the explosion
                    pos.getX() + 0.5,      // Center X
                    pos.getY() + 0.5,      // Center Y
                    pos.getZ() + 0.5,      // Center Z
                    3.0f,                  // Explosion power
                    World.ExplosionSourceType.MOB  // Allows block destruction and entity damage
            );
        }

        super.onBlockHit(blockHitResult);
        this.discard(); // Remove the projectile after the explosion
    }
}
