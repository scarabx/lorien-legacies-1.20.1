package net.scarab.lorienlegacies.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.effect.LumenEffect;
import net.scarab.lorienlegacies.effect.ModEffects;

public class LorienLegaciesModNetworking {
    public static final Identifier SHOOT_FIREBALL_PACKET = new Identifier("lorienlegacies", "shoot_fireball");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SHOOT_FIREBALL_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(ModEffects.LUMEN)) {
                    LumenEffect.shootFireball(player);
                }
            });
        });
    }
}
