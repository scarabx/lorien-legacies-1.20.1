package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;

import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.effect.active_effects.GlacenEffect;
import net.scarab.lorienlegacies.effect.active_effects.LumenEffect;
import net.scarab.lorienlegacies.effect.active_effects.PondusEffect;
import net.scarab.lorienlegacies.effect.active_effects.TelekinesisEffect;
import net.scarab.lorienlegacies.effect.toggle_effects.*;
import net.scarab.lorienlegacies.legacy.TelekinesisLegacy;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisToggles;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisTogglesAccess;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class LorienLegaciesModNetworking {

    public static final Identifier SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "shoot_fireball");

    public static final Identifier HUMAN_FIREBALL_PACKET = new Identifier("lorienlegacies", "human_fireball");

    public static final Identifier TOGGLE_FLAMING_HANDS_PACKET = new Identifier("lorienlegacies", "toggle_flaming_hands");

    public static final Identifier SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "shoot_iceball");

    public static final Identifier ICICLES_PACKET = new Identifier("lorienlegacies", "icicles");

    public static final Identifier TOGGLE_ICE_HANDS_PACKET = new Identifier("lorienlegacies", "toggle_ice_hands");

    public static final Identifier FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "freeze_water");

    public static final Identifier TOGGLE_SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_fireball");

    public static final Identifier LEFT_CLICK_PACKET = new Identifier("lorienlegacies", "left_click");

    public static final Identifier RIGHT_CLICK_PACKET = new Identifier("lorienlegacies", "right_click");

    public static final Identifier TOGGLE_SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_iceball");

    public static final Identifier TOGGLE_FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "toggle_freeze_water");

    public static final Identifier TOGGLE_REGENERAS_PACKET = new Identifier("lorienlegacies", "toggle_regeneras");

    public static final Identifier TOGGLE_ACCELIX_PACKET = new Identifier("lorienlegacies", "toggle_accelix");

    public static final Identifier TOGGLE_FORTEM_PACKET = new Identifier("lorienlegacies", "toggle_fortem");

    public static final Identifier TOGGLE_NOVIS_PACKET = new Identifier("lorienlegacies", "toggle_novis");

    public static final Identifier TOGGLE_NOXEN_PACKET = new Identifier("lorienlegacies", "toggle_noxen");

    public static final Identifier TOGGLE_IMPENETRABLE_SKIN_PACKET = new Identifier("lorienlegacies", "toggle_impenetrable_skin");

    public static final Identifier TOGGLE_INTANGIBILITY_PACKET = new Identifier("lorienlegacies", "toggle_intangibility");

    public static final Identifier TOGGLE_AVEX_PACKET = new Identifier("lorienlegacies", "toggle_avex");

    public static final Identifier START_AVEX_FLIGHT_PACKET = new Identifier("lorienlegacies", "start_avex_flight");

    public static final Identifier TELEKINESIS_PUSH_PACKET = new Identifier("lorienlegacies", "telekinesis_push");

    public static final Identifier TELEKINESIS_PULL_PACKET = new Identifier("lorienlegacies", "telekinesis_pull");

    public static final Identifier TELEKINESIS_MOVE_PACKET = new Identifier("lorienlegacies", "telekinesis_move");

    public static final Identifier CHIMAERA_MORPH_PACKET = new Identifier("lorienlegacies", "chimaera_morph");

    public static final Identifier CHIMAERA_CALL_PACKET = new Identifier("lorienlegacies", "chimaera_call");

    public static final Identifier MARK_TARGET_FOR_WOLF_PACKET = new Identifier("lorienlegacies", "mark_target_for_wolf");

    public static void registerC2SPackets() {

        ServerPlayNetworking.registerGlobalReceiver(SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(HUMAN_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    LumenEffect.humanFireball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FLAMING_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    ToggleFlamingHandsEffect.toggleFlamingHands(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    GlacenEffect.shootIceball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ICICLES_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    ToggleIciclesEffect.toggleIcicles(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ICE_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    ToggleIceHandsEffect.toggleIceHands(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FREEZE_WATER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    GlacenEffect.freezeWater(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    ToggleShootFireballEffect.toggleShootFireball(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    ToggleShootIceballEffect.toggleShootIceball(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(LEFT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
                if (player.hasStatusEffect(GlACEN)) {
                    GlacenEffect.shootIceball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(RIGHT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                /*if (player.hasStatusEffect(TELEKINESIS)) {
                    TelekinesisEffect.push(player);
                }
                if (player.hasStatusEffect(TELEKINESIS)) {
                    TelekinesisEffect.pull(player);
                }*/
                if (player.hasStatusEffect(CHIMAERA_MORPH)) {
                    MorphHandler.chimaeraMorph(player);
                }
                if (player.hasStatusEffect(CHIMAERA_CALL)) {
                    MorphHandler.teleportChimaera(player);
                }
                if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) {
                    MorphHandler.markTargetForWolf(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FREEZE_WATER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GlACEN)) {
                    ToggleFreezeWaterEffect.toggleFreezeWater(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ACCELIX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ACCELIX)) {
                    ToggleAccelixEffect.toggleAccelix(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_REGENERAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(REGENERAS)) {
                    ToggleRegenerasEffect.toggleRegeneras(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FORTEM_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(FORTEM)) {
                    ToggleFortemEffect.toggleFortem(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(NOVIS)) {
                    ToggleNovisEffect.toggleNovis(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOXEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(NOXEN)) {
                    ToggleNoxenEffect.toggleNoxen(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_IMPENETRABLE_SKIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // Just toggle the TOGGLE_ status effect; do NOT directly add/remove IMPENETRABLE_SKIN here
                ToggleImpenetrableSkinEffect.toggleImpenetrableSkin(player);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_INTANGIBILITY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    PondusEffect.applyIntangibility(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_AVEX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(AVEX)) {
                    ToggleAvexEffect.toggleAvex(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(START_AVEX_FLIGHT_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(AVEX) && player.hasStatusEffect(TOGGLE_AVEX)) {
                    player.startFallFlying();
                }
            });
        });

        /*ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PUSH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisPushEffect.toggleTelekinesisPush(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PULL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisPullEffect.toggleTelekinesisPull(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_MOVE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisMoveEffect.toggleTelekinesisMove(player);
                }
            });
        });*/

        ServerPlayNetworking.registerGlobalReceiver(CHIMAERA_MORPH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(CHIMAERA_MORPH)) {
                    player.removeStatusEffect(CHIMAERA_MORPH);
                } else {
                    player.addStatusEffect(new StatusEffectInstance(CHIMAERA_MORPH, Integer.MAX_VALUE, 0, false, false, false));
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(CHIMAERA_CALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(CHIMAERA_CALL)) {
                    player.removeStatusEffect(CHIMAERA_CALL);
                } else {
                    player.addStatusEffect(new StatusEffectInstance(CHIMAERA_CALL, Integer.MAX_VALUE, 0, false, false, false));
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(MARK_TARGET_FOR_WOLF_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) {
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                } else {
                    player.addStatusEffect(new StatusEffectInstance(MARK_TARGET_FOR_WOLF, Integer.MAX_VALUE, 0, false, false, false));
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                }
            });
        });

        // Register the packet receiver to handle toggle requests
        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PUSH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player instanceof TelekinesisTogglesAccess accessor) {
                    TelekinesisToggles toggles = accessor.getTelekinesisToggles();
                    TelekinesisToggles.toggleAbility(player.getWorld(), player, toggles, "push");
                }
            });
        });

        // Register the packet receiver to handle toggle requests
        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PULL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player instanceof TelekinesisTogglesAccess accessor) {
                    TelekinesisToggles toggles = accessor.getTelekinesisToggles();
                    TelekinesisToggles.toggleAbility(player.getWorld(), player, toggles, "pull");
                }
            });
        });

        // Register the packet receiver to handle toggle requests
        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_MOVE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player instanceof TelekinesisTogglesAccess accessor) {
                    TelekinesisToggles toggles = accessor.getTelekinesisToggles();
                    TelekinesisToggles.toggleAbility(player.getWorld(), player, toggles, "move");
                }
            });
        });
    }
}
