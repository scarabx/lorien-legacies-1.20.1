package net.scarab.lorienlegacies.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.item.DiamondDagger;
import net.scarab.lorienlegacies.item.ModItems;

public class ModModelPredicateProvider {
    public static void registerModModels() {
        ModelPredicateProviderRegistry.register(ModItems.DIAMOND_DAGGER,
                new Identifier(LorienLegaciesMod.MOD_ID, "wrist_wrapped"),
                (stack, world, entity, seed) -> {
                    if (stack.hasNbt() && stack.getNbt().getBoolean(DiamondDagger.WRIST_WRAPPED_KEY)) {
                        return 1f;
                    }
                    return 0f;
                });
        //ModelPredicateProviderRegistry.register(ModItems.JOUST_STAFF, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}
