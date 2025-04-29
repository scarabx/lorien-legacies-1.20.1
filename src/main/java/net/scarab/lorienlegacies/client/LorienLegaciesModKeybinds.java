package net.scarab.lorienlegacies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.scarab.lorienlegacies.client.gui.RadialMenuHandler;
import net.scarab.lorienlegacies.client.gui.RadialMenuScreen;
import org.lwjgl.glfw.GLFW;

public class LorienLegaciesModKeybinds implements ClientModInitializer {

    private static boolean sneakingLastTick = false;

    @Override
    public void onInitializeClient() {

        KeyBinding openRadialMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.open_radial_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.category.lorienlegacies.lorienlegacies"));

        // Register HUD renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            RadialMenuHandler.render(drawContext, tickDelta);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Radial Menu Keybind
            if (openRadialMenuKey.wasPressed()) {
                if (RadialMenuHandler.menuOpen) {
                    RadialMenuHandler.closeMenu();
                    client.setScreen(null);
                } else {
                    RadialMenuHandler.menuOpen = true;
                    client.setScreen(new RadialMenuScreen());
                }
            }
        });
    }
}
