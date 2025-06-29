package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import net.minecraft.world.explosion.Explosion;
import net.scarab.lorienlegacies.item.ModItems;

import java.util.List;

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
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();

                // Check for nearby entities within 5-block radius
                List<LivingEntity> nearbyEntities = this.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        new Box(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5),
                        LivingEntity::isAlive
                );

                // If any entity nearby, re-center explosion on it
                if (!nearbyEntities.isEmpty()) {
                    LivingEntity target = nearbyEntities.get(0);
                    x = target.getX();
                    y = target.getY();
                    z = target.getZ();
                }

                // Create explosion at determined location
                this.getWorld().createExplosion(
                        this, // cause
                        x, y, z,
                        3.0F,
                        World.ExplosionSourceType.MOB
                );
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
