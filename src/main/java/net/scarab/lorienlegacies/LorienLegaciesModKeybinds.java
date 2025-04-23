package net.scarab.lorienlegacies;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import org.lwjgl.glfw.GLFW;

public class LorienLegaciesModKeybinds implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyBinding shootFireball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.shoot_fireball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding humanFireball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.human_fireball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding flamingHands = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.flaming_hands", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding shootIceball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.shoot_iceball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.category.lorienlegacies.lorienlegacies"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (shootFireball.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.LUMEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.SHOOT_FIREBALL_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            while (humanFireball.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.LUMEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.HUMAN_FIREBALL_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            while (flamingHands.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.LUMEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.TOGGLE_FLAMING_HANDS_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            while (shootIceball.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.GlACEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.SHOOT_ICEBALL_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }
        });

    }
}
