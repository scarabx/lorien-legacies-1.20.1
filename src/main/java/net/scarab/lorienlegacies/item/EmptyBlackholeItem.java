package net.scarab.lorienlegacies.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.entity.EmptyBlackHoleEntity;
import net.scarab.lorienlegacies.entity.ModEntities;

public class EmptyBlackholeItem extends Item {

    public EmptyBlackholeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            // Get the player's facing direction as a normalized vector
            Vec3d look = user.getRotationVec(1.0F);
            // Multiply direction by 3 to move 3 blocks forward
            double spawnX = user.getX() + look.x * 3.0;
            double spawnY = user.getY() + 2.0; // One block above the player's head
            double spawnZ = user.getZ() + look.z * 3.0;
            EmptyBlackHoleEntity entity = new EmptyBlackHoleEntity(ModEntities.EMPTY_BLACK_HOLE, world);
            entity.setPos(spawnX, spawnY, spawnZ);
            entity.setItem(stack.copy());
            world.spawnEntity(entity);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}
