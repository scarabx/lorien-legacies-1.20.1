package net.scarab.lorienlegacies.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.block.ModBlocks;

public class ModItemGroup {
    public static final ItemGroup LORIEN_LEGACIES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(LorienLegaciesMod.MOD_ID, "lorien_legacies"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.lorien_legacies"))
                    .icon(() -> new ItemStack(ModItems.LORALITE)).entries((displayContext, entries) -> {

                        entries.add(ModItems.LORALITE);

                        entries.add(ModItems.LORIC_STONE);

                        entries.add(ModItems.LEATHER_SLEEVE);

                        entries.add(ModItems.CHIMAERA_STAFF);

                        entries.add(ModItems.LORIEN_BOOK);

                        entries.add(ModBlocks.LORALITE_BLOCK);

                        entries.add(ModBlocks.LORALITE_ORE);

                        entries.add(ModItems.ICEBALL);

                    }).build());

    public static void registerItemGroups() {

    }
}
