package net.scarab.lorienlegacies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

import static net.scarab.lorienlegacies.network.LorienLegaciesModNetworking.LEFT_CLICK_PACKET;
import static net.scarab.lorienlegacies.network.LorienLegaciesModNetworking.RIGHT_CLICK_PACKET;

public class ClickTracker implements ClientModInitializer {
    private boolean wasLeftClicking = false;
    private boolean wasRightClicking = false;

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {

                // Detect left-click (swing)
                boolean leftClickingNow = client.player.handSwinging;
                if (leftClickingNow && !wasLeftClicking) {
                    ClientPlayNetworking.send(LEFT_CLICK_PACKET, PacketByteBufs.empty());
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
