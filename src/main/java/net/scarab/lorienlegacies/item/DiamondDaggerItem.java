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

public class DiamondDaggerItem extends Item {

    public static final String WRIST_WRAPPED_KEY = "WristWrap";

    public DiamondDaggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        boolean wristWrapped = isWristWrapped(stack);
        setWristWrapped(stack, !wristWrapped); // Toggle
        if (!world.isClient()) {
            player.sendMessage(wristWrapped ? Text.literal("Diamond Dagger: Wrist Wrap Disabled") : Text.literal("Diamond Dagger: Wrist Wrap Enabled"), false);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity player) {
            boolean wristWrapped = isWristWrapped(stack);
            float damage = wristWrapped ? 8.0F : 4.0F;
            target.damage(player.getDamageSources().playerAttack(player), damage);
            stack.damage(1, player, e -> e.sendToolBreakStatus(Hand.MAIN_HAND));
        }
        return true;
    }

    public boolean isWristWrapped(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean(WRIST_WRAPPED_KEY);
    }

    public void setWristWrapped(ItemStack stack, boolean isWristWrapped) {
        stack.getOrCreateNbt().putBoolean(WRIST_WRAPPED_KEY, isWristWrapped);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.diamond_dagger.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.diamond_dagger.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.diamond_dagger.tooltip.shift.3"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
