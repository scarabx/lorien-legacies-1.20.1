package net.scarab.lorienlegacies.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.item.ModItems;

public class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {
    public ModBlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.LORALITE_BLOCK);

        addDrop(ModBlocks.LORALITE_ORE, oreDrops(ModBlocks.LORALITE_ORE, ModItems.LORALITE));
    }
}
