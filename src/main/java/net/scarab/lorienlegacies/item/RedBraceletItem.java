package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RedBraceletItem extends Item {

    public RedBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (entity instanceof PlayerEntity player && player.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d lookVec = player.getRotationVec(1.0F).normalize();
            // Calculate right and left vectors (rotate lookVec 90 degrees)
            Vec3d rightVec = new Vec3d(-lookVec.z, 0, lookVec.x).normalize(); // Right-hand direction
            Vec3d leftVec = rightVec.multiply(-1); // Left-hand direction
            long hostilesFront = 0;
            long hostilesBack = 0;
            long hostilesRight = 0;
            long hostilesLeft = 0;
            for (HostileEntity hostile : serverWorld.getEntitiesByClass(HostileEntity.class, player.getBoundingBox().expand(5), e -> true)) {
                Vec3d dirToEntity = hostile.getPos().subtract(player.getPos()).normalize();
                double dotFront = lookVec.dotProduct(dirToEntity);
                double dotRight = rightVec.dotProduct(dirToEntity);

                if (dotFront > 0.5) {
                    hostilesFront++;
                }
                if (dotFront < -0.5) {
                    hostilesBack++;
                }
                if (dotRight > 0.5) {
                    hostilesRight++;
                }
                if (dotRight < -0.5) {
                    hostilesLeft++;
                }
            }
            // Prioritize direction: front > back > right > left
            if (hostilesFront >= 1) {
                replaceBraceletWith(player, ModItems.RED_SHIELD_FRONT);
            } else if (hostilesBack >= 1) {
                replaceBraceletWith(player, ModItems.RED_SHIELD_BACK);
            } else if (hostilesRight >= 1) {
                replaceBraceletWith(player, ModItems.RED_SHIELD_RIGHT);
            } else if (hostilesLeft >= 1) {
                replaceBraceletWith(player, ModItems.RED_SHIELD_LEFT);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // Helper method to handle item replacement
    private void replaceBraceletWith(PlayerEntity player, Item newItem) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == ModItems.RED_BRACELET) {
                stack.decrement(1);
                player.getInventory().insertStack(new ItemStack(newItem));
                break;
            }
        }
    }
}
