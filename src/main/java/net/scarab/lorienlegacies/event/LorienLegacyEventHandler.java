package net.scarab.lorienlegacies.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.scarab.lorienlegacies.effect.ModEffects;

import java.util.List;

public class LorienLegacyEventHandler {

    public static void register() {
        keepEffectsOnDeath();
    }

    private static void keepEffectsOnDeath() {

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                // Effects that should always persist with infinite duration
                List<StatusEffect> persistentEffects = List.of(
                        ModEffects.ACCELIX,
                        ModEffects.AVEX, ModEffects.INTANGIFLY,
                        ModEffects.PONDUS, ModEffects.TOGGLE_IMPENETRABLE_SKIN, ModEffects.TOGGLE_INTANGIBILITY,
                        ModEffects.TELEKINESIS, ModEffects.TOGGLE_TELEKINESIS_PUSH, ModEffects.TOGGLE_TELEKINESIS_PULL, ModEffects.TOGGLE_TELEKINESIS_MOVE, ModEffects.TOGGLE_TELEKINESIS_DEFLECT,
                        ModEffects.CHIMAERA_CALL, ModEffects.CHIMAERA_MORPH, ModEffects.MARK_TARGET_FOR_WOLF,
                        ModEffects.GLACEN, ModEffects.TOGGLE_FREEZE_WATER, ModEffects.TOGGLE_ICE_HANDS, ModEffects.TOGGLE_ICICLES, ModEffects.TOGGLE_SHOOT_ICEBALL,
                        ModEffects.LUMEN, ModEffects.TOGGLE_FLAMING_HANDS, ModEffects.TOGGLE_HUMAN_FIREBALL_AOE, ModEffects.TOGGLE_SHOOT_FIREBALL,
                        ModEffects.NOVIS, ModEffects.TOGGLE_NOVIS,
                        ModEffects.NOXEN,
                        ModEffects.REGENERAS,
                        ModEffects.STURMA, ModEffects.TOGGLE_LIGHTNING_STRIKE,
                        ModEffects.SUBMARI,
                        ModEffects.XIMIC,
                        ModEffects.KINETIC_DETONATION, ModEffects.TOGGLE_KINETIC_DETONATION,
                        ModEffects.TELETRAS, ModEffects.TOGGLE_TELETRAS
                );

                for (StatusEffect effect : persistentEffects) {
                    StatusEffectInstance instance = oldPlayer.getStatusEffect(effect);
                    if (instance != null && instance.getAmplifier() != 99) {
                        newPlayer.addStatusEffect(new StatusEffectInstance(
                                effect,
                                Integer.MAX_VALUE,
                                instance.getAmplifier(),
                                false,
                                false,
                                false
                        ));
                    }
                }

                // Handle LEGACY_COOLDOWN
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

                // Handle LEGACY_INHIBITION
                StatusEffectInstance inhibitionEffect = oldPlayer.getStatusEffect(ModEffects.LEGACY_INHIBITION);
                if (inhibitionEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.LEGACY_INHIBITION,
                            inhibitionEffect.getDuration(),
                            inhibitionEffect.getAmplifier(),
                            inhibitionEffect.isAmbient(),
                            inhibitionEffect.shouldShowParticles(),
                            inhibitionEffect.shouldShowIcon()
                    ));
                }

                // Handle ACTIVE_LEGACY_INHIBITION
                StatusEffectInstance activeInhibitionEffect = oldPlayer.getStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION);
                if (activeInhibitionEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.ACTIVE_LEGACY_INHIBITION,
                            activeInhibitionEffect.getDuration(),
                            activeInhibitionEffect.getAmplifier(),
                            activeInhibitionEffect.isAmbient(),
                            activeInhibitionEffect.shouldShowParticles(),
                            activeInhibitionEffect.shouldShowIcon()
                    ));
                }

                // Handle ACTIVE_LEGACY_INHIBITION
                StatusEffectInstance pondusStaminaEffect = oldPlayer.getStatusEffect(ModEffects.PONDUS_STAMINA);
                if (pondusStaminaEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.PONDUS_STAMINA,
                            pondusStaminaEffect.getDuration(),
                            pondusStaminaEffect.getAmplifier(),
                            pondusStaminaEffect.isAmbient(),
                            pondusStaminaEffect.shouldShowParticles(),
                            pondusStaminaEffect.shouldShowIcon()
                    ));
                }

                // Handle ACTIVE_LEGACY_INHIBITION
                StatusEffectInstance pondusCooldownEffect = oldPlayer.getStatusEffect(ModEffects.PONDUS_COOLDOWN);
                if (pondusCooldownEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.PONDUS_COOLDOWN,
                            pondusCooldownEffect.getDuration(),
                            pondusCooldownEffect.getAmplifier(),
                            pondusCooldownEffect.isAmbient(),
                            pondusCooldownEffect.shouldShowParticles(),
                            pondusCooldownEffect.shouldShowIcon()
                    ));
                }

                // Handle ACTIVE_LEGACY_INHIBITION
                StatusEffectInstance deflectStaminaEffect = oldPlayer.getStatusEffect(ModEffects.DEFLECT_STAMINA);
                if (deflectStaminaEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.DEFLECT_STAMINA,
                            deflectStaminaEffect.getDuration(),
                            deflectStaminaEffect.getAmplifier(),
                            deflectStaminaEffect.isAmbient(),
                            deflectStaminaEffect.shouldShowParticles(),
                            deflectStaminaEffect.shouldShowIcon()
                    ));
                }

                // Handle ACTIVE_LEGACY_INHIBITION
                StatusEffectInstance deflectCooldownEffect = oldPlayer.getStatusEffect(ModEffects.DEFLECT_COOLDOWN);
                if (deflectCooldownEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.DEFLECT_COOLDOWN,
                            deflectCooldownEffect.getDuration(),
                            deflectCooldownEffect.getAmplifier(),
                            deflectCooldownEffect.isAmbient(),
                            deflectCooldownEffect.shouldShowParticles(),
                            deflectCooldownEffect.shouldShowIcon()
                    ));
                }

                // Handle X_RAY_STONE_COOLDOWN
                StatusEffectInstance xRayStoneCooldownEffect = oldPlayer.getStatusEffect(ModEffects.X_RAY_STONE_COOLDOWN);
                if (xRayStoneCooldownEffect != null) {
                    newPlayer.addStatusEffect(new StatusEffectInstance(
                            ModEffects.X_RAY_STONE_COOLDOWN,
                            xRayStoneCooldownEffect.getDuration(),
                            xRayStoneCooldownEffect.getAmplifier(),
                            xRayStoneCooldownEffect.isAmbient(),
                            xRayStoneCooldownEffect.shouldShowParticles(),
                            xRayStoneCooldownEffect.shouldShowIcon()
                    ));
                }
            }
        });

        //ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            //if (entity instanceof TameableEntity tameable && tameable.getOwner() instanceof ServerPlayerEntity owner) {
                //LegacyBestowalHandler.onPetDeath(owner);
            //}
        //});
    }
}
