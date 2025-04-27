package net.scarab.lorienlegacies.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.scarab.lorienlegacies.client.gui.RadialMenuHandler;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import org.lwjgl.glfw.GLFW;

public class LorienLegaciesModKeybinds implements ClientModInitializer {

    private static boolean sneakingLastTick = false;

    @Override
    public void onInitializeClient() {

        KeyBinding openRadialMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.open_radial", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding shootFireball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.shoot_fireball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding humanFireball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.human_fireball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding flamingHands = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.flaming_hands", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding shootIceball = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.shoot_iceball", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding icicles = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.icicles", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding iceHands = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.ice_hands", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key.category.lorienlegacies.lorienlegacies"));

        KeyBinding freezeWater = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.freeze_water", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "key.category.lorienlegacies.lorienlegacies"));

        HudRenderCallback.EVENT.register((drawcontext, tickDelta) -> {
            RadialMenuHandler.render(drawcontext, tickDelta);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Radial Menu Logic
            if (openRadialMenuKey.wasPressed()) {
                RadialMenuHandler.toggleMenu();
            }

            if (RadialMenuHandler.menuOpen) {
                if (client.options.attackKey.isPressed()) {
                    RadialMenuHandler.selectOption();
                }

                if (client.options.sneakKey.isPressed()) {
                    if (!sneakingLastTick) {
                        RadialMenuHandler.nextPage();
                    }
                    sneakingLastTick = true;
                } else {
                    sneakingLastTick = false;
                }
            }

            // Ability keybinds logic
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

            while (icicles.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.GlACEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.ICICLES_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            while (iceHands.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.GlACEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.TOGGLE_ICE_HANDS_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }

            while (freezeWater.wasPressed()) {
                if (client.player.hasStatusEffect(ModEffects.GlACEN)) {
                    ClientPlayNetworking.send(LorienLegaciesModNetworking.FREEZE_WATER_PACKET, new PacketByteBuf(Unpooled.buffer()));
                }
            }
        });
    }
}
