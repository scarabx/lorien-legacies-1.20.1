package net.scarab.lorienlegacies.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.active_effects.*;
import net.scarab.lorienlegacies.effect.chimaera_effects.*;
import net.scarab.lorienlegacies.effect.passive_effects.*;
import net.scarab.lorienlegacies.effect.toggle_effects.*;

public class ModEffects {

    public static final StatusEffect LUMEN = registerStatusEffect("lumen",
            new LumenEffect(StatusEffectCategory.BENEFICIAL, 0xFFA500/*Orange*/));

    // A flag to track whether the AOE fire is active
    public static final StatusEffect TOGGLE_HUMAN_FIREBALL_AOE = registerStatusEffect("toggle_human_fireball_aoe",
            new ToggleHumanFireballAOEEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000/*Orange*/));

    public static final StatusEffect TOGGLE_FLAMING_HANDS = registerStatusEffect("toggle_flaming_hands",
            new ToggleFlamingHandsEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000/*Orange*/));

    public static final StatusEffect GLACEN = registerStatusEffect("glacen",
            new GlacenEffect(StatusEffectCategory.BENEFICIAL, 0x0000AA/*Dark Blue*/));

    public static final StatusEffect TOGGLE_ICICLES = registerStatusEffect("toggle_icicles",
            new ToggleIciclesEffect(StatusEffectCategory.BENEFICIAL, 0x0000AA/*Dark Blue*/));

    public static final StatusEffect TOGGLE_ICE_HANDS = registerStatusEffect("toggle_ice_hands",
            new ToggleIceHandsEffect(StatusEffectCategory.BENEFICIAL, 0x0000AA/*Dark Blue*/));

    public static final StatusEffect TOGGLE_SHOOT_FIREBALL = registerStatusEffect("toggle_shoot_fireball",
            new ToggleShootFireballEffect(StatusEffectCategory.BENEFICIAL, 0xFFA5000/*Orange*/));

    public static final StatusEffect TOGGLE_SHOOT_ICEBALL = registerStatusEffect("toggle_shoot_iceball",
            new ToggleShootIceballEffect(StatusEffectCategory.BENEFICIAL, 0x0000AA/*Dark Blue*/));

    public static final StatusEffect TOGGLE_FREEZE_WATER = registerStatusEffect("toggle_freeze_water",
            new ToggleFreezeWaterEffect(StatusEffectCategory.BENEFICIAL, 0x0000AA/*Dark Blue*/));

    public static final StatusEffect REGENERAS = registerStatusEffect("regeneras",
            new RegenerasEffect(StatusEffectCategory.BENEFICIAL, 0xFFC0CB/*Pink*/));

    public static final StatusEffect FORTEM = registerStatusEffect("fortem",
            new FortemEffect(StatusEffectCategory.BENEFICIAL, 0xFF0000/*Red*/));

    public static final StatusEffect ACCELIX = registerStatusEffect("accelix",
            new AccelixEffect(StatusEffectCategory.BENEFICIAL, 0xFFFF00/*Yellow*/));

    public static final StatusEffect NOXEN = registerStatusEffect("noxen",
            new NoxenEffect(StatusEffectCategory.BENEFICIAL, 0x00FF00/*Green*/));

    public static final StatusEffect NOVIS = registerStatusEffect("novis",
            new NovisEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF/*Light Blue*/));

    public static final StatusEffect TOGGLE_REGENERAS = registerStatusEffect("toggle_regeneras",
            new ToggleRegenerasEffect(StatusEffectCategory.BENEFICIAL, 0xFFC0CB/*Pink*/));

    public static final StatusEffect TOGGLE_FORTEM = registerStatusEffect("toggle_fortem",
            new ToggleFortemEffect(StatusEffectCategory.BENEFICIAL, 0xFF0000/*Red*/));

    public static final StatusEffect TOGGLE_ACCELIX = registerStatusEffect("toggle_accelix",
            new ToggleAccelixEffect(StatusEffectCategory.BENEFICIAL, 0xFFFF00/*Yellow*/));

    public static final StatusEffect TOGGLE_NOXEN = registerStatusEffect("toggle_noxen",
            new ToggleNoxenEffect(StatusEffectCategory.BENEFICIAL, 0x00FF00/*Green*/));

    public static final StatusEffect TOGGLE_NOVIS = registerStatusEffect("toggle_novis",
            new NovisEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF/*Light Blue*/));

    public static final StatusEffect PONDUS = registerStatusEffect("pondus",
            new PondusEffect(StatusEffectCategory.BENEFICIAL, 0x1A0B29/*Obsidian*/));

    public static final StatusEffect TOGGLE_IMPENETRABLE_SKIN = registerStatusEffect("toggle_impenetrable_skin",
            new ToggleImpenetrableSkinEffect(StatusEffectCategory.BENEFICIAL, 0x1A0B29/*Obsidian*/));

    public static final StatusEffect TOGGLE_INTANGIBILITY = registerStatusEffect("toggle_intangibility",
            new ToggleIntangibilityEffect(StatusEffectCategory.BENEFICIAL, 0x55FFFF/*Light Blue*/));

    public static final StatusEffect AVEX = registerStatusEffect("avex",
            new AvexEffect(StatusEffectCategory.BENEFICIAL, 0x87CEEB/*Sky Blue*/));

    public static final StatusEffect TOGGLE_AVEX = registerStatusEffect("toggle_avex",
            new ToggleAvexEffect(StatusEffectCategory.BENEFICIAL, 0x87CEEB/*Sky Blue*/));

    public static final StatusEffect TELEKINESIS = registerStatusEffect("telekinesis",
            new TelekinesisEffect(StatusEffectCategory.BENEFICIAL, 0x9b4f96/*Purple*/));

    public static final StatusEffect TOGGLE_TELEKINESIS_PUSH = registerStatusEffect("toggle_telekinesis_push",
            new ToggleTelekinesisPushEffect(StatusEffectCategory.BENEFICIAL, 0x9b4f96/*Purple*/));

    public static final StatusEffect TOGGLE_TELEKINESIS_PULL = registerStatusEffect("toggle_telekinesis_pull",
            new ToggleTelekinesisPullEffect(StatusEffectCategory.BENEFICIAL, 0x9b4f96/*Purple*/));

    public static final StatusEffect TOGGLE_TELEKINESIS_MOVE = registerStatusEffect("toggle_telekinesis_move",
            new ToggleTelekinesisMoveEffect(StatusEffectCategory.BENEFICIAL, 0x9b4f96/*Purple*/));

    public static final StatusEffect CHIMAERA_ESSENCE = registerStatusEffect("chimaera_essence",
            new ChimaeraEssenceEffect(StatusEffectCategory.BENEFICIAL, 0x00AA00/*Dark Green*/));

    public static final StatusEffect CHIMAERA_MORPH = registerStatusEffect("chimaera_morph",
            new ChimaeraMorphEffect(StatusEffectCategory.BENEFICIAL, 0xA52A2A/*Brown*/));

    public static final StatusEffect MARK_TARGET_FOR_WOLF = registerStatusEffect("mark_target_for_wolf",
            new MarkTargetForWolfEffect(StatusEffectCategory.BENEFICIAL, 0x90EE90/*Light Green*/));

    public static final StatusEffect CHIMAERA_CALL = registerStatusEffect("chimaera_call",
            new ChimaeraCallEffect(StatusEffectCategory.BENEFICIAL, 0xFF7F50/*Coral*/));

    public static final StatusEffect STAMINA = registerStatusEffect("stamina",
            new StaminaEffect(StatusEffectCategory.BENEFICIAL, 0xFFFF00/*Yellow*/));

    public static final StatusEffect TIRED = registerStatusEffect("tired",
            new TiredEffect(StatusEffectCategory.BENEFICIAL, 0xFF0000/*Red*/));

    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(LorienLegaciesMod.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        LorienLegaciesMod.LOGGER.info("Registering Mod Effects for " + LorienLegaciesMod.MOD_ID);
    }
}
