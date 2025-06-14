package net.scarab.lorienlegacies.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.item.ModItems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmptyBlackHoleEntity extends Entity implements FlyingItemEntity {

    private int currentSuckDepth = 0;
    private int suckDelayCounter = 0;
    private static final int SUCK_DELAY_TICKS = 4; // Change this to control delay speed
    private final List<ItemEntity> suckedItems = new ArrayList<>();
    private int ticksExisted = 0;

    public EmptyBlackHoleEntity(EntityType<?> type, World world) {
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
            //World world = this.getWorld();

            // Suck blocks every few ticks
            suckDelayCounter++;
            if (suckDelayCounter >= SUCK_DELAY_TICKS) {
                suckOneLayer();
                suckDelayCounter = 0;
            }

            // Pull floating item drops toward the black hole
            Iterator<ItemEntity> iterator = suckedItems.iterator();
            while (iterator.hasNext()) {
                ItemEntity item = iterator.next();

                if (!item.isAlive()) {
                    iterator.remove();
                    continue;
                }

                // Vector toward black hole center
                double dx = this.getX() - item.getX();
                double dy = this.getY() - item.getY();
                double dz = this.getZ() - item.getZ();

                double distSq = dx * dx + dy * dy + dz * dz;
                double speed = 0.1;

                // Normalize and apply velocity
                double factor = speed / Math.sqrt(distSq + 0.0001);
                item.setVelocity(dx * factor, dy * factor, dz * factor);

                // Absorb if close enough
                if (distSq < 0.5) {
                    item.discard(); // vanish!
                    iterator.remove();
                }
            }

            // End after 100 ticks
            if (ticksExisted == 100) {
                // Clean up leftover sucked items (force discard)
                for (ItemEntity item : suckedItems) {
                    if (item.isAlive()) {
                        item.discard();
                    }
                }
                suckedItems.clear();
                spawnFilledBlackhole();
                this.discard();
            }
        }
    }

    private void suckOneLayer() {
        World world = this.getWorld();
        BlockPos center = this.getBlockPos();
        int baseY = center.getY();

        int targetY = baseY - currentSuckDepth;
        if (targetY < world.getBottomY()) return;

        int radius = 1;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = new BlockPos(center.getX() + dx, targetY, center.getZ() + dz);
                BlockState state = world.getBlockState(pos);

                if (!state.isAir() && state.getHardness(world, pos) >= 0) {
                    List<ItemStack> drops = Block.getDroppedStacks(state, (ServerWorld) world, pos, world.getBlockEntity(pos));
                    world.removeBlock(pos, false);

                    for (ItemStack stack : drops) {
                        ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
                        item.setToDefaultPickupDelay();  // safe pickup delay
                        item.setVelocity(0, 0.05, 0);    // initial upward nudge
                        item.setNoGravity(true);         // disable vanilla gravity so it floats up steadily
                        suckedItems.add(item);
                        world.spawnEntity(item);
                    }
                }
            }
        }

        currentSuckDepth++;
    }

    private void spawnFilledBlackhole() {
        ItemStack filledBlackholeStack = new ItemStack(ModItems.FILLED_BLACKHOLE);
        this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), filledBlackholeStack));
    }

    public void setItem(ItemStack copy) {
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(ModItems.EMPTY_BLACKHOLE);
    }

    @Override
    public boolean shouldSave() {
        return true;
    }
}
