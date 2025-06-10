package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.entity.JoustStaffEntity;
import net.scarab.lorienlegacies.entity.ModEntities;

public class JoustStaffItem extends Item {

    public JoustStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient() && attacker instanceof PlayerEntity player) {
            target.damage(player.getDamageSources().playerAttack(player), 10F);
            target.setOnFireFor(20);
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            JoustStaffEntity entity = new JoustStaffEntity(ModEntities.JOUST_STAFF, user, world);
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
    public ActionResult useOnBlock(ItemUsageContext context) {

        PlayerEntity user = context.getPlayer();
        if (user == null) return super.useOnBlock(context);
        if (user.getWorld().isClient()) {
            return ActionResult.SUCCESS; // Skip on client to avoid duplicate logic
        }
        if (!user.isSneaking()) {
            // Not sneaking, allow default use (e.g., throwing)
            return ActionResult.PASS;
        }
        // Player is sneaking - perform conversion from SILVER_PIPE to JOUST_STAFF
        for (int i = 0; i < user.getInventory().size(); i++) {
            ItemStack stack = user.getInventory().getStack(i);
            if (stack.getItem() == ModItems.JOUST_STAFF) {
                stack.decrement(1);
                user.getInventory().insertStack(new ItemStack(ModItems.SILVER_PIPE));
                break;
            }
        }
        return ActionResult.SUCCESS;
    }

    public static void applyProjectileDeflectionEffect(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(ModEffects.PROJECTILE_DEFLECTION, 20, 0, false, false, false));
    }
}
