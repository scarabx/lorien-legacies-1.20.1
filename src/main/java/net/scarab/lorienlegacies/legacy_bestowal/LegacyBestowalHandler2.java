package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler2 {

    private static final Set<UUID> isBurning = new HashSet<>();

    private static final Set<UUID> isFreezing = new HashSet<>();

    private static final Set<UUID> isDrowning = new HashSet<>();

    private static final Set<UUID> isSuffocating = new HashSet<>();

    private static final Set<UUID> isFallingToDeath = new HashSet<>();

    private static final Set<UUID> isGettingDamaged = new HashSet<>();

    public static void bestowLumenLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !player.hasStatusEffect(ModEffects.LUMEN) && player.isOnFire() && player.getHealth() <= 4 && !isBurning.contains(id)) {

            isBurning.add(id);

            if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.LUMEN, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Lumen legacy."), false);

        }

        if (!player.isOnFire()) {

            isBurning.remove(id);

        }
    }

    public static void bestowGlacenLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(ModEffects.GLACEN) && player.isFrozen() && player.getHealth() <= 4 && !isFreezing.contains(id)) {

            isFreezing.add(id);

            if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.GLACEN, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Glacen legacy."), false);

        }

        if (!player.isFrozen()) {

            isFreezing.remove(id);

        }
    }

    public static void bestowSubmariLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(ModEffects.SUBMARI) && player.isSubmergedInWater() && player.getAir() >= 0 && player.getHealth() <= 4 && !isDrowning.contains(id)) {

            isDrowning.add(id);

            if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.SUBMARI, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Submari legacy."), false);

        }

        if (!player.isSubmergedInWater() && !(player.getAir() < 0)) {

            isDrowning.remove(id);

        }
    }

    public static void bestowPondusLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(ModEffects.PONDUS) && player.isInsideWall() && player.getHealth() <= 4 && !isSuffocating.contains(id)) {

            isSuffocating.add(id);

            if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.PONDUS, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Pondus legacy."), false);

        }

        if (!player.isInsideWall()) {

            isSuffocating.remove(id);

        }
    }

    public static void bestowRegenerasLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(ModEffects.REGENERAS) && player.getHealth() <= 4 && !isGettingDamaged.contains(id)) {

            isGettingDamaged.add(id);

            if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.REGENERAS, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Regeneras legacy."), false);

        }

        if (player.getHealth() > 4) {

            isGettingDamaged.remove(id);

        }
    }

    public static void bestowAvexLegacy(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!player.hasStatusEffect(ModEffects.AVEX) && !player.isOnGround() && player.fallDistance > 3 && !isFallingToDeath.contains(id)) {

            float estimatedFallDamage = Math.max(0, MathHelper.floor(player.fallDistance - 3));

            if (estimatedFallDamage >= player.getHealth()) {

                isFallingToDeath.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.AVEX, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Avex legacy."), false);

            }
        }

        if (player.isOnGround()) {

            isFallingToDeath.remove(id);

        }
    }

    public static void registerLegacyBestowalHandler2() {

        LorienLegaciesMod.LOGGER.info("Registering Legacy Bestowal Handler 2 for " + LorienLegaciesMod.MOD_ID);

    }
}
