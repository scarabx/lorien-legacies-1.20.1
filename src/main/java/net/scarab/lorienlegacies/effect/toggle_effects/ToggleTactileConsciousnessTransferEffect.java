package net.scarab.lorienlegacies.effect.toggle_effects;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import static javax.swing.text.html.parser.DTDConstants.ID;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_XIMIC_ACCELIX;
import static net.scarab.lorienlegacies.network.LorienLegaciesModNetworking.RESET_TACTILE_CONSCIOUSNESS_TRANSFER_PACKET;

public class ToggleTactileConsciousnessTransferEffect extends StatusEffect {

    public ToggleTactileConsciousnessTransferEffect(StatusEffectCategory category, int color) {
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
    }

    // Toggle helper method for safely enabling/disabling the effect invisibly
    public static void toggleTactileConsciousnessTransfer(ServerPlayerEntity player) {

        if (player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)) {
            player.removeStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER);

            // Tell client to reset camera
            ServerPlayNetworking.send(player, RESET_TACTILE_CONSCIOUSNESS_TRANSFER_PACKET, new PacketByteBuf(Unpooled.buffer()));
        } else {
            // Apply the status effect invisibly: no ambient, no particles, no icon
            player.addStatusEffect(new StatusEffectInstance(
                    TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER,
                    -1,
                    0,
                    false,
                    false,
                    false
            ));
        }
    }
}
