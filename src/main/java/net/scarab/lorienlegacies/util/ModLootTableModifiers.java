package net.scarab.lorienlegacies.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.item.ModItems;

public class ModLootTableModifiers {

    private static final Identifier OCEAN_RUIN_CHEST_ID
            = new Identifier("minecraft", "chests/underwater_ruin_small");

    private static final Identifier SHIPWRECK_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/shipwreck_treasure");

    private static final Identifier DESERT_PYRAMID_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/desert_pyramid");

    private static final Identifier ANCIENT_CITY_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/ancient_city");

    private static final Identifier BURIED_TREASURE_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/buried_treasure");

    private static final Identifier IGLOO_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/igloo_chest");

    private static final Identifier JUNGLE_TEMPLE_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/jungle_temple");

    private static final Identifier WOODLAND_MANSION_STRUCTURE_CHEST_ID
            = new Identifier("minecraft", "chests/woodland_mansion");

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(OCEAN_RUIN_CHEST_ID.equals(id)) {
                // Adds Green Stone to the Ocean Ruin loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) // Drops 10% of the time
                        .with(ItemEntry.builder(ModItems.GREEN_STONE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(SHIPWRECK_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds X Ray Stone to the Shipwreck loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.08f)) // Drops 8% of the time
                        .with(ItemEntry.builder(ModItems.X_RAY_STONE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(DESERT_PYRAMID_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Diamond Dagger to the Desert Temple loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) // Drops 10% of the time
                        .with(ItemEntry.builder(ModItems.DIAMOND_DAGGER))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(ANCIENT_CITY_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Leather Sleeve to the Ancient City loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.07f)) // Drops 7% of the time
                        .with(ItemEntry.builder(ModItems.LEATHER_SLEEVE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(BURIED_TREASURE_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Joust Staff to the Buried Treasure loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.08f)) // Drops 8% of the time
                        .with(ItemEntry.builder(ModItems.SILVER_PIPE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(IGLOO_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Spiky Yellow Ball to the Igloo loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.07f)) // Drops 7% of the time
                        .with(ItemEntry.builder(ModItems.SPIKY_YELLOW_BALL))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(JUNGLE_TEMPLE_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Strand of Green Stones to the Jungle Temple loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f)) // Drops 5% of the time
                        .with(ItemEntry.builder(ModItems.STRAND_OF_GREEN_STONES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(WOODLAND_MANSION_STRUCTURE_CHEST_ID.equals(id)) {
                // Adds Red Bracelet to the Woodland Mansion loot table.
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.03f)) // Drops 3% of the time
                        .with(ItemEntry.builder(ModItems.RED_BRACELET))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
