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
import net.scarab.lorienlegacies.effect.ModEffects;

public class ShockCollarProjectileEntity extends ThrownItemEntity {
    public ShockCollarProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShockCollarProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.THROWN_SHOCK_COLLAR_PROJECTILE, livingEntity, world);
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

        if (entityHitResult.getEntity() instanceof LivingEntity target) {

            // Apply Active Legacy Inhibition effect to the target
            target.addStatusEffect(new StatusEffectInstance(ModEffects.ACTIVE_LEGACY_INHIBITION, 100, 0, false, false, false));
        }
    }
}
