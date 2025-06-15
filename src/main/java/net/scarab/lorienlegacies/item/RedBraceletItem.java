package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class RedBraceletItem extends Item {

    public RedBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (!(entity instanceof PlayerEntity player) || world.isClient()) return;
        ServerWorld serverWorld = (ServerWorld) world;
        // Check for nearby hostiles
        boolean hasNearbyHostiles = !serverWorld.getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(3),
                e -> true
        ).isEmpty();
        // Check for specific nearby projectile entities
        boolean hasNearbyProjectiles = !serverWorld.getOtherEntities(
                player,
                player.getBoundingBox().expand(3), // You can tweak the range
                e -> e instanceof ProjectileEntity && e.isAlive()
        ).isEmpty();
        if (hasNearbyHostiles || hasNearbyProjectiles) {
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
                player.removeStatusEffect(TIRED);
                player.removeStatusEffect(PONDUS_COOLDOWN);
                player.removeStatusEffect(PONDUS_STAMINA);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}