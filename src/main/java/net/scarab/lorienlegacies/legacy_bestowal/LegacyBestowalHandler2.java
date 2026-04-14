package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.potion.ModPotions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler2 {

    private static final List<StatusEffect> legacies = List.of(

            ModEffects.ACCELIX,
            ModEffects.AVEX,
            ModEffects.GLACEN,
            ModEffects.KINETIC_DETONATION,
            ModEffects.LUMEN,
            ModEffects.PONDUS,
            ModEffects.NOVIS,
            ModEffects.NOXEN,
            ModEffects.REGENERAS,
            ModEffects.STURMA,
            ModEffects.SUBMARI,
            ModEffects.TELETRAS,
            ModEffects.XIMIC

    );

    private static final Set<UUID> isBurning = new HashSet<>();

    private static final Set<UUID> isFreezing = new HashSet<>();

    private static final Set<UUID> isDrowning = new HashSet<>();

    private static final Set<UUID> isSuffocating = new HashSet<>();

    private static final Set<UUID> isFallingToDeath = new HashSet<>();

    private static final Set<UUID> isGettingDamaged = new HashSet<>();

    private static final Set<UUID> isSprinting = new HashSet<>();

    private static final Map<UUID, BlockPos> sprintStartPos = new HashMap<>();

    public static void bestowLumenLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5 || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !player.hasStatusEffect(ModEffects.LUMEN) && player.isOnFire() && player.getHealth() <= 4 && !isBurning.contains(id)) {

                isBurning.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.LUMEN, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Lumen legacy."), false);

                ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                player.giveItemStack(splashPotion);

                player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                bestowXimicLegacy(player);

            }

            if (!player.isOnFire()) {

                isBurning.remove(id);

            }
        }
    }

    public static void bestowGlacenLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5  || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(ModEffects.GLACEN) && player.isFrozen() && player.getHealth() <= 4 && !isFreezing.contains(id)) {

                isFreezing.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.GLACEN, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Glacen legacy."), false);

                ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                player.giveItemStack(splashPotion);

                player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                bestowXimicLegacy(player);

            }

            if (!player.isFrozen()) {

                isFreezing.remove(id);

            }
        }
    }

    public static void bestowSubmariLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5 || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(ModEffects.SUBMARI) && player.isSubmergedInWater() && player.getAir() >= 0 && player.getHealth() <= 4 && !isDrowning.contains(id)) {

                isDrowning.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.SUBMARI, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Submari legacy."), false);

                ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                player.giveItemStack(splashPotion);

                player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                bestowXimicLegacy(player);

            }

            if (!player.isSubmergedInWater() && !(player.getAir() < 0)) {

                isDrowning.remove(id);

            }
        }
    }

    public static void bestowPondusLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5 || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(ModEffects.PONDUS) && player.isInsideWall() && player.getHealth() <= 4 && !isSuffocating.contains(id)) {

                isSuffocating.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.PONDUS, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Pondus legacy."), false);

                ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                player.giveItemStack(splashPotion);

                player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                bestowXimicLegacy(player);

            }

            if (!player.isInsideWall()) {

                isSuffocating.remove(id);

            }
        }
    }

    public static void bestowRegenerasLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5 || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(ModEffects.REGENERAS) && player.getHealth() <= 4 && !isGettingDamaged.contains(id)) {

                isGettingDamaged.add(id);

                if (ThreadLocalRandom.current().nextDouble() >= 0.01) {

                    player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                    return;

                }

                player.addStatusEffect(new StatusEffectInstance(ModEffects.REGENERAS, Integer.MAX_VALUE, 0, false, false, false));

                player.sendMessage(Text.literal("You have been bestowed upon the Regeneras legacy."), false);

                ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                player.giveItemStack(splashPotion);

                player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                bestowXimicLegacy(player);

            }

            if (player.getHealth() > 4) {

                isGettingDamaged.remove(id);

            }
        }
    }

    public static void bestowAvexLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5 || player.hasStatusEffect(ModEffects.XIMIC)) {

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

                    ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                    player.giveItemStack(splashPotion);

                    player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                    bestowXimicLegacy(player);

                }
            }

            if (player.isOnGround()) {

                isFallingToDeath.remove(id);

            }
        }
    }

    public static void bestowAccelixLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() < 5  || player.hasStatusEffect(ModEffects.XIMIC)) {

            UUID id = player.getUuid();

            if (!player.hasStatusEffect(ModEffects.ACCELIX) && player.isSprinting() && !isSprinting.contains(id)) {

                isSprinting.add(id);

                if (!sprintStartPos.containsKey(id)) {

                    sprintStartPos.put(id, player.getBlockPos());

                }

                BlockPos startPos = sprintStartPos.get(id);

                double distanceSprinted = Math.sqrt(Math.pow(player.getX() - startPos.getX(), 2) + Math.pow(player.getZ() - startPos.getZ(), 2));

                if (distanceSprinted >= 10) {

                    if (ThreadLocalRandom.current().nextDouble() >= 0.1) {

                        player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                        return;

                    }

                    player.addStatusEffect(new StatusEffectInstance(ModEffects.ACCELIX, Integer.MAX_VALUE, 0, false, false, false));

                    player.sendMessage(Text.literal("You have been bestowed upon the Accelix legacy."), false);

                    ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

                    player.giveItemStack(splashPotion);

                    player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

                    bestowXimicLegacy(player);

                    sprintStartPos.remove(id);

                }

            } else {

                sprintStartPos.remove(player.getUuid());

            }

            //if (!player.isSprinting()) {

                isSprinting.remove(id);

            //}
        }
    }

    public static void bestowXimicLegacy(ServerPlayerEntity player) {

        if (legacies.stream().filter(player::hasStatusEffect).count() == 3) {

            if (ThreadLocalRandom.current().nextDouble() >= 0.01) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.XIMIC, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Ximic legacy."), false);

            ItemStack splashPotion = PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CHIMAERA_ESSENCE);

            player.giveItemStack(splashPotion);

            player.sendMessage(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY").formatted(Formatting.RED), false);

        }
    }

    public static void registerLegacyBestowalHandler2() {

        LorienLegaciesMod.LOGGER.info("Registering Legacy Bestowal Handler 2 for " + LorienLegaciesMod.MOD_ID);

    }
}
