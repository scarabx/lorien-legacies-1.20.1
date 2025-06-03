package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DiamondDagger extends Item {

    private static final String WristWrap = "WristWrap";

    public DiamondDagger(Settings settings) {
        super(settings);
    }

    // Toggle wrist_wrapped mode when right-clicking with the dagger
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        boolean wristWrapped = isWristWrapped(stack);
        setWristWrapped(stack, !wristWrapped); // Toggle

        if (!world.isClient) {
            player.sendMessage(
                    wristWrapped
                            ? Text.literal("Diamond Dagger: Wrist Wrap Disabled")
                            : Text.literal("Diamond Dagger: Wrist Wrap Enabled"),
                    false
            );
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity player) {
            boolean wristWrapped = isWristWrapped(stack);
            float damage = wristWrapped ? 6.0F : 3.0F;
            int cooldown = wristWrapped ? 4 : 8; // half cooldown when wristWrapped

            target.damage(player.getDamageSources().playerAttack(player), damage);
            stack.damage(1, player, e -> e.sendToolBreakStatus(Hand.MAIN_HAND));
            player.getItemCooldownManager().set(this, cooldown);
        }
        return true;
    }

    private boolean isWristWrapped(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean(WristWrap);
    }

    private void setWristWrapped(ItemStack stack, boolean isWristWrapped) {
        stack.getOrCreateNbt().putBoolean(WristWrap, isWristWrapped);
    }
}
