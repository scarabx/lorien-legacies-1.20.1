package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RedShieldBackItem extends Item {

    public RedShieldBackItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (entity instanceof PlayerEntity player && world instanceof ServerWorld serverWorld) {
            Vec3d lookVec = player.getRotationVec(1.0F).normalize();
            // Check for hostiles behind (dot product < -0.5)
            boolean hasHostilesBehind = !serverWorld.getEntitiesByClass(
                    HostileEntity.class,
                    player.getBoundingBox().expand(5),
                    hostile -> {
                        Vec3d dirToEntity = hostile.getPos().subtract(player.getPos()).normalize();
                        return lookVec.dotProduct(dirToEntity) < -0.5;
                    }
            ).isEmpty();
            // If no hostiles behind, revert to bracelet
            if (!hasHostilesBehind) {
                stack.decrement(1);
                player.getInventory().insertStack(new ItemStack(ModItems.RED_BRACELET));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
