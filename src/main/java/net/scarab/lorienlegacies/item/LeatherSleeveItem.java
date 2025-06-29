package net.scarab.lorienlegacies.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LeatherSleeveItem extends Item {

    public static final String EXTENDED_KEY = "Extended";

    public LeatherSleeveItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        boolean extended = isExtended(stack);
        setExtended(stack, !extended); // Toggle
        if (!world.isClient()) {
            player.sendMessage(extended ? Text.literal("Leather Sleeve: Blade Retracted") : Text.literal("Leather Sleeve: Blade Extended"), false);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity player) {
            boolean extended = isExtended(stack);
            float damage = extended ? 21.6F : 0.0F;
            target.damage(player.getDamageSources().playerAttack(player), damage);
            stack.damage(1, player, e -> e.sendToolBreakStatus(Hand.MAIN_HAND));
        }
        return true;
    }

    public boolean isExtended(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean(EXTENDED_KEY);
    }

    public void setExtended(ItemStack stack, boolean isWristWrapped) {
        stack.getOrCreateNbt().putBoolean(EXTENDED_KEY, isWristWrapped);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.leather_sleeve.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.leather_sleeve.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.leather_sleeve.tooltip.shift.3"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
