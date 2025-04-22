package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.effect.LumenEffect;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.effect.ToggleFlamingHandsEffect;

public class LorienLegaciesModNetworking {

    public static final Identifier SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "shoot_fireball");

    public static final Identifier HUMAN_FIREBALL_PACKET = new Identifier("lorienlegacies", "human_fireball");

    public static final Identifier TOGGLE_FLAMING_HANDS_PACKET = new Identifier("lorienlegacies", "toggle_flaming_hands");

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
    }
}
