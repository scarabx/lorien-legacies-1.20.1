package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DiamondDagger extends SwordItem {

    public static final String WRIST_WRAPPED_KEY = "WristWrap";

    // Store base attack damage and attack speed modifier passed in constructor
    private final float baseAttackDamage;
    private final float baseAttackSpeed;

    public DiamondDagger(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.baseAttackDamage = attackDamage;
        this.baseAttackSpeed = attackSpeed;
    }

    // Rest of your code unchanged...
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

            float damage;
            float attacksPerSecond;

            if (wristWrapped) {
                damage = ToolMaterials.DIAMOND.getAttackDamage() + 4.0F;
                attacksPerSecond = 3.0F;
            } else {
                damage = baseAttackDamage;
                attacksPerSecond = 4.0F + baseAttackSpeed;
            }

            int cooldown = (int) Math.ceil(20 / attacksPerSecond);

            target.damage(player.getDamageSources().playerAttack(player), damage);
            stack.damage(1, player, e -> e.sendToolBreakStatus(Hand.MAIN_HAND));
            player.getItemCooldownManager().set(this, cooldown);
        }
        return true;
    }

    public boolean isWristWrapped(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().getBoolean(WRIST_WRAPPED_KEY);
    }

    public void setWristWrapped(ItemStack stack, boolean isWristWrapped) {
        stack.getOrCreateNbt().putBoolean(WRIST_WRAPPED_KEY, isWristWrapped);
    }
}
