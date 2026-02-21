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

    @Override
    public void onInitializeClient() {

        KeyBinding openRadialMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.open_radial_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.category.lorienlegacies.lorienlegacies"));

        // Register HUD renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            RadialMenuHandler.render(drawContext, tickDelta);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isHeld = InputUtil.isKeyPressed(
                    client.getWindow().getHandle(),
                    openRadialMenuKey.getDefaultKey().getCode()
            );

            // Open menu on hold - only if no GUI/menu/inventory/command/chat is open
            if (isHeld && !RadialMenuHandler.menuOpen && client.currentScreen == null) {
                RadialMenuHandler.menuOpen = true;
                client.setScreen(new RadialMenuScreen());
            }

            // Close/select menu on release
            if (!isHeld && RadialMenuHandler.menuOpen) {
                RadialMenuHandler.selectOption();
                RadialMenuHandler.closeMenu();
                client.setScreen(null);
            }
        });
    }
}