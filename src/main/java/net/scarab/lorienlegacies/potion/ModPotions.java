package net.scarab.lorienlegacies.potion;

import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final Potion CHIMAERA_ESSENCE = registerPotion("chimaera_essence",
            new Potion(new StatusEffectInstance(ModEffects.CHIMAERA_ESSENCE, Integer.MAX_VALUE, 0)));

    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registries.POTION, new Identifier(LorienLegaciesMod.MOD_ID, name), potion);
    }

    public static void registerPotions() {
        LorienLegaciesMod.LOGGER.info("Registering Potions for " + LorienLegaciesMod.MOD_ID);
    }
}
