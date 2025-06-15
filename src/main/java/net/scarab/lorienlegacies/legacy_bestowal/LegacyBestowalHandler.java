package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.item.ModItems;
import net.scarab.lorienlegacies.potion.ModPotions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler {

    private static final Map<UUID, Integer> playerStress = new HashMap<>();
    private static final Map<UUID, Long> lastLowHealthTime = new HashMap<>();
    private static final Map<UUID, Long> lastHungerTime = new HashMap<>();
    private static final Map<UUID, Long> lastMultiThreatTime = new HashMap<>();
    private static final Map<UUID, Long> lastFullHealthTime = new HashMap<>();
    private static final Map<UUID, Long> lastRestingTime = new HashMap<>();
    private static final Map<UUID, Long> lastSleepTime = new HashMap<>();
    private static final Map<UUID, Long> lastEatingTime = new HashMap<>();

    private static final Map<StatusEffect, Integer> LEGACY_POOL = Map.ofEntries(
            Map.entry(ModEffects.TELEKINESIS, 10),
            Map.entry(ModEffects.LUMEN, 2),
            Map.entry(ModEffects.AVEX, 1),
            Map.entry(ModEffects.PONDUS, 1),
            Map.entry(ModEffects.GLACEN, 3),
            Map.entry(ModEffects.ACCELIX, 4),
            Map.entry(ModEffects.NOVIS, 1),
            Map.entry(ModEffects.NOXEN, 6),
            Map.entry(ModEffects.REGENERAS, 1),
            Map.entry(ModEffects.SUBMARI, 5),
            Map.entry(ModEffects.STURMA, 1),
            Map.entry(ModEffects.XIMIC, 1),
            Map.entry(ModEffects.TELETRAS, 1),
            Map.entry(ModEffects.KINETIC_DETONATION, 1)
    );

    private static final int TICKS_PER_SECOND = 20;
    private static final int THROTTLE_TICKS = TICKS_PER_SECOND * 10;
    private static final int MAX_STRESS = 150;
    private static final int LEGACY_COOLDOWN_TICKS = 20 * 60 * 5;

    public static void stressManager(ServerPlayerEntity player) {
        UUID id = player.getUuid();
        long time = player.getServerWorld().getTime();
        int stress = playerStress.getOrDefault(id, 0);

        /*if (player.getHealth() <= 2 && time - lastLowHealthTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress += 3;
            lastLowHealthTime.put(id, time);
        }/*

        if (player.getHungerManager().getFoodLevel() <= 6 &&
                time - lastHungerTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress += 3;
            lastHungerTime.put(id, time);
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
        }*/

        /*if (player.getHealth() == player.getMaxHealth() &&
                time - lastFullHealthTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastFullHealthTime.put(id, time);
        }*/

        /*if (!player.hasStatusEffect(StatusEffects.POISON)
                && !player.isOnFire()
                && !player.isSubmergedInWater()
                && player.getAir() >= player.getMaxAir()
                && player.getAttacker() == null
                && !(player.getHealth() <= 2)
                && !(player.getHungerManager().getFoodLevel() <= 6) &&
                time - lastRestingTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastRestingTime.put(id, time);
        }*/

        /*if (player.isSleeping() &&
                time - lastSleepTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastSleepTime.put(id, time);
        }*/

        /*if (player.isUsingItem() && player.getActiveItem().getItem().isFood() &&
                time - lastEatingTime.getOrDefault(id, 0L) > THROTTLE_TICKS) {
            stress -= 1;
            lastEatingTime.put(id, time);
        }*/

        if (stress < 0) stress = 0;
        if (stress >= MAX_STRESS) stress = 0;

        boolean onCooldown = player.hasStatusEffect(ModEffects.LEGACY_COOLDOWN);

        if ((stress >= 100) && !onCooldown) {
            giveLegacy(player);
            stress = 0;
        }

        playerStress.put(id, stress);

        Text stressText = Text.literal("Stress: " + stress)
                .formatted(stress >= 100 ? Formatting.RED : stress >= 60 ? Formatting.GOLD : Formatting.GREEN);

        Text cooldownText = Text.empty();
        if (onCooldown) {
            int ticksLeft = player.getStatusEffect(ModEffects.LEGACY_COOLDOWN).getDuration();
            int totalSeconds = ticksLeft / TICKS_PER_SECOND;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            double ratio = (double) ticksLeft / LEGACY_COOLDOWN_TICKS;
            Formatting cooldownColor = ratio <= 0.2 ? Formatting.GREEN : ratio <= 0.6 ? Formatting.GOLD : Formatting.RED;

            String timeString = minutes > 0
                    ? String.format("%dm %02ds", minutes, seconds)
                    : String.format("%ds", seconds);

            cooldownText = Text.literal(" | Legacy Cooldown: " + timeString).formatted(cooldownColor);
        }

        Text staminaText = Text.empty();

        StatusEffectInstance staminaEffect = player.getStatusEffect(ModEffects.STAMINA);
        StatusEffectInstance tiredEffect = player.getStatusEffect(ModEffects.TIRED);

        if (staminaEffect != null) {
            int ticksLeft = staminaEffect.getDuration();
            int totalSeconds = ticksLeft / TICKS_PER_SECOND;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            String timeString = minutes > 0
                    ? String.format("%dm %02ds", minutes, seconds)
                    : String.format("%ds", seconds);

            staminaText = Text.literal(" | Stamina: " + timeString).formatted(Formatting.YELLOW);
        } else if (tiredEffect != null) {
            int ticksLeft = tiredEffect.getDuration();
            int totalSeconds = ticksLeft / TICKS_PER_SECOND;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            String timeString = minutes > 0
                    ? String.format("%dm %02ds", minutes, seconds)
                    : String.format("%ds", seconds);

            staminaText = Text.literal(" | Tired: " + timeString).formatted(Formatting.RED);
        }

        Text dangerText = Text.empty();
        if (player.getWorld() instanceof ServerWorld serverWorld &&
                (player.getMainHandStack().isOf(ModItems.RED_BRACELET) || player.getOffHandStack().isOf(ModItems.RED_BRACELET))) {

            boolean dangerNearby = !serverWorld.getEntitiesByClass(
                    HostileEntity.class,
                    player.getBoundingBox().expand(15),
                    e -> true
            ).isEmpty();

            if (dangerNearby) {
                dangerText = Text.literal(" | ").formatted(Formatting.RED) // red but not bold
                        .append(Text.literal("DANGER").formatted(Formatting.RED, Formatting.BOLD)); // red and bold
            }
        }

        Text combinedText = Text.empty()
                .append(stressText)
                .append(cooldownText)
                .append(staminaText)
                .append(dangerText);

        player.sendMessage(combinedText, true);
    }

    /*public static void onPetDeath(ServerPlayerEntity owner) {
        UUID id = owner.getUuid();
        int stress = playerStress.getOrDefault(id, 0);
        stress += 3;
        if (stress > MAX_STRESS) stress = MAX_STRESS;
        playerStress.put(id, stress);
    }*/

    public static void giveLegacy(ServerPlayerEntity player) {
        if (ThreadLocalRandom.current().nextDouble() >= 0.33) {
            player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);
            return;
        }

        StatusEffect randomEffect = getRandomLegacyWeighted(player);

        if (!player.hasStatusEffect(randomEffect)) {
            player.addStatusEffect(new StatusEffectInstance(randomEffect, Integer.MAX_VALUE, 0, false, false, false));
            player.addStatusEffect(new StatusEffectInstance(ModEffects.LEGACY_COOLDOWN, LEGACY_COOLDOWN_TICKS, 0, false, false, false));
            player.sendMessage(Text.literal("You have been bestowed upon the " + randomEffect.getName().getString() + " legacy!"), false);
            ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);
            player.giveItemStack(splashPotion);
            player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);
        } else {
            player.sendMessage(Text.literal(player.getName().getString() + " already has been bestowed upon the " + randomEffect.getName().getString() + " legacy."), false);
        }
    }

    private static StatusEffect getRandomLegacyWeighted(ServerPlayerEntity player) {
        int totalWeight = 0;
        Map<StatusEffect, Integer> filtered = new HashMap<>();

        long legacyCount = player.getStatusEffects().stream()
                .map(StatusEffectInstance::getEffectType)
                .filter(LEGACY_POOL::containsKey)
                .count();

        for (Map.Entry<StatusEffect, Integer> entry : LEGACY_POOL.entrySet()) {
            StatusEffect effect = entry.getKey();
            int weight = entry.getValue();
            if (effect == ModEffects.XIMIC && legacyCount < 5) continue;
            filtered.put(effect, weight);
            totalWeight += weight;
        }

        int random = ThreadLocalRandom.current().nextInt(totalWeight);
        int cumulative = 0;

        for (Map.Entry<StatusEffect, Integer> entry : filtered.entrySet()) {
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
}
