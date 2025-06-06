package net.scarab.lorienlegacies.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.item.ModItems;

public class ModModelPredicateProvider {
    public static void registerModModels() {

        //ModelPredicateProviderRegistry.register(ModItems.JOUST_STAFF, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}
