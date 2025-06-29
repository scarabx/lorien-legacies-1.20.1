package net.scarab.lorienlegacies.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.block.ModBlocks;

public class ModItems {

    public static final Item GREEN_STONE = registerItem("green_stone",
            new GreenStoneItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item X_RAY_STONE = registerItem("x_ray_stone",
            new XRayStoneItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item DIAMOND_DAGGER = registerItem("diamond_dagger",
            new DiamondDaggerItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item SILVER_PIPE = registerItem("silver_pipe",
            new SilverPipeItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item JOUST_STAFF = registerItem("joust_staff",
            new JoustStaffItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item LEATHER_SLEEVE = registerItem("retracted_leather_sleeve",
            new LeatherSleeveItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item SPIKY_YELLOW_BALL = registerItem("spiky_yellow_ball",
            new SpikyYellowBallItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item SPIKY_BLACK_BALL = registerItem("spiky_black_ball",
            new SpikyBlackBallItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item STRAND_OF_GREEN_STONES = registerItem("strand_of_green_stones",
            new StrandOfGreenStonesItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item FILLED_BLACKHOLE = registerItem("filled_blackhole",
            new FilledBlackholeItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item EMPTY_BLACKHOLE = registerItem("empty_blackhole",
            new EmptyBlackholeItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item RED_BRACELET = registerItem("red_bracelet",
            new RedBraceletItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item RED_SHIELD = registerItem("red_shield",
            new RedShieldItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item SHOCK_COLLAR = registerItem("shock_collar",
            new ShockCollarItem(new FabricItemSettings().maxCount(1)));

    public static final Item INHIBITOR = registerItem("inhibitor",
            new InhibitorItem(new FabricItemSettings().maxDamage(1)));

    public static final Item INHIBITOR_REMOTE = registerItem("inhibitor_remote",
            new InhibitorRemoteItem(new FabricItemSettings().maxDamage(500)));

    public static final Item DEINHIBITOR = registerItem("deinhibitor",
            new DeinhibitorItem(new FabricItemSettings().maxDamage(1)));

    public static final Item LORALITE = registerItem("loralite",
            new Item(new FabricItemSettings()));

    public static final Item LORIC_STONE = registerItem("loric_stone",
            new Item(new FabricItemSettings()));

    public static final Item ICEBALL = registerItem("iceball",
            new IceballItem(new FabricItemSettings()));

    public static final Item KINETIC_PROJECTILE = registerItem("kinetic_projectile",
            new KineticProjectileItem(new FabricItemSettings().maxCount(1)));

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
