package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
}
