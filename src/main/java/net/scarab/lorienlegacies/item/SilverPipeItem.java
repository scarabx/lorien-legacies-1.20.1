package net.scarab.lorienlegacies.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SilverPipeItem extends Item {

    public SilverPipeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        for (int i = 0; i < user.getInventory().size(); i++) {
            ItemStack stack = user.getInventory().getStack(i);
            if (stack.getItem() == ModItems.SILVER_PIPE) {
                stack.decrement(1);
                user.getInventory().insertStack(new ItemStack(ModItems.JOUST_STAFF));
                break;
            }
        }

        return super.use(world, user, hand);
    }
}
