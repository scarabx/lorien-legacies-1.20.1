package net.scarab.lorienlegacies.effect.active_effects;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;

public class AvexEffect extends StatusEffect {

    public AvexEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        // Reapply invisibly if needed
        StatusEffectInstance current = entity.getStatusEffect(this);
        if (current != null && (current.shouldShowParticles() || current.shouldShowIcon())) {
            entity.removeStatusEffect(this);
            entity.addStatusEffect(new StatusEffectInstance(
                    this,
                    current.getDuration(),
                    current.getAmplifier(),
                    false,
                    false,
                    false
            ));
        }

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 400, 0, false, false, false));

        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.getWorld().isClient) return;
        if (!player.hasStatusEffect(ModEffects.TIRED)) {

            // Start flying if not already and the player is in the air (jumped and not on ground or flying)
            if (!player.isFallFlying() && !player.isOnGround() && player.getVelocity().y > 0 && player.isSneaking()) {
                ClientPlayNetworking.send(
                        LorienLegaciesModNetworking.START_AVEX_FLIGHT_PACKET,
                        PacketByteBufs.empty()
                );
            }

            // Apply forward motion while flying
            if (player.isFallFlying()) {
                Vec3d look = player.getRotationVec(1.0F);
                Vec3d boosted = player.getVelocity().add(look.multiply(0.05));

                double maxSpeed = 1.5;
                if (boosted.length() > maxSpeed) {
                    boosted = boosted.normalize().multiply(maxSpeed);
                }

                player.setVelocity(boosted);

                // Optional: stop flight gently when landing
                if (player.isOnGround()) {
                    player.stopFallFlying();
                }
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.stopFallFlying();
        }
        super.onRemoved(entity, attributes, amplifier);
    }
}
