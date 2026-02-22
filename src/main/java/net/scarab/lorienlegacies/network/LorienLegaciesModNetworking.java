package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.effect.active_effects.*;
import net.scarab.lorienlegacies.effect.toggle_effects.*;
import net.scarab.lorienlegacies.item.ModItems;

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

    public static final Identifier LEFT_CLICK_RELEASE_PACKET = new Identifier("lorienlegacies", "left_click_release");

    public static final Identifier RIGHT_CLICK_PACKET = new Identifier("lorienlegacies", "right_click");

    public static final Identifier TOGGLE_SHOOT_ICEBALL_PACKET = new Identifier("lorienlegacies", "toggle_shoot_iceball");

    public static final Identifier TOGGLE_FREEZE_WATER_PACKET = new Identifier("lorienlegacies", "toggle_freeze_water");

    public static final Identifier TOGGLE_NOVIS_PACKET = new Identifier("lorienlegacies", "toggle_novis");

    public static final Identifier TOGGLE_IMPENETRABLE_SKIN_PACKET = new Identifier("lorienlegacies", "toggle_impenetrable_skin");

    public static final Identifier TOGGLE_INTANGIBILITY_PACKET = new Identifier("lorienlegacies", "toggle_intangibility");

    public static final Identifier START_AVEX_FLIGHT_PACKET = new Identifier("lorienlegacies", "start_avex_flight");

    public static final Identifier INTANGIFLY_PACKET = new Identifier("lorienlegacies", "intangifly");

    public static final Identifier TELEKINESIS_PUSH_PACKET = new Identifier("lorienlegacies", "telekinesis_push");

    public static final Identifier TELEKINESIS_PULL_PACKET = new Identifier("lorienlegacies", "telekinesis_pull");

    public static final Identifier TELEKINESIS_DEFLECT_PACKET = new Identifier("lorienlegacies", "telekinesis_deflect");

    public static final Identifier TELEKINESIS_MOVE_PACKET = new Identifier("lorienlegacies", "telekinesis_move");

    public static final Identifier TOGGLE_LIGHTNING_STRIKE_PACKET = new Identifier("lorienlegacies", "toggle_lightning_strike");

    public static final Identifier TOGGLE_CONJURE_RAIN_PACKET = new Identifier("lorienlegacies", "toggle_conjure_rain");

    public static final Identifier TOGGLE_CONJURE_THUNDER_PACKET = new Identifier("lorienlegacies", "toggle_conjure_thunder");

    public static final Identifier TOGGLE_CONJURE_CLEAR_WEATHER_PACKET = new Identifier("lorienlegacies", "toggle_conjure_clear_weather");

    public static final Identifier TOGGLE_XIMIC_ACCELIX_PACKET = new Identifier("lorienlegacies", "toggle_ximic_accelix");

    public static final Identifier TOGGLE_XIMIC_AVEX_PACKET = new Identifier("lorienlegacies", "toggle_ximic_avex");

    public static final Identifier TOGGLE_XIMIC_GLACEN_PACKET = new Identifier("lorienlegacies", "toggle_ximic_glacen");

    public static final Identifier TOGGLE_XIMIC_LUMEN_PACKET = new Identifier("lorienlegacies", "toggle_ximic_lumen");

    public static final Identifier TOGGLE_XIMIC_NOVIS_PACKET = new Identifier("lorienlegacies", "toggle_ximic_novis");

    public static final Identifier TOGGLE_XIMIC_NOXEN_PACKET = new Identifier("lorienlegacies", "toggle_ximic_noxen");

    public static final Identifier TOGGLE_XIMIC_PONDUS_PACKET = new Identifier("lorienlegacies", "toggle_ximic_pondus");

    public static final Identifier TOGGLE_XIMIC_REGENERAS_PACKET = new Identifier("lorienlegacies", "toggle_ximic_regeneras");

    public static final Identifier TOGGLE_XIMIC_STURMA_PACKET = new Identifier("lorienlegacies", "toggle_ximic_sturma");

    public static final Identifier TOGGLE_XIMIC_SUBMARI_PACKET = new Identifier("lorienlegacies", "toggle_ximic_submari");

    public static final Identifier TOGGLE_XIMIC_TELEKINESIS_PACKET = new Identifier("lorienlegacies", "toggle_ximic_telekinesis");

    public static final Identifier TOGGLE_XIMIC_KINETIC_DETONATION_PACKET = new Identifier("lorienlegacies", "toggle_ximic_kinetic_detonation");

    public static final Identifier TOGGLE_XIMIC_TELETRAS_PACKET = new Identifier("lorienlegacies", "toggle_ximic_teletras_packet");

    public static final Identifier TOGGLE_KINETIC_DETONATION = new Identifier("lorienlegacies", "toggle_kinetic_detonation");

    public static final Identifier TOGGLE_TELETRAS_PACKET = new Identifier("lorienlegacies", "toggle_teletras");

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
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    ToggleShootIceballEffect.toggleShootIceball(player);
                    player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                    player.removeStatusEffect(TOGGLE_LIGHTNING_STRIKE);
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
                if (player.hasStatusEffect(TELEKINESIS)) {
                    TelekinesisEffect.deflect(player);
                    player.removeStatusEffect(PONDUS_COOLDOWN);
                    player.addStatusEffect(new StatusEffectInstance(DEFLECT_STAMINA, 20, 0, false, false, false));
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
                if (player.hasStatusEffect(TELETRAS)) {
                    TeletrasEffect.teleport(player);
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

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(NOVIS)) {
                    ToggleNovisEffect.toggleNovis(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_IMPENETRABLE_SKIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    ToggleImpenetrableSkinEffect.toggleImpenetrableSkin(player);
                    if (!player.hasStatusEffect(PONDUS_STAMINA) && !player.hasStatusEffect(PONDUS_COOLDOWN)) {
                        player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 200, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_INTANGIBILITY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    ToggleIntangibilityEffect.toggleIntangibility(player);
                    if (!player.hasStatusEffect(PONDUS_STAMINA) && !player.hasStatusEffect(PONDUS_COOLDOWN)) {
                        player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 200, 0, false, false, false));
                    }
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_PULL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisPullEffect.toggleTelekinesisPull(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TELEKINESIS_DEFLECT_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELEKINESIS)) {
                    ToggleTelekinesisDeflectEffect.toggleTelekinesisDeflect(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    player.removeStatusEffect(CHIMAERA_MORPH);
                    player.removeStatusEffect(CHIMAERA_CALL);
                    player.removeStatusEffect(MARK_TARGET_FOR_WOLF);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
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
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_RAIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureRainEffect.toggleConjureRain(player);
                    SturmaEffect.conjureRain(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_RAIN);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_THUNDER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureThunderEffect.toggleConjureThunder(player);
                    SturmaEffect.conjureThunder(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_THUNDER);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_CLEAR_WEATHER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    ToggleConjureClearWeatherEffect.toggleConjureClearWeather(player);
                    SturmaEffect.conjureClearWeather(player, (ServerWorld) player.getWorld());
                    player.removeStatusEffect(TOGGLE_CONJURE_CLEAR_WEATHER);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_ACCELIX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicAccelixEffect.toggleXimicAccelix(player);
                    XimicEffect.applyXimicAccelix(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_ACCELIX);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_AVEX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicAvexEffect.toggleXimicAvex(player);
                    XimicEffect.applyXimicAvex(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_AVEX);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_GLACEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicGlacenEffect.toggleXimicGlacen(player);
                    XimicEffect.applyXimicGlacen(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_GLACEN);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_LUMEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicLumenEffect.toggleXimicLumen(player);
                    XimicEffect.applyXimicLumen(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_LUMEN);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicNovisEffect.toggleXimicNovis(player);
                    XimicEffect.applyXimicNovis(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_NOVIS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_NOXEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicNoxenEffect.toggleXimicNoxen(player);
                    XimicEffect.applyXimicNoxen(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_NOXEN);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_PONDUS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicPondusEffect.toggleXimicPondus(player);
                    XimicEffect.applyXimicPondus(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_PONDUS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_REGENERAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicRegenerasEffect.toggleXimicRegeneras(player);
                    XimicEffect.applyXimicRegeneras(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_REGENERAS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_STURMA_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicSturmaEffect.toggleXimicSturma(player);
                    XimicEffect.applyXimicSturma(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_STURMA);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_SUBMARI_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicSubmariEffect.toggleXimicSubmari(player);
                    XimicEffect.applyXimicSubmari(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_SUBMARI);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_TELEKINESIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicTelekinesisEffect.toggleXimicTelekinesis(player);
                    XimicEffect.applyXimicTelekinesis(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_TELEKINESIS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_KINETIC_DETONATION_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicKineticDetonationEffect.toggleXimicKineticDetonation(player);
                    XimicEffect.applyXimicKineticDetonation(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_KINETIC_DETONATION);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_TELETRAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    ToggleXimicTeletrasEffect.toggleXimicTeletras(player);
                    XimicEffect.applyXimicTeletras(player);
                    player.removeStatusEffect(TOGGLE_XIMIC_TELETRAS);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_KINETIC_DETONATION, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(KINETIC_DETONATION)) {
                    ToggleKineticDetonationEffect.toggleKineticDetonation(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_TELETRAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELETRAS)) {
                    ToggleTeletrasEffect.toggleTeletras(player);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
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
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    player.removeStatusEffect(TOGGLE_TELETRAS);
                }
            });
        });
    }
}
