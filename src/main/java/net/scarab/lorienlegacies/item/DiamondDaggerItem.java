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

    // WristWrap string key under which the wristWrapped boolean

    public static final String WRIST_WRAPPED_KEY = "WristWrap";

    public DiamondDaggerItem(Settings settings) {

        super(settings);

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        /* Stack references the item currently held by the player in this hand which is the DiamondDaggerItem

        Only a reference to the item, not a copy of the item itself */

        ItemStack stack = player.getStackInHand(hand);

        System.out.println("Stack has been found");

        /* Stack's isWristWrapped nbt (true/false) is determined by isWristWrapped method that checks stack's nbt

        if isWristWrapped nbt is true, boolean wristWrapped is true,

        if isWristWrapped nbt is false, boolean wristWrapped is false */

        boolean wristWrapped = isWristWrapped(stack);

        System.out.println("Stack's wristwrapped nbt has been determined");

        System.out.println("isWristWrapped(stack) -> " + wristWrapped);

        /* Takes the stack's current isWristWrapped nbt and flips it,

        stack's nbt = isWristWrapped false -> stack's nbt = isWristWrapped true,

        stack's nbt = isWristWrapped true -> stack's nbt = isWristWrapped false */

        setWristWrapped(stack, !wristWrapped);

        System.out.println("Stack's wristwrapped nbt has been determined");

        System.out.println("Wristwrapped -> " + !wristWrapped);

        /* Sends a chat message that indicates stack's isWristWrapped nbt value status

        /* Only runs on the server because it is functional code

        If server/client check is absent game automatically runs code on both client and server/runs code twice

        causing client/server sync issues*/

        if (!world.isClient()) {

            player.sendMessage(wristWrapped ? Text.literal("Diamond Dagger: Wrist Wrap Disabled") : Text.literal("Diamond Dagger: Wrist Wrap Enabled"), false);

        }

        return TypedActionResult.success(stack, world.isClient());

    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        /* Only runs on the server because it is functional code

        If server/client check is absent game automatically runs code on both client and server/runs code twice

        causing unwanted double results, like damage reduces twice: client damages stack,

        server damages stack, the server later syncs its value → item durability may jump, jitter, or break incorrectly */

        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity player) {

            System.out.println("Server and attacker check has succeeded");

            /* Determines if stack has Nbt containing a boolean with a WristWrap string key

            if isWristWrapped nbt is true, boolean wristWrapped is true,

            if isWristWrapped nbt is false, boolean wristWrapped is false */

            boolean wristWrapped = isWristWrapped(stack);

            System.out.println("isWristWrapped(stack) -> " + wristWrapped);

            // Float value for damage that is 8 if the stack's isWristWrapped nbt is true and 4 if it is false

            float damage = wristWrapped ? 8.0F : 4.0F;

            System.out.println(damage + "has been determined");

            // Applies the above determined damage to the target

            /* Tells the server to reduce the target entity’s health by damage points

            and records that the damage came from this player’s attack,

            without this, dagger only does 1 damage */

            target.damage(player.getDamageSources().playerAttack(player), damage);

            System.out.println(damage + "has been applied to target entity");

        }

        return true;

    }

    /* NBT is a Map<String, NBT compound>, where in this case NBT compound is a Boolean, but it can be other types too,

    therefore Nbt is a Map<String, Boolean> where Boolean is true/false

    stack.hasNbt() checks if the stack has nbt value, if not returns false,

    if true, stack.getNbt().getBoolean(WRIST_WRAPPED_KEY) determines if the stack's nbt contains a boolean (true/false) with a WristWrap string key

    stack.getOrCreateNbt() gets the stack's nbt value, if it exists, it uses it, if it doesn't, it creates an empty nbt and putBoolean(...) assigns a boolean (true/false)

    with a WristWrap string key */

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
