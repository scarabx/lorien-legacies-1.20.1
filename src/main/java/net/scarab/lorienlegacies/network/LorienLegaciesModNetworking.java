package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.effect.active_effects.*;
import net.scarab.lorienlegacies.effect.toggle_effects.*;

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

    public static final Identifier TOGGLE_KINETIC_DETONATION_PACKET = new Identifier("lorienlegacies", "toggle_kinetic_detonation");

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
                    if (player.hasStatusEffect(TOGGLE_FLAMING_HANDS)) {
                        player.removeStatusEffect(TOGGLE_FLAMING_HANDS);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_FLAMING_HANDS, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_ICICLES)) {
                        player.removeStatusEffect(TOGGLE_ICICLES);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_ICICLES, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_ICE_HANDS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    if (player.hasStatusEffect(TOGGLE_ICE_HANDS)) {
                        player.removeStatusEffect(TOGGLE_ICE_HANDS);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_ICE_HANDS, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL)) {
                        player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_SHOOT_FIREBALL, Integer.MAX_VALUE, 0, false, false, false));
                    }
                    player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);
                    player.removeStatusEffect(TOGGLE_LIGHTNING_STRIKE);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOOT_ICEBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(GLACEN)) {
                    if (player.hasStatusEffect(TOGGLE_SHOOT_ICEBALL)) {
                        player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_SHOOT_ICEBALL, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_FREEZE_WATER)) {
                        player.removeStatusEffect(TOGGLE_FREEZE_WATER);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_FREEZE_WATER, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(NOVIS)) {
                    if (player.hasStatusEffect(TOGGLE_NOVIS)) {
                        player.removeStatusEffect(TOGGLE_NOVIS);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_NOVIS, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_IMPENETRABLE_SKIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    if (player.hasStatusEffect(TOGGLE_IMPENETRABLE_SKIN)) {
                        player.removeStatusEffect(TOGGLE_IMPENETRABLE_SKIN);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_IMPENETRABLE_SKIN, Integer.MAX_VALUE, 0, false, false, false));
                    }
                    if (!player.hasStatusEffect(PONDUS_STAMINA) && !player.hasStatusEffect(PONDUS_COOLDOWN)) {
                        player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 12000, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_INTANGIBILITY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(PONDUS)) {
                    if (player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {
                        player.removeStatusEffect(TOGGLE_INTANGIBILITY);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_INTANGIBILITY, Integer.MAX_VALUE, 0, false, false, false));
                    }
                    if (!player.hasStatusEffect(PONDUS_STAMINA) && !player.hasStatusEffect(PONDUS_COOLDOWN)) {
                        player.addStatusEffect(new StatusEffectInstance(PONDUS_STAMINA, 200, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(INTANGIFLY_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(AVEX)) {
                    if (player.hasStatusEffect(INTANGIFLY)) {
                        player.removeStatusEffect(INTANGIFLY);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(INTANGIFLY, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PUSH)) {
                        player.removeStatusEffect(TOGGLE_TELEKINESIS_PUSH);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_TELEKINESIS_PUSH, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_PULL)) {
                        player.removeStatusEffect(TOGGLE_TELEKINESIS_PULL);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_TELEKINESIS_PULL, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_DEFLECT)) {
                        player.removeStatusEffect(TOGGLE_TELEKINESIS_DEFLECT);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_TELEKINESIS_DEFLECT, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
                    if (player.hasStatusEffect(TOGGLE_TELEKINESIS_MOVE)) {
                        player.removeStatusEffect(TOGGLE_TELEKINESIS_MOVE);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_TELEKINESIS_MOVE, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_LIGHTNING_STRIKE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    if (player.hasStatusEffect(TOGGLE_LIGHTNING_STRIKE)) {
                        player.removeStatusEffect(TOGGLE_LIGHTNING_STRIKE);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_LIGHTNING_STRIKE, Integer.MAX_VALUE, 0, false, false, false));
                    }
                    player.removeStatusEffect(TOGGLE_SHOOT_FIREBALL);
                    player.removeStatusEffect(TOGGLE_SHOOT_ICEBALL);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_RAIN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    player.addStatusEffect(new StatusEffectInstance(TOGGLE_CONJURE_RAIN, 0, 0, false, false, false));
                    SturmaEffect.conjureRain(player, (ServerWorld) player.getWorld());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_THUNDER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    player.addStatusEffect(new StatusEffectInstance(TOGGLE_CONJURE_THUNDER, 0, 0, false, false, false));
                    SturmaEffect.conjureThunder(player, (ServerWorld) player.getWorld());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_CONJURE_CLEAR_WEATHER_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(STURMA)) {
                    player.addStatusEffect(new StatusEffectInstance(TOGGLE_CONJURE_CLEAR_WEATHER, 0, 0, false, false, false));
                    SturmaEffect.conjureClearWeather(player, (ServerWorld) player.getWorld());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_ACCELIX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(ACCELIX, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_AVEX_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(AVEX, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_GLACEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(GLACEN, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_LUMEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(LUMEN, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_NOVIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(NOVIS, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_NOXEN_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(NOXEN, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_PONDUS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(PONDUS, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_REGENERAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(REGENERAS, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_STURMA_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(STURMA, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_SUBMARI_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(SUBMARI, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_TELEKINESIS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(TELEKINESIS, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_KINETIC_DETONATION_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(KINETIC_DETONATION, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_XIMIC_TELETRAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(XIMIC)) {
                    player.addStatusEffect(new StatusEffectInstance(TELETRAS, Integer.MAX_VALUE, 0, false, false, false));
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_KINETIC_DETONATION_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(KINETIC_DETONATION)) {
                    if (player.hasStatusEffect(TOGGLE_KINETIC_DETONATION)) {
                        player.removeStatusEffect(TOGGLE_KINETIC_DETONATION);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_KINETIC_DETONATION, Integer.MAX_VALUE, 0, false, false, false));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_TELETRAS_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(TELETRAS)) {
                    if (player.hasStatusEffect(TOGGLE_TELETRAS)) {
                        player.removeStatusEffect(TOGGLE_TELETRAS);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(TOGGLE_TELETRAS, Integer.MAX_VALUE, 0, false, false, false));
                    }
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
