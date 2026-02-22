package net.scarab.lorienlegacies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.util.hit.HitResult;

import static net.scarab.lorienlegacies.network.LorienLegaciesModNetworking.*;

public class ClickTracker implements ClientModInitializer {

    // Tracks whether the player was left-clicking (swinging) in the previous tick

    private boolean wasLeftClicking = false;

    // Tracks whether the player was right-clicking in the previous tick

    private boolean wasRightClicking = false;

    @Override
    public void onInitializeClient() {

        // Register a client-side tick event that runs code at the end of every client tick (after game logic, before render)

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // Ensure the client has a player and world context before processing input

            if (client.player != null && client.world != null) {

                // Detect left-click (swing)

                boolean leftClickingNow = client.options.attackKey.isPressed();

                if (leftClickingNow && !wasLeftClicking) {

                    ClientPlayNetworking.send(LEFT_CLICK_PACKET, PacketByteBufs.empty());

                }

                if (!leftClickingNow && wasLeftClicking) {

                    ClientPlayNetworking.send(LEFT_CLICK_RELEASE_PACKET, PacketByteBufs.empty());

                }

                wasLeftClicking = leftClickingNow;

                // Detect right-click in air

                boolean rightClickingNow = client.options.useKey.isPressed();

                boolean inAir = client.crosshairTarget == null ||

                        client.crosshairTarget.getType().name().equals("MISS");

                if (rightClickingNow && !wasRightClicking && inAir) {

                    ClientPlayNetworking.send(RIGHT_CLICK_PACKET, PacketByteBufs.empty());

                }

                // Detect block and entity right-clicks

                HitResult hitResult = client.crosshairTarget;

                if (rightClickingNow && !wasRightClicking && hitResult != null) {

                    if (hitResult.getType() == HitResult.Type.BLOCK) {

                        // Block right-click logic

                        ClientPlayNetworking.send(RIGHT_CLICK_PACKET, PacketByteBufs.empty());

                    } else if (hitResult.getType() == HitResult.Type.ENTITY) {

                        // Entity right-click logic

                        ClientPlayNetworking.send(RIGHT_CLICK_PACKET, PacketByteBufs.empty());

                    }
                }

                wasRightClicking = rightClickingNow;  // Update for next tick

            }
        });
    }
}
