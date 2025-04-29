package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class IceballProjectileEntity extends ThrownItemEntity {
    public IceballProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceballProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.THROWN_ICEBALL_PROJECTILE, livingEntity, world);
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

        // Damage the target (apply 5.0 damage in this case)
        if (entityHitResult.getEntity() instanceof LivingEntity target) {
            target.damage(target.getWorld().getDamageSources().thrown(this, this.getOwner()), 5.0F);

            // Apply Slowness effect to the target
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 4, false, false, false)); // 100 ticks (5 seconds) with level 5 slowness
        }
    }
}
