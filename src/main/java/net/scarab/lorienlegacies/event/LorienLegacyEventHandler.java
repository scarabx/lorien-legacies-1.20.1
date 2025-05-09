package net.scarab.lorienlegacies.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.List;

public class LorienLegacyEventHandler {

    // Register event listener
    public static void register() {
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

                // Reapply the effects that were present before death
                for (StatusEffect effect : persistentEffects) {
                    if (oldPlayer.hasStatusEffect(effect)) {
                        newPlayer.addStatusEffect(new StatusEffectInstance(
                                effect,
                                Integer.MAX_VALUE, // Keep the effect indefinitely
                                0, // Default amplifier
                                false, // No ambient
                                false, // No particles
                                false  // No icon
                        ));
                    }
                }
            }
        });
    }
}
