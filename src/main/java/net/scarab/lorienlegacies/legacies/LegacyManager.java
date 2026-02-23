package net.scarab.lorienlegacies.legacies;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LegacyManager {

    // Store each player's legacy data
    public static final Map<UUID, PlayerLegacyData> playerData = new HashMap<>();

    // All possible legacies
    public enum Legacy {
        LUMEN,
        GLACEN,
        NOVIS,
        NOXEN
    }

    // Set a legacy for a player
    public static void setLegacy(PlayerEntity player, Legacy legacyType) {
        playerData.put(player.getUuid(), new PlayerLegacyData(legacyType));
        player.sendMessage(Text.literal("Legacy set to " + legacyType.name()), false);
    }

    // Toggle a specific legacy for a player
    public static void toggleLegacy(PlayerEntity player, Legacy legacyType) {
        PlayerLegacyData data = playerData.get(player.getUuid());

        // If the player has no legacy data, assign it first
        if (data == null) {
            data = new PlayerLegacyData(legacyType);
            playerData.put(player.getUuid(), data);
        }

        // Only toggle if the player's current legacy matches the requested one
        if (data.legacy != legacyType) return;

        data.active = !data.active;

        if (data.active) {
            player.sendMessage(Text.literal(legacyType.name() + " activated"), false);

            // Apply legacy-specific effects
            if (legacyType == Legacy.NOVIS) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.INVISIBILITY,
                        Integer.MAX_VALUE,
                        0,
                        false,
                        false,
                        false
                ));
            }

            // TODO: Add other legacy-specific activation effects here

        } else {
            player.sendMessage(Text.literal(legacyType.name() + " deactivated"), false);

            // Remove legacy-specific effects
            if (legacyType == Legacy.NOVIS) {
                player.removeStatusEffect(StatusEffects.INVISIBILITY);
            }

            // TODO: Add other legacy-specific deactivation effects here
        }
    }
}