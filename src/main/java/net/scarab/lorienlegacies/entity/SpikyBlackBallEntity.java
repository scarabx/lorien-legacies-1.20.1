package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import net.minecraft.world.explosion.Explosion;
import net.scarab.lorienlegacies.item.ModItems;

public class SpikyBlackBallEntity extends Entity implements FlyingItemEntity {

    private int ticksExisted = 0;
    private boolean exploded = false;

    public SpikyBlackBallEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        // No data to track currently
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.ticksExisted = nbt.getInt("TicksExisted");
        this.exploded = nbt.getBoolean("Exploded");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("TicksExisted", this.ticksExisted);
        nbt.putBoolean("Exploded", this.exploded);
    }

    @Override
    public void tick() {
        super.tick();
        ticksExisted++;

        if (!this.getWorld().isClient) {
            if (ticksExisted == 10 && !exploded) {
                this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0F, World.ExplosionSourceType.MOB);
                exploded = true;
            } else if (ticksExisted == 15) {
                spawnYellowBall();
                this.discard();
            }
        }
    }

    private void spawnYellowBall() {
        ItemStack yellowBallStack = new ItemStack(ModItems.SPIKY_YELLOW_BALL);
        this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), yellowBallStack));
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(ModItems.SPIKY_BLACK_BALL);
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}
