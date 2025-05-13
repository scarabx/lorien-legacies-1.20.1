package net.scarab.lorienlegacies.legacy;

import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisToggles;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisTogglesAccess;

public class TelekinesisManager {

    public static void handleTelekinesis(ServerPlayerEntity player) {

        // Get the player's TelekinesisToggleAccess
        if (player instanceof TelekinesisTogglesAccess accessor) {
            TelekinesisToggles toggles = accessor.getTelekinesisToggles();

            if (toggles.isPushToggle()) {
                // Handle push logic
                TelekinesisLegacy.push(player);
            } else if (toggles.isPullToggle()) {
                // Handle pull logic
                TelekinesisLegacy.pull(player);
            } else if (toggles.isMoveToggle()) {
                // Handle move logic
                TelekinesisLegacy.move(player);
            }
        }
    }

    public static void register() {

    }
}
