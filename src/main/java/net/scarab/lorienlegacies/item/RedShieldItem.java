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

public class RedShieldItem extends Item {

    public RedShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player) || world.isClient()) return;

        player.removeStatusEffect(TIRED);
        player.removeStatusEffect(PONDUS_COOLDOWN);
        player.removeStatusEffect(PONDUS_STAMINA);

        player.addStatusEffect(new StatusEffectInstance(PONDUS, 20, 99, false, false, false));
        player.addStatusEffect(new StatusEffectInstance(TOGGLE_IMPENETRABLE_SKIN, 20, 99, false, false, false));

        ServerWorld serverWorld = (ServerWorld) world;

        // Check for nearby hostiles and projectiles
        boolean hasNearbyHostiles = !serverWorld.getEntitiesByClass(
                HostileEntity.class,
                player.getBoundingBox().expand(3),
                e -> true
        ).isEmpty();

        boolean hasNearbyProjectiles = !serverWorld.getOtherEntities(
                player,
                player.getBoundingBox().expand(3),
                e -> e instanceof ProjectileEntity && e.isAlive()
        ).isEmpty();

        // Revert only if no hostiles and no projectiles nearby
        if (!hasNearbyHostiles && !hasNearbyProjectiles) {
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
                        player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 200, 0, false, false, false));
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
