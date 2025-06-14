package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.item.ModItems;

public class FilledBlackHoleEntity extends Entity implements FlyingItemEntity {

    private int ticksExisted = 0;

    public FilledBlackHoleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.ticksExisted = nbt.getInt("TicksExisted");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("TicksExisted", this.ticksExisted);
    }

    @Override
    public void tick() {
        super.tick();
        ticksExisted++;

        if (!this.getWorld().isClient) {
            if (ticksExisted == 100) {
                spawnStrandOfGreenStones();
                this.discard();
            }

            // Shoot arrows every 10 ticks
            if (ticksExisted % 10 == 0) {
                shootArrowProjectile();
            }
        }
    }

    private void shootArrowProjectile() {

        World world = this.getWorld();

        for (int i = 0; i < 20; i++) { // shoot 8 arrows per burst
            ArrowEntity arrow = new ArrowEntity(world, this.getX(), this.getY() + 0.5, this.getZ());

            double dx = (this.random.nextDouble() * 2.0) - 1.0;
            double dy = (this.random.nextDouble() * 1.0) - 0.5;
            double dz = (this.random.nextDouble() * 2.0) - 1.0;

            double len = Math.sqrt(dx*dx + dy*dy + dz*dz);
            dx /= len;
            dy /= len;
            dz /= len;

            double speed = 1.5;
            arrow.setVelocity(dx * speed, dy * speed, dz * speed);

            arrow.setDamage(20.0);
            arrow.setCritical(true);
            arrow.pickupType = ArrowEntity.PickupPermission.DISALLOWED;

            world.spawnEntity(arrow);
        }
    }

    private void spawnStrandOfGreenStones() {
        ItemStack strandOfGreenStonesStack = new ItemStack(ModItems.STRAND_OF_GREEN_STONES);
        this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), strandOfGreenStonesStack));
    }

    public void setItem(ItemStack copy) {
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(ModItems.FILLED_BLACKHOLE);
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}
