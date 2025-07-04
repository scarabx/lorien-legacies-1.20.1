package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.item.ModItems;

public class SpikyYellowBallEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    private ItemStack stack = new ItemStack(ModItems.SPIKY_YELLOW_BALL);
    private int ticksSinceLanded = 0;
    private boolean landed = false;

    public SpikyYellowBallEntity(EntityType<? extends SpikyYellowBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpikyYellowBallEntity(EntityType<? extends SpikyYellowBallEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.SPIKY_YELLOW_BALL);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            this.inGround = true;
        }
        super.onCollision(hitResult);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.inGround) {
            if (!landed) {
                landed = true;
                ticksSinceLanded = 0;
            } else {
                ticksSinceLanded++;
                if (ticksSinceLanded >= 5 && !this.getWorld().isClient) {
                    spawnBlackBall();
                    this.discard();
                }
            }
        }
    }

    private void spawnBlackBall() {
        SpikyBlackBallEntity blackBall = new SpikyBlackBallEntity(ModEntities.SPIKY_BLACK_BALL, this.getWorld());
        blackBall.setPos(this.getX(), this.getY(), this.getZ());
        this.getWorld().spawnEntity(blackBall);
    }

    public void setItem(ItemStack copy) {
        this.stack = copy;
    }

    public void setProperties(PlayerEntity user, float pitch, float yaw, float roll, float speed, float divergence) {
        this.setVelocity(user, pitch, yaw, roll, speed, divergence);
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {

        // Fall and stop moving
        this.setVelocity(0, 0, 0);
        this.setNoGravity(false);
        this.setOwner(null); // allows any player to pick it up
        this.pickupType = PickupPermission.ALLOWED;

        // Simulate "sticking" into ground or stopping after hit
        this.setPosition(this.getX(), this.getY() - 0.1, this.getZ()); // nudge it to contact ground
        this.inGround = true;
        this.setNoClip(true); // prevent weird collision physics
    }
}
