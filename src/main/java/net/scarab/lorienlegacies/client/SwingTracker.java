package net.scarab.lorienlegacies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import static net.scarab.lorienlegacies.network.LorienLegaciesModNetworking.SWING_PACKET;

public class SwingTracker implements ClientModInitializer {
    private boolean wasSwinging = false; // Track if the player was swinging last tick

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                boolean swingingNow = client.player.handSwinging;
                if (swingingNow && !wasSwinging) {
                    // Player just started swinging → send packet
                    ClientPlayNetworking.send(SWING_PACKET, PacketByteBufs.empty());
                }
                wasSwinging = swingingNow;  // Update for next tick
            }
        });
    }
}
