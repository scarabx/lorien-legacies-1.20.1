package net.scarab.lorienlegacies;

import net.fabricmc.api.ModInitializer;

import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.item.ModItemGroup;
import net.scarab.lorienlegacies.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LorienLegaciesMod implements ModInitializer {
	public static final String MOD_ID = "lorienlegacies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();

		ModItems.registerModItems();

		ModBlocks.registerModBlocks();

	}
}