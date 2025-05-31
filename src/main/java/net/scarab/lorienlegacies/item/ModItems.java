package net.scarab.lorienlegacies.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.block.ModBlocks;

public class ModItems {

    public static final Item LORALITE = registerItem("loralite",
            new Item(new FabricItemSettings()));

    public static final Item LORIC_STONE = registerItem("loric_stone",
            new Item(new FabricItemSettings()));

    public static final Item LEATHER_SLEEVE = registerItem("leather_sleeve",
            new Item(new FabricItemSettings()));

    public static final Item ICEBALL = registerItem("iceball",
            new IceballItem(new FabricItemSettings()));

    public static final Item SHOCK_COLLAR = registerItem("shock_collar",
            new ShockCollarItem(new FabricItemSettings()));

    public static final Item INHIBITOR_ITEM = registerItem("inhibitor",
            new InhibitorItem(new FabricItemSettings()));

    public static final Item INHIBITOR_REMOTE_ITEM = registerItem("inhibitor_remote",
            new InhibitorRemoteItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LorienLegaciesMod.MOD_ID, name), item);
    }

    private static void itemGroupIngredients(FabricItemGroupEntries entries) {
        entries.add(LORALITE);

        entries.add(ModBlocks.LORALITE_BLOCK);
    }

    public static void registerModItems() {
        LorienLegaciesMod.LOGGER.info("Registering Mod Items for " + LorienLegaciesMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::itemGroupIngredients);
    }
}
