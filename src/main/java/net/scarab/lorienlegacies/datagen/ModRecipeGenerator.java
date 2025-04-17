package net.scarab.lorienlegacies.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LORIC_STONE)
                .pattern(" L ")
                .pattern("LDL")
                .pattern(" L ")
                .input('L', ModItems.LORALITE)
                .input('D', Items.DIAMOND)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(ModItems.LORALITE), conditionsFromItem(ModItems.LORALITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.LORIC_STONE) + "_"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LEATHER_SLEEVE)
                .pattern(" L ")
                .pattern(" H ")
                .pattern(" H ")
                .input('L', ModItems.LORALITE)
                .input('H', Items.LEATHER) // H for Horse Skin
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(ModItems.LORALITE), conditionsFromItem(ModItems.LORALITE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.LEATHER_SLEEVE) + "_"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LORIEN_BOOK)
                        .input(ModItems.LORALITE)
                        .input(Items.BOOK)
                        .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
                        .criterion(hasItem(ModItems.LORALITE), conditionsFromItem(ModItems.LORALITE))
                        .offerTo(exporter, new Identifier(getRecipeName(ModItems.LORIEN_BOOK) + "_"));

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.LORALITE, RecipeCategory.MISC, ModBlocks.LORALITE_BLOCK);

        offerSmelting(exporter, List.of(ModItems.LORALITE, ModBlocks.LORALITE_ORE), RecipeCategory.MISC, ModItems.LORALITE,
                0.25f, 200, "loralite");
        offerBlasting(exporter, List.of(ModItems.LORALITE, ModBlocks.LORALITE_ORE), RecipeCategory.MISC, ModItems.LORALITE,
                0.25f, 200, "loralite");
    }
}
