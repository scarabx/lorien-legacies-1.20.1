package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.effect.*;
import net.scarab.lorienlegacies.effect.active_effects.GlacenEffect;
import net.scarab.lorienlegacies.effect.active_effects.LumenEffect;
import net.scarab.lorienlegacies.effect.toggle_effects.*;

public class LorienLegaciesModNetworking {

    public static final Identifier SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "shoot_fireball");

    public static final Identifier HUMAN_FIREBALL_PACKET = new Identifier("lorienlegacies", "human_fireball");

    public static final Identifier TOGGLE_FLAMING_HANDS_PACKET = new Identifier("lorienlegacies", "toggle_flaming_hands");

    public static final Identifier SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "shoot_iceball");

    public static final Identifier ICICLES_PACKET = new Identifier("lorienlegacies", "icicles");

    public static final Identifier TOGGLE_ICE_HANDS_PACKET = new Identifier("lorienlegacies", "toggle_ice_hands");

    public static final Identifier FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "freeze_water");

    public static final Identifier TOGGLE_SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_fireball");

    public static final Identifier SWING_PACKET = new Identifier("lorienlegacies", "swing");

    public static final Identifier TOGGLE_SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_iceball");

    public static final Identifier TOGGLE_FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "toggle_freeze_water");

    public static final Identifier TOGGLE_REGENERAS_PACKET = new Identifier("lorienlegacies", "toggle_regeneras");

    public static final Identifier TOGGLE_ACCELIX_PACKET = new Identifier("lorienlegacies", "toggle_accelix");

    public static final Identifier TOGGLE_FORTEM_PACKET = new Identifier("lorienlegacies", "toggle_fortem");

    public static final Identifier TOGGLE_NOVIS_PACKET = new Identifier("lorienlegacies", "toggle_novis");

    public static final Identifier TOGGLE_NOXEN_PACKET = new Identifier("lorienlegacies", "toggle_noxen");

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

        ServerPlayNetworking.registerGlobalReceiver(SWING_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    GlacenEffect.shootIceball(player);
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
    }
}
