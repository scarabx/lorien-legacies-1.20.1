package net.scarab.lorienlegacies.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;

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
                        ModEffects.ACCELIX,
                        ModEffects.AVEX, ModEffects.INTANGIFLY,
                        ModEffects.PONDUS, ModEffects.TOGGLE_IMPENETRABLE_SKIN, ModEffects.TOGGLE_INTANGIBILITY,
                        ModEffects.TELEKINESIS, ModEffects.TOGGLE_TELEKINESIS_PUSH, ModEffects.TOGGLE_TELEKINESIS_PULL, ModEffects.TOGGLE_TELEKINESIS_MOVE,
                        ModEffects.CHIMAERA_CALL, ModEffects.CHIMAERA_MORPH, ModEffects.MARK_TARGET_FOR_WOLF,
                        ModEffects.GLACEN, ModEffects.TOGGLE_FREEZE_WATER, ModEffects.TOGGLE_ICE_HANDS, ModEffects.TOGGLE_ICICLES, ModEffects.TOGGLE_SHOOT_ICEBALL,
                        ModEffects.LUMEN, ModEffects.TOGGLE_FLAMING_HANDS, ModEffects.TOGGLE_HUMAN_FIREBALL_AOE, ModEffects.TOGGLE_SHOOT_FIREBALL,
                        ModEffects.NOVIS, ModEffects.TOGGLE_NOVIS,
                        ModEffects.NOXEN,
                        ModEffects.REGENERAS,
                        ModEffects.STURMA, ModEffects.TOGGLE_LIGHTNING_STRIKE,
                        ModEffects.SUBMARI
                        // Removed LEGACY_COOLDOWN here to handle separately below
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

                // Handle LEGACY_COOLDOWN separately to preserve remaining duration
                StatusEffectInstance cooldownEffect = oldPlayer.getStatusEffect(ModEffects.LEGACY_COOLDOWN);
                if (cooldownEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.LEGACY_COOLDOWN,
                            cooldownEffect.getDuration(),
                            cooldownEffect.getAmplifier(),
                            cooldownEffect.isAmbient(),
                            cooldownEffect.shouldShowParticles(),
                            cooldownEffect.shouldShowIcon()
                    ));
                }
            }
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (entity instanceof TameableEntity tameable && tameable.getOwner() instanceof ServerPlayerEntity owner) {
                LegacyBestowalHandler.onPetDeath(owner);
            }
        });
    }
}

