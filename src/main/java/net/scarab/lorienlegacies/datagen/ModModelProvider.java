package net.scarab.lorienlegacies.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LORALITE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LORALITE_ORE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.LORALITE, Models.GENERATED);

        itemModelGenerator.register(ModItems.LORIC_STONE, Models.GENERATED);

        itemModelGenerator.register(ModItems.ICEBALL, Models.GENERATED);

        itemModelGenerator.register(ModItems.KINETIC_PROJECTILE, Models.GENERATED);

        itemModelGenerator.register(ModItems.SHOCK_COLLAR, Models.GENERATED);

        itemModelGenerator.register(ModItems.INHIBITOR, Models.GENERATED);

        itemModelGenerator.register(ModItems.INHIBITOR_REMOTE, Models.GENERATED);

        itemModelGenerator.register(ModItems.DEINHIBITOR, Models.GENERATED);
    }
}
