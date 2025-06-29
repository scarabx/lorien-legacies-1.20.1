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
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.entity.SpikyYellowBallEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpikyYellowBallItem extends Item {

    public SpikyYellowBallItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            SpikyYellowBallEntity entity = new SpikyYellowBallEntity(ModEntities.SPIKY_YELLOW_BALL, user, world);
            entity.setItem(stack.copy()); // Pass model info
            entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F, 0.1F);
            world.spawnEntity(entity);
            // Remove one item
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.spiky_yellow_ball.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.spiky_yellow_ball.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.spiky_yellow_ball.tooltip.shift.3"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
