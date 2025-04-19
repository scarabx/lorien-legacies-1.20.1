package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;

public class ModEffects {
    public static final StatusEffect LUMEN = registerStatusEffect("lumen",
            new LumenEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000));

    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(LorienLegaciesMod.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        LorienLegaciesMod.LOGGER.info("Registering Mod Effects for " + LorienLegaciesMod.MOD_ID);
    }
}
