package net.scarab.lorienlegacies.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.3"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.4"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.5"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.silver_pipe.tooltip.shift.6"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
