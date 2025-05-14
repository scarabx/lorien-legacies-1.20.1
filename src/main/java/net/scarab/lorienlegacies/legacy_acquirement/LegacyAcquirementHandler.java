package net.scarab.lorienlegacies.legacy_acquirement;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LegacyAcquirementHandler {

    // Track stress per player using UUID
    private static final Map<UUID, Integer> playerStress = new HashMap<>();

    public static void stressManager(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        int stress = playerStress.getOrDefault(id, 0);

        // Increase stress when health is low
        if (player.getHealth() <= 2) {
            stress++;
        } else {
            // Reset stress when health is restored
            stress = 0;
        }

        playerStress.put(id, stress);

        // Give legacy if stress exceeds threshold
        if (stress >= 1 && !player.hasStatusEffect(ModEffects.TELEKINESIS)) {
            giveLegacy(player);
        }
    }

    public static void giveLegacy(ServerPlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(
                ModEffects.TELEKINESIS, Integer.MAX_VALUE, 0, false, false, false));
    }

    public static void registerLegacyAcquirementHandler() {
        LorienLegaciesMod.LOGGER.info("Registering Legacy Acquirement Handler for " + LorienLegaciesMod.MOD_ID);
    }
}
