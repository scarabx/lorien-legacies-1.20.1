package net.scarab.lorienlegacies.effect.active_effects;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;


import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_AVEX;

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

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 100, 0, false, false, false));

        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.hasStatusEffect(TOGGLE_AVEX)) return;
        if (!player.getWorld().isClient) return;
        if (!player.isFallFlying()) {
            // Request server to start flying
            ClientPlayNetworking.send(
                    LorienLegaciesModNetworking.START_AVEX_FLIGHT_PACKET,
                    PacketByteBufs.empty()
            );
        }

        // Apply forward motion while fall flying
        if (player.isFallFlying()) {
            Vec3d look = player.getRotationVec(1.0F);
            Vec3d boosted = player.getVelocity().add(look.multiply(0.05));

            // Limit maximum speed to avoid infinite acceleration
            double maxSpeed = 1.5; // tweak this value as needed
            if (boosted.length() > maxSpeed) {
                boosted = boosted.normalize().multiply(maxSpeed);
            }

            player.setVelocity(boosted);
        }

        if (player.isFallFlying() && player.isOnGround()) {
            player.removeStatusEffect(TOGGLE_AVEX);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
