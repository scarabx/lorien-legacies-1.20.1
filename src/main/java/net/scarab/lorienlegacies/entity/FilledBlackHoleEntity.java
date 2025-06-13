package net.scarab.lorienlegacies.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.item.ModItems;

public class FilledBlackHoleEntity extends Entity implements FlyingItemEntity {

    private ItemStack stack = new ItemStack(ModItems.FILLED_BLACKHOLE);
    private int ticksExisted = 0;
    private boolean exploded = false;

    public FilledBlackHoleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.ticksExisted = nbt.getInt("TicksExisted");
        //this.exploded = nbt.getBoolean("Exploded");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("TicksExisted", this.ticksExisted);
        //nbt.putBoolean("Exploded", this.exploded);
    }

    @Override
    public void tick() {
        super.tick();
        ticksExisted++;

        if (!this.getWorld().isClient) {
            if (ticksExisted == 100 /*&& !exploded*/) {
                //exploded = true;
            //} else if (ticksExisted == 15) {
                spawnStrandOfGreenStones();
                this.discard();
            }
        }
    }

    private void spawnStrandOfGreenStones() {
        ItemStack strandOfGreenStonesStack = new ItemStack(ModItems.STRAND_OF_GREEN_STONES);
        this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), strandOfGreenStonesStack));
    }

    public void setItem(ItemStack copy) {
        this.stack = copy;
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
