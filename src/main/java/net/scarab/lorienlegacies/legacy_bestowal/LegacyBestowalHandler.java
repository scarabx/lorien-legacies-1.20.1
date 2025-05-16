package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler {

    private static final Map<UUID, Integer> playerStress = new HashMap<>();
    private static final Map<UUID, Long> lastLegacyTime = new HashMap<>();

    private static final Map<UUID, Long> lastLowHealthTime = new HashMap<>();
    private static final Map<UUID, Long> lastMultiThreatTime = new HashMap<>();
    private static final Map<UUID, Long> lastFullHealthTime = new HashMap<>();
    private static final Map<UUID, Long> lastRestingTime = new HashMap<>();
    private static final Map<UUID, Long> lastSleepTime = new HashMap<>();
    private static final Map<UUID, Long> lastEatingTime = new HashMap<>();

    private static final Map<StatusEffect, Integer> LEGACY_POOL = Map.of(
            ModEffects.TELEKINESIS, 10,
            ModEffects.LUMEN, 8,
            ModEffects.AVEX, 6,
            ModEffects.PONDUS, 4,
            ModEffects.GLACEN, 4,
            ModEffects.ACCELIX, 3,
            ModEffects.FORTEM, 2,
            ModEffects.NOVIS, 2,
            ModEffects.NOXEN, 1,
            ModEffects.REGENERAS, 1
    );

    private static final long LEGACY_COOLDOWN_TICKS = 20 * 60 * 5; // 5 minutes
    private static final int TICKS_PER_SECOND = 20;
    private static final int THROTTLE_TICKS = TICKS_PER_SECOND * 10; // 10 seconds
    private static final int MAX_STRESS = 150;

    public static void stressManager(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        long time = player.getServerWorld().getTime();
        int stress = playerStress.getOrDefault(id, 0);

        // Increase stress
        if (player.getHealth() <= 2 && time - lastLowHealthTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress += 3;
            lastLowHealthTime.put(id, time);
        }

        if ((player.hasStatusEffect(StatusEffects.POISON) || player.isOnFire() || (player.isSubmergedInWater() && player.getAir() <= 0)) &&
                time - lastMultiThreatTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress += 3;
            lastMultiThreatTime.put(id, time);
        }

        if (player.getWorld() instanceof ServerWorld serverWorld) {
            long nearbyHostiles = serverWorld.getEntitiesByClass(
                    HostileEntity.class,
                    player.getBoundingBox().expand(5),
                    e -> true
            ).stream().count();
            if (nearbyHostiles >= 3 && time - lastMultiThreatTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
                stress += 3;
                lastMultiThreatTime.put(id, time);
            }
        }

        // Decrease stress
        if (player.getHealth() == player.getMaxHealth() &&
                time - lastFullHealthTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastFullHealthTime.put(id, time);
        }

        if (!player.hasStatusEffect(StatusEffects.POISON)
                && !player.isOnFire()
                && !player.isSubmergedInWater()
                && player.getAir() >= player.getMaxAir()
                && player.getAttacker() == null &&
                time - lastRestingTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastRestingTime.put(id, time);
        }

        if (player.isSleeping() &&
                time - lastSleepTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastSleepTime.put(id, time);
        }

        if (player.isUsingItem() && player.getActiveItem().getItem().isFood() &&
                time - lastEatingTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastEatingTime.put(id, time);
        }

        if (stress < 0) stress = 0;
        if (stress >= MAX_STRESS) {
            stress = 0;
        }

        boolean onCooldown = lastLegacyTime.containsKey(id) &&
                (time - lastLegacyTime.get(id) < LEGACY_COOLDOWN_TICKS);

        if ((stress >= 100) && !onCooldown) {
            giveLegacy(player);
            stress = 0;
            lastLegacyTime.put(id, time);
        }

        playerStress.put(id, stress);

        Text stressText = Text.literal("Stress: " + stress)
                .formatted(stress >= 100 ? Formatting.RED : stress >= 60 ? Formatting.GOLD : Formatting.GREEN);

        Text cooldownText = Text.literal("");
        if (lastLegacyTime.containsKey(id)) {
            long timePassed = time - lastLegacyTime.get(id);
            long cooldownLeft = LEGACY_COOLDOWN_TICKS - timePassed;
            if (cooldownLeft > 0) {
                int totalSeconds = (int) (cooldownLeft / TICKS_PER_SECOND);
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;
                double ratio = (double) cooldownLeft / LEGACY_COOLDOWN_TICKS;

                Formatting cooldownColor = ratio <= 0.2 ? Formatting.GREEN : ratio <= 0.6 ? Formatting.GOLD : Formatting.RED;
                String timeString = minutes > 0
                        ? String.format("%dm %02ds", minutes, seconds)
                        : String.format("%ds", seconds);

                cooldownText = Text.literal(" | Legacy Cooldown: " + timeString).formatted(cooldownColor);
            }
        }

        Text combinedText = Text.empty()
                .append(stressText)
                .append(cooldownText);

        player.sendMessage(combinedText, true);
    }

    public static void onPetDeath(ServerPlayerEntity owner) {
        UUID id = owner.getUuid();
        int stress = playerStress.getOrDefault(id, 0);
        stress += 3;
        if (stress > MAX_STRESS) stress = MAX_STRESS;
        playerStress.put(id, stress);
    }

    public static void giveLegacy(ServerPlayerEntity player) {
        // ~67% chance for failure, 33% chance for success
        if (ThreadLocalRandom.current().nextDouble() >= 0.33) {
            player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);
            return;
        }

        StatusEffect randomEffect = getRandomLegacyWeighted();

        if (!player.hasStatusEffect(randomEffect)) {
            player.addStatusEffect(new StatusEffectInstance(randomEffect, Integer.MAX_VALUE, 0, false, false, false));
            player.sendMessage(Text.literal("You have been bestowed upon the " + randomEffect.getName().getString() + " legacy!"), false);
        } else {
            player.sendMessage(Text.literal(player.getName().getString() + " already has been bestowed upon the " + randomEffect.getName().getString() + " legacy."), false);
        }
    }

    private static StatusEffect getRandomLegacyWeighted() {
        int totalWeight = LEGACY_POOL.values().stream().mapToInt(Integer::intValue).sum();
        int random = ThreadLocalRandom.current().nextInt(totalWeight);
        int cumulative = 0;

        for (Map.Entry<StatusEffect, Integer> entry : LEGACY_POOL.entrySet()) {
            cumulative += entry.getValue();
            if (random < cumulative) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void resetStress(UUID playerId) {
        playerStress.put(playerId, 0);
    }

    public static void registerLegacyBestowalHandler() {
        LorienLegaciesMod.LOGGER.info("Registering Legacy Bestowal Handler for " + LorienLegaciesMod.MOD_ID);
    }

    public static int getStress(ServerPlayerEntity player) {
        return playerStress.getOrDefault(player.getUuid(), 0);
    }

    public static void setStress(ServerPlayerEntity player, int stress) {
        playerStress.put(player.getUuid(), stress);
    }

    public static long getCooldownLeft(ServerPlayerEntity player) {
        long currentTime = player.getServerWorld().getTime();
        long lastTime = getLastLegacyTime(player);
        return Math.max(0, LEGACY_COOLDOWN_TICKS - (currentTime - lastTime));
    }

    public static long getLastLegacyTime(ServerPlayerEntity player) {
        return lastLegacyTime.getOrDefault(player.getUuid(), 0L);
    }

    public static void setLastLegacyTimeFromCooldown(ServerPlayerEntity player, long cooldownLeft) {
        long currentTime = player.getServerWorld().getTime();
        long adjustedTime = currentTime - (LEGACY_COOLDOWN_TICKS - cooldownLeft);
        lastLegacyTime.put(player.getUuid(), adjustedTime);
    }
}
