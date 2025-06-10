package net.scarab.lorienlegacies.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.item.ModItems;

public class JoustStaffEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    private ItemStack stack = new ItemStack(ModItems.JOUST_STAFF);

    public JoustStaffEntity(EntityType<? extends JoustStaffEntity> entityType, World world) {
        super(entityType, world);
    }

    public JoustStaffEntity(EntityType<? extends JoustStaffEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.JOUST_STAFF);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK && !this.getWorld().isClient) {
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            BlockPos hitPos = blockHit.getBlockPos().offset(blockHit.getSide()); // Offset to the adjacent air block
            World world = this.getWorld();
            if (world.getBlockState(hitPos).isAir() && Blocks.FIRE.getDefaultState().canPlaceAt(world, hitPos)) {
                world.setBlockState(hitPos, Blocks.FIRE.getDefaultState());
            }
            this.inGround = true;
        }
        super.onCollision(hitResult);
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
        if (!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getWorld().getDamageSources().trident(this, this.getOwner()), 20.0F);
            entityHitResult.getEntity().setOnFireFor(20);
        }
    }
}