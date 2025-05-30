package net.scarab.lorienlegacies.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.effect.ModEffects;

import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;
import static net.scarab.lorienlegacies.effect.ModEffects.LEGACY_INHIBITION;

public class InhibitorRemoteItem extends Item {

    public InhibitorRemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            for (PlayerEntity player : world.getPlayers()) {
                StatusEffectInstance effectInstance = player.getStatusEffect(LEGACY_INHIBITION);
                if (effectInstance != null) {
                    player.addStatusEffect(new StatusEffectInstance(ACTIVE_LEGACY_INHIBITION, 100, 0));
                }
            }
        }

        // Return success and consume the item if you want
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }
}
