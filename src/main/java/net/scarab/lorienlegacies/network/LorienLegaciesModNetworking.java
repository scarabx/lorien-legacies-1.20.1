package net.scarab.lorienlegacies.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.effect.active_effects.*;
import net.scarab.lorienlegacies.effect.toggle_effects.*;

import java.util.HashSet;
import java.util.Set;

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

    public static final Identifier SYNC_TOGGLES_PACKET = new Identifier("lorienlegacies", "sync_toggles");

    public static final Identifier TOGGLE_SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_iceball");

    public static final Identifier TOGGLE_FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "toggle_freeze_water");

    public static final Identifier TOGGLE_FORTEM_PACKET = new Identifier("lorienlegacies", "toggle_fortem");

    public static final Identifier TOGGLE_NOVIS_PACKET = new Identifier("lorienlegacies", "toggle_novis");

    public static final Identifier TOGGLE_IMPENETRABLE_SKIN_PACKET = new Identifier("lorienlegacies", "toggle_impenetrable_skin");

    public static final Identifier TOGGLE_INTANGIBILITY_PACKET = new Identifier("lorienlegacies", "toggle_intangibility");

    public static final Identifier START_AVEX_FLIGHT_PACKET = new Identifier("lorienlegacies", "start_avex_flight");

    public static final Identifier INTANGIFLY_PACKET = new Identifier("lorienlegacies", "intangifly");

    public static final Identifier TELEKINESIS_PUSH_PACKET = new Identifier("lorienlegacies", "telekinesis_push");

    public static final Identifier TELEKINESIS_PULL_PACKET = new Identifier("lorienlegacies", "telekinesis_pull");

    public static final Identifier TELEKINESIS_MOVE_PACKET = new Identifier("lorienlegacies", "telekinesis_move");

    public static final Identifier TOGGLE_LIGHTNING_STRIKE_PACKET = new Identifier("lorienlegacies", "toggle_lightning_strike");

    public static final Identifier TOGGLE_CONJURE_RAIN_PACKET = new Identifier("lorienlegacies", "toggle_conjure_rain");

    public static final Identifier TOGGLE_CONJURE_THUNDER_PACKET = new Identifier("lorienlegacies", "toggle_conjure_thunder");

    public static final Identifier TOGGLE_CONJURE_CLEAR_WEATHER_PACKET = new Identifier("lorienlegacies", "toggle_conjure_clear_weather");

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
                    //boolean wasFlamingHandsToggledOn = !player.hasStatusEffect(TOGGLE_FLAMING_HANDS); // being turned on
                    ToggleFlamingHandsEffect.toggleFlamingHands(player);
                    //if (wasFlamingHandsToggledOn && !player.hasStatusEffect(STAMINA)) {
                        //player.addStatusEffect(new StatusEffectInstance(ModEffects.STAMINA, 6000, 0, false, false, false));
                    //}
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    GlacenEffect.shootIceball(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ICICLES_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    ToggleIciclesEffect.toggleIcicles(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ICE_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    ToggleIceHandsEffect.toggleIceHands(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FREEZE_WATER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    GlacenEffect.freezeWater(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    ToggleShootFireballEffect.toggleShootFireball(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);
                    player.removeStatusEffect(TOGGLE_LIGHTNING_STRIKE);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)) toggled.add(TOGGLE_SHOOT_FIREBALL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_SHOOT_ICEBALL)) toggled.add(TOGGLE_SHOOT_ICEBALL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_LIGHTNING_STRIKE)) toggled.add(TOGGLE_LIGHTNING_STRIKE_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    ToggleShootIceballEffect.toggleShootIceball(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                    player.removeStatusEffect(TOGGLE_LIGHTNING_STRIKE);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_SHOOT_ICEBALL)) toggled.add(TOGGLE_SHOOT_ICEBALL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)) toggled.add(SHOOT_FIREBALL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_LIGHTNING_STRIKE)) toggled.add(TOGGLE_LIGHTNING_STRIKE_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(LEFT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
                if (player.hasStatusEffect(GLACEN)) {
                    GlacenEffect.shootIceball(player);
                }
                if (player.hasStatusEffect(STURMA)) {
                    SturmaEffect.lightningStrike(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(RIGHT_CLICK_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    TelekinesisEffect.push(player);
                }
                if (player.hasStatusEffect(TELEKINESIS)) {
                    TelekinesisEffect.pull(player);
                }
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
                if (player.hasStatusEffect(GLACEN)) {
                    ToggleFreezeWaterEffect.toggleFreezeWater(player);
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

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_IMPENETRABLE_SKIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // Just toggle the TOGGLE_ status effect; do NOT directly add/remove IMPENETRABLE_SKIN here
                ToggleImpenetrableSkinEffect.toggleImpenetrableSkin(player);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_INTANGIBILITY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    ToggleIntangibilityEffect.toggleIntangibility(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(INTANGIFLY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(AVEX)) {
                    IntangiFlyEffect.toggleIntangiFly(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(START_AVEX_FLIGHT_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(AVEX)) {
                    player.startFallFlying();
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PUSH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisPushEffect.toggleTelekinesisPush(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) toggled.add(TELEKINESIS_PUSH_PACKET);
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) toggled.add(TELEKINESIS_PULL_PACKET);
                    if (player.hasStatusEffect(CHIMAERA_MORPH)) toggled.add(CHIMAERA_MORPH_PACKET);
                    if (player.hasStatusEffect(CHIMAERA_CALL)) toggled.add(CHIMAERA_CALL_PACKET);
                    if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) toggled.add(MARK_TARGET_FOR_WOLF_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PULL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisPullEffect.toggleTelekinesisPull(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) toggled.add(TELEKINESIS_PULL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) toggled.add(TELEKINESIS_PUSH_PACKET);
                    if (player.hasStatusEffect(CHIMAERA_MORPH)) toggled.add(CHIMAERA_MORPH_PACKET);
                    if (player.hasStatusEffect(CHIMAERA_CALL)) toggled.add(CHIMAERA_CALL_PACKET);
                    if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) toggled.add(MARK_TARGET_FOR_WOLF_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_MOVE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisMoveEffect.toggleTelekinesisMove(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_LIGHTNING_STRIKE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleLightningStrikeEffect.toggleLightningStrike(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                    player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_LIGHTNING_STRIKE)) toggled.add(TOGGLE_LIGHTNING_STRIKE_PACKET);
                    if (player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)) toggled.add(TOGGLE_SHOOT_FIREBALL_PACKET);
                    if (player.hasStatusEffect(TOGGLE_SHOOT_ICEBALL)) toggled.add(SHOOT_ICEBALL_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_RAIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureRainEffect.toggleConjureRain(player);
                    SturmaEffect.conjureRain(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_RAIN);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_CONJURE_RAIN)) toggled.add(TOGGLE_CONJURE_RAIN_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_THUNDER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureThunderEffect.toggleConjureThunder(player);
                    SturmaEffect.conjureThunder(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_THUNDER);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_CONJURE_THUNDER)) toggled.add(TOGGLE_CONJURE_THUNDER_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_CLEAR_WEATHER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureClearWeatherEffect.toggleConjureClearWeather(player);
                    SturmaEffect.conjureClearWeather(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_CLEAR_WEATHER);

                    // Sync to client
                    PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                    Set<Identifier> toggled = new HashSet<>();
                    if (player.hasStatusEffect(TOGGLE_CONJURE_CLEAR_WEATHER)) toggled.add(TOGGLE_CONJURE_CLEAR_WEATHER_PACKET);
                    syncBuf.writeVarInt(toggled.size());
                    for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                    ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(CHIMAERA_MORPH_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(CHIMAERA_MORPH)) {
                    player.removeStatusEffect(CHIMAERA_MORPH);
                } else {
                    player.addStatusEffect(new StatusEffectInstance(CHIMAERA_MORPH, Integer.MAX_VALUE, 0, false, false, false));
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                }

                // Sync to client
                PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                Set<Identifier> toggled = new HashSet<>();
                if (player.hasStatusEffect(CHIMAERA_CALL)) toggled.add(CHIMAERA_CALL_PACKET);
                if (player.hasStatusEffect(CHIMAERA_MORPH)) toggled.add(CHIMAERA_MORPH_PACKET);
                if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) toggled.add(MARK_TARGET_FOR_WOLF_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) toggled.add(TELEKINESIS_PUSH_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) toggled.add(TELEKINESIS_PULL_PACKET);
                syncBuf.writeVarInt(toggled.size());
                for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                }

                // Sync to client
                PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                Set<Identifier> toggled = new HashSet<>();
                if (player.hasStatusEffect(CHIMAERA_MORPH)) toggled.add(CHIMAERA_MORPH_PACKET);
                if (player.hasStatusEffect(CHIMAERA_CALL)) toggled.add(CHIMAERA_CALL_PACKET);
                if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) toggled.add(MARK_TARGET_FOR_WOLF_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) toggled.add(TELEKINESIS_PUSH_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) toggled.add(TELEKINESIS_PULL_PACKET);
                syncBuf.writeVarInt(toggled.size());
                for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                }

                // Sync to client
                PacketByteBuf syncBuf = new PacketByteBuf(Unpooled.buffer());
                Set<Identifier> toggled = new HashSet<>();
                if (player.hasStatusEffect(CHIMAERA_MORPH)) toggled.add(CHIMAERA_MORPH_PACKET);
                if (player.hasStatusEffect(CHIMAERA_CALL)) toggled.add(CHIMAERA_CALL_PACKET);
                if (player.hasStatusEffect(MARK_TARGET_FOR_WOLF)) toggled.add(MARK_TARGET_FOR_WOLF_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) toggled.add(TELEKINESIS_PUSH_PACKET);
                if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) toggled.add(TELEKINESIS_PULL_PACKET);
                syncBuf.writeVarInt(toggled.size());
                for (Identifier id : toggled) syncBuf.writeIdentifier(id);
                ServerPlayNetworking.send(player, SYNC_TOGGLES_PACKET, syncBuf);
            });
        });
    }
}
