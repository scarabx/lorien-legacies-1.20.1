package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.effect.*;
import net.scarab.lorienlegacies.effect.active_effects.GlacenEffect;
import net.scarab.lorienlegacies.effect.active_effects.LumenEffect;
import net.scarab.lorienlegacies.effect.active_effects.PondusEffect;
import net.scarab.lorienlegacies.effect.active_effects.TelekinesisEffect;
import net.scarab.lorienlegacies.effect.toggle_effects.*;
import net.scarab.lorienlegacies.item.ChimaeraStaffItem;
import net.scarab.lorienlegacies.item.ModItems;

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

    public static void registerC2SPackets() {

        ServerPlayNetworking.registerGlobalReceiver(SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(HUMAN_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    LumenEffect.humanFireball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FLAMING_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    ToggleFlamingHandsEffect.toggleFlamingHands(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    GlacenEffect.shootIceball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ICICLES_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    ToggleIciclesEffect.toggleIcicles(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ICE_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    ToggleIceHandsEffect.toggleIceHands(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FREEZE_WATER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    GlacenEffect.freezeWater(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    ToggleShootFireballEffect.toggleShootFireball(player);
                    player.removeStatusEffect(ModEffects.TOGGLE_SHOOT_ICEBALL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    ToggleShootIceballEffect.toggleShootIceball(player);
                    player.removeStatusEffect(ModEffects.TOGGLE_SHOOT_FIREBALL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(LEFT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    GlacenEffect.shootIceball(player);
                }
                if (player.getMainHandStack().isOf(ModItems.CHIMAERA_STAFF)) {
                    ChimaeraStaffItem.chimaeraMorph(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(RIGHT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.TELEKINESIS)) {
                    TelekinesisEffect.push(player);
                }
                if (player.hasStatusEffect(ModEffects.TELEKINESIS)) {
                    TelekinesisEffect.pull(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FREEZE_WATER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.GlACEN)) {
                    ToggleFreezeWaterEffect.toggleFreezeWater(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ACCELIX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.ACCELIX)) {
                    ToggleAccelixEffect.toggleAccelix(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_REGENERAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.REGENERAS)) {
                    ToggleRegenerasEffect.toggleRegeneras(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_FORTEM_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.FORTEM)) {
                    ToggleFortemEffect.toggleFortem(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.NOVIS)) {
                    ToggleNovisEffect.toggleNovis(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOXEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.NOXEN)) {
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
                if (player.hasStatusEffect(ModEffects.PONDUS)) {
                    PondusEffect.applyIntangibility(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_AVEX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.AVEX)) {
                    ToggleAvexEffect.toggleAvex(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(START_AVEX_FLIGHT_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.AVEX) && player.hasStatusEffect(ModEffects.TOGGLE_AVEX)) {
                    player.startFallFlying();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PUSH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.TELEKINESIS)) {
                    ToggleTelekinesisPushEffect.toggleTelekinesisPush(player);
                    player.removeStatusEffect(ModEffects.TOGGLE_TELEKINESIS_PULL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PULL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.TELEKINESIS)) {
                    ToggleTelekinesisPullEffect.toggleTelekinesisPull(player);
                    player.removeStatusEffect(ModEffects.TOGGLE_TELEKINESIS_PUSH);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_MOVE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.TELEKINESIS)) {
                    ToggleTelekinesisMoveEffect.toggleTelekinesisMove(player);
                }
            });
        });
    }
}
