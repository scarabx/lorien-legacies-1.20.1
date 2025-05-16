package net.scarab.lorienlegacies.stats;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillTracker {

    private static final Map<UUID, Integer> playerKills = new HashMap<>();
    private static final int KILLS_TO_UNLOCK = 2;

    public static void onMobKilled(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int kills = playerKills.getOrDefault(id, 0) + 1;

        playerKills.put(id, kills);

        player.sendMessage(Text.literal("Kills: " + kills), true);

        if (kills == KILLS_TO_UNLOCK) {
            unlockSomething(player);
        }
    }

    private static void unlockSomething(ServerPlayerEntity player) {
        // Your custom reward logic here
        player.sendMessage(Text.literal("You have unlocked a secret reward for 100 kills!"), false);
    }

    public static int getKills(ServerPlayerEntity player) {
        return playerKills.getOrDefault(player.getUuid(), 0);
    }

    public static void setKills(ServerPlayerEntity player, int kills) {
        playerKills.put(player.getUuid(), kills);
    }
}
