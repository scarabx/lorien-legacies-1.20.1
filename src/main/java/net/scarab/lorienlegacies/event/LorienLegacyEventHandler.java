package net.scarab.lorienlegacies.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.List;

public class LorienLegacyEventHandler {

    public static void register() {
        keepEffectsOnDeath();
    }

    private static void keepEffectsOnDeath() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                // List of effects that should persist through death
                List<StatusEffect> persistentEffects = List.of(
                        ModEffects.ACCELIX, ModEffects.TOGGLE_ACCELIX,
                        ModEffects.AVEX, ModEffects.TOGGLE_AVEX,
                        ModEffects.PONDUS, ModEffects.TOGGLE_IMPENETRABLE_SKIN,
                        ModEffects.TOGGLE_INTANGIBILITY
                        // Add more effects as needed
                );

                for (StatusEffect effect : persistentEffects) {
                    if (oldPlayer.hasStatusEffect(effect)) {
                        newPlayer.addStatusEffect(new StatusEffectInstance(
                                effect,
                                Integer.MAX_VALUE,
                                0,
                                false,
                                false,
                                false
                        ));
                    }
                }
            }
        });
    }
}
