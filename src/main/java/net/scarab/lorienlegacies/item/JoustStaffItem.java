package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
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
            if (user.isSneaking()) {
                // Player is sneaking - convert JOUST_STAFF to SILVER_PIPE
                for (int i = 0; i < user.getInventory().size(); i++) {
                    ItemStack invStack = user.getInventory().getStack(i);
                    if (invStack.getItem() == ModItems.JOUST_STAFF) {
                        invStack.decrement(1);
                        user.getInventory().insertStack(new ItemStack(ModItems.SILVER_PIPE));
                        break;
                    }
                }
                return TypedActionResult.success(stack, false);
            }
            // Throw the joust staff entity
            JoustStaffEntity entity = new JoustStaffEntity(ModEntities.JOUST_STAFF, user, world);
            entity.setItem(stack.copy()); // Pass model info
            entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F, 0.1F);
            world.spawnEntity(entity);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}