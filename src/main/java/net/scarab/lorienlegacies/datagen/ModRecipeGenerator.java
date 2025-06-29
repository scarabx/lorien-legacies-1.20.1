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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SHOCK_COLLAR)
                .pattern("DED")
                .pattern(" D ")
                .input('D', Items.DIAMOND)
                .input('E', Items.ENDER_EYE)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SHOCK_COLLAR) + "_"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INHIBITOR)
                .pattern(" N ")
                .pattern(" S ")
                .pattern(" N ")
                .input('N', Items.NETHERITE_INGOT)
                .input('S', Items.NETHER_STAR)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INHIBITOR) + "_"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INHIBITOR_REMOTE)
                .pattern(" N ")
                .pattern("NSN")
                .pattern(" N ")
                .input('N', Items.NETHERITE_INGOT)
                .input('S', Items.NETHER_STAR)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INHIBITOR_REMOTE) + "_"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DEINHIBITOR)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .input('N', Items.NETHERITE_INGOT)
                .input('S', Items.NETHER_STAR)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DEINHIBITOR) + "_"));

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.LORALITE, RecipeCategory.MISC, ModBlocks.LORALITE_BLOCK);

        offerSmelting(exporter, List.of(ModItems.LORALITE, ModBlocks.LORALITE_ORE), RecipeCategory.MISC, ModItems.LORALITE,
                0.25f, 200, "loralite");

        offerBlasting(exporter, List.of(ModItems.LORALITE, ModBlocks.LORALITE_ORE), RecipeCategory.MISC, ModItems.LORALITE,
                0.25f, 200, "loralite");
    }
}
