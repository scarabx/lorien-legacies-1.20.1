package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;

public class ModEffects {

    public static final StatusEffect LUMEN = registerStatusEffect("lumen",
            new LumenEffect(StatusEffectCategory.BENEFICIAL, 0xFFA500));

    // A flag to track whether the AOE fire is active
    public static final StatusEffect TOGGLE_HUMAN_FIREBALL_AOE = registerStatusEffect("toggle_human_fireball_aoe",
            new ToggleHumanFireballAOEEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000));

    public static final StatusEffect TOGGLE_FLAMING_HANDS = registerStatusEffect("toggle_flaming_hands",
            new ToggleFlamingHandsEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000));

    public static final StatusEffect GlACEN = registerStatusEffect("glacen",
            new GlacenEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF));

    public static final StatusEffect TOGGLE_ICICLES = registerStatusEffect("toggle_icicles",
            new ToggleIciclesEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF));

    public static final StatusEffect TOGGLE_ICE_HANDS = registerStatusEffect("toggle_ice_hands",
            new ToggleIceHandsEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF));

    public static final StatusEffect TOGGLE_SHOOT_FIREBALL = registerStatusEffect("toggle_shoot_fireball",
            new ToggleShootFireballEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF));

    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(LorienLegaciesMod.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        LorienLegaciesMod.LOGGER.info("Registering Mod Effects for " + LorienLegaciesMod.MOD_ID);
    }
}
