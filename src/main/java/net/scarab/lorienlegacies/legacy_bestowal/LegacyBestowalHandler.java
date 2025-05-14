package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler {

    // Track stress per player using UUID
    private static final Map<UUID, Integer> playerStress = new HashMap<>();
    private static final List<StatusEffect> LEGACY_POOL = List.of(
            ModEffects.TELEKINESIS,
            ModEffects.LUMEN,
            ModEffects.AVEX,
            ModEffects.PONDUS,
            ModEffects.GlACEN
    );

    public static void stressManager(ServerPlayerEntity player) {

        UUID id = player.getUuid();
        int stress = playerStress.getOrDefault(id, 0);

        // Increase stress when health is low
        if (player.getHealth() <= 2) {
            stress = stress + 40;
            player.sendMessage(Text.literal("Stress increased by 40"));
        }

        if (player.hasStatusEffect(StatusEffects.POISON) && player.isOnFire() && player.isSubmergedInWater() && player.getAir() < player.getMaxAir()) {
            stress = stress + 10;
            player.sendMessage(Text.literal("Stress increased by 10"));
        }

        // Surrounded by hostiles
        if (player.getWorld() instanceof ServerWorld serverWorld) {
            long nearbyHostiles = serverWorld.getEntitiesByClass(HostileEntity.class, player.getBoundingBox().expand(5), e -> true).stream().count();
            if (nearbyHostiles >= 3) {
                stress = stress + 15;
                player.sendMessage(Text.literal("Stress increased by 15"));
            }
        }

        if (player.getHealth() == player.getMaxHealth()) {
            stress = stress - 10;
            player.sendMessage(Text.literal("Stress decreased by 10"));
        }

        if (!player.hasStatusEffect(StatusEffects.POISON) && !player.isOnFire() && !player.isSubmergedInWater() && !(player.getAir() < player.getMaxAir()) && player.getAttacker() == null) {
            stress = stress - 1;
            player.sendMessage(Text.literal("Stress decreased by 1"));
        }

        if (player.isSleeping()) {
            stress = stress - 10;
            player.sendMessage(Text.literal("Stress decreased by 10"));
        }

        if (player.isUsingItem() && player.getActiveItem().getItem().isFood()) {
            stress = stress - 3;
            player.sendMessage(Text.literal("Stress decreased by 3"));
        }

        playerStress.put(id, stress);

        // Give legacy if stress exceeds threshold
        if (stress == 100) {
            giveLegacy(player);
            stress = 0;
        }

        // Update the player's stress value in the map
        playerStress.put(id, stress);
    }

    public static void onPetDeath(ServerPlayerEntity owner) {
        UUID id = owner.getUuid();
        int stress = playerStress.getOrDefault(id, 0);

        stress += 30;
        playerStress.put(id, stress);
    }

    public static void giveLegacy(ServerPlayerEntity player) {
        // Choose a random effect from the LEGACY_POOL using ThreadLocalRandom
        StatusEffect randomEffect = LEGACY_POOL.get(ThreadLocalRandom.current().nextInt(LEGACY_POOL.size()));

        // Check if the player already has a legacy effect from the LEGACY_POOL
        if (!player.hasStatusEffect(randomEffect)) {
            // Apply the selected effect if the player doesn't have it already
            player.addStatusEffect(new StatusEffectInstance(randomEffect, Integer.MAX_VALUE, 0, false, false, false));
            // Notify the player which legacy they received
            player.sendMessage(Text.literal("You have received the " + randomEffect.getName().getString() + " legacy!"), false);
        } else {
            // Notify the player in chat that they already have this legacy
            player.sendMessage(Text.literal(player.getName().getString() + " already has the " + randomEffect.getName().getString() + " legacy."), false);
        }
    }

    public static void registerLegacyBestowalHandler() {
        LorienLegaciesMod.LOGGER.info("Registering Legacy Acquirement Handler for " + LorienLegaciesMod.MOD_ID);
    }
}
