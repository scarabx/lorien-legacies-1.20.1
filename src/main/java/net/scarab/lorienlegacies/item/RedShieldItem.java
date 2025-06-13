package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class RedShieldItem extends Item {

    public RedShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player) || world.isClient()) return;

        ServerWorld serverWorld = (ServerWorld) world;
        boolean hasNearbyHostiles = !serverWorld.getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(3),
                e -> true
        ).isEmpty();

        if (!hasNearbyHostiles) {
            boolean inOffhand = player.getOffHandStack() == stack;

            if (stack.getItem() == ModItems.RED_SHIELD) {
                stack.decrement(1);
                ItemStack bracelet = new ItemStack(ModItems.RED_BRACELET);

                if (inOffhand) {
                    player.setStackInHand(net.minecraft.util.Hand.OFF_HAND, bracelet);
                } else {
                    player.getInventory().insertStack(slot, bracelet);
                }

                StatusEffectInstance pondus = player.getStatusEffect(PONDUS);
                if (pondus != null && pondus.getAmplifier() == 99) {
                    player.removeStatusEffect(PONDUS);
                    if (player.hasStatusEffect(BESTOWED_PONDUS)) {
                        player.addStatusEffect(new StatusEffectInstance(PONDUS, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }

                StatusEffectInstance impenetrableSkin = player.getStatusEffect(TOGGLE_IMPENETRABLE_SKIN);
                if (impenetrableSkin != null && impenetrableSkin.getAmplifier() == 99) {
                    player.removeStatusEffect(TOGGLE_IMPENETRABLE_SKIN);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
