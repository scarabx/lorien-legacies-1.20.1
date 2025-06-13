package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.scarab.lorienlegacies.effect.ModEffects.PONDUS;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_IMPENETRABLE_SKIN;

public class RedBraceletItem extends Item {

    public RedBraceletItem(Settings settings) {
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
        if (hasNearbyHostiles) {
            boolean inOffhand = player.getOffHandStack() == stack;

            if (stack.getItem() == ModItems.RED_BRACELET) {
                stack.decrement(1);
                ItemStack shield = new ItemStack(ModItems.RED_SHIELD);

                if (inOffhand) {
                    player.setStackInHand(net.minecraft.util.Hand.OFF_HAND, shield);
                } else {
                    player.getInventory().insertStack(slot, shield);
                }

                player.addStatusEffect(new StatusEffectInstance(PONDUS, Integer.MAX_VALUE, 99, false, false, false));
                player.addStatusEffect(new StatusEffectInstance(TOGGLE_IMPENETRABLE_SKIN, Integer.MAX_VALUE, 99, false, false, false));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}