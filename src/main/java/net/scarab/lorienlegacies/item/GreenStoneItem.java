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

public class GreenStoneItem extends Item {

    public GreenStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        // Check if the player is submerged in water
        if (user.isSubmergedInWater()) {
            // Get the player's look direction as a normalized vector
            var lookVec = user.getRotationVec(1.0F).normalize();
            // Apply velocity boost in the direction the player is looking
            double boostStrength = 5; // Change this value to increase/decrease the force
            user.addVelocity(lookVec.x * boostStrength, lookVec.y * boostStrength, lookVec.z * boostStrength);
            user.getItemCooldownManager().set(this, 20); // 5-second cooldown
            return TypedActionResult.success(stack, world.isClient());
        }
        return TypedActionResult.pass(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.green_stone.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.green_stone.tooltip.shift.2"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
