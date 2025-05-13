package net.scarab.lorienlegacies.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.scarab.lorienlegacies.legacy.TelekinesisLegacy;

public class LegacyGiverItem extends Item {

    public LegacyGiverItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        TelekinesisLegacy.giveTelekinesis(world, player);  // Handle toggling and message

        return super.use(world, player, hand);
    }
}
