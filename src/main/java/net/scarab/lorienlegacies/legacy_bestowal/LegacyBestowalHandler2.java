package net.scarab.lorienlegacies.legacy_bestowal;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.scarab.lorienlegacies.LorienLegaciesMod;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.concurrent.ThreadLocalRandom;

public class LegacyBestowalHandler2 {

    public static void bestowLumenLegacy(ServerPlayerEntity player) {

        if (!player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !player.hasStatusEffect(ModEffects.LUMEN) && player.getFireTicks() > 140) {

            if (ThreadLocalRandom.current().nextDouble() > 0.33) {

                player.sendMessage(Text.literal("You felt a surge of power... but nothing happened."), false);

                return;

            }

            player.addStatusEffect(new StatusEffectInstance(ModEffects.LUMEN, Integer.MAX_VALUE, 0, false, false, false));

            player.sendMessage(Text.literal("You have been bestowed upon the Lumen legacy."), false);

        }
    }

    public static void registerLegacyBestowalHandler2() {

        LorienLegaciesMod.LOGGER.info("Registering Legacy Bestowal Handler 2 for " + LorienLegaciesMod.MOD_ID);

    }
}
