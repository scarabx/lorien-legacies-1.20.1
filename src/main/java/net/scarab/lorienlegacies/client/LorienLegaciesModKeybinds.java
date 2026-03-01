package net.scarab.lorienlegacies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.scarab.lorienlegacies.client.gui.RadialMenuHandler;
import org.lwjgl.glfw.GLFW;

public class LorienLegaciesModKeybinds implements ClientModInitializer {

    private static KeyBinding openRadialMenuKey;
    private boolean wasKeyPressed = false;
    private boolean mouseGrabbedState = false;

    @Override
    public void onInitializeClient() {

        openRadialMenuKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.lorienlegacies.open_radial_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.category.lorienlegacies.lorienlegacies"));

        // Register HUD renderer
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            RadialMenuHandler.render(drawContext, tickDelta);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Check if the rebound key is physically held down
            InputUtil.Key boundKey = KeyBindingHelper.getBoundKeyOf(openRadialMenuKey);
            boolean isPressed = false;
            if (boundKey != null) {
                isPressed = InputUtil.isKeyPressed(
                        client.getWindow().getHandle(),
                        boundKey.getCode()
                );
            }

            // Only allow opening the radial menu if no screen is currently open
            // (prevents opening in inventories, chat, etc.)
            boolean canOpenMenu = client.currentScreen == null;

            // Open menu on key press
            if (isPressed && !RadialMenuHandler.menuOpen && canOpenMenu) {
                RadialMenuHandler.menuOpen = true;
                // Store current mouse grab state and release mouse for cursor control
                mouseGrabbedState = client.mouse.isCursorLocked();
                client.mouse.unlockCursor();
            }

            // Close AND SELECT menu on key release
            if (!isPressed && wasKeyPressed && RadialMenuHandler.menuOpen) {
                RadialMenuHandler.selectOption();
                RadialMenuHandler.closeMenu();
                // Restore mouse grab state
                if (mouseGrabbedState) {
                    client.mouse.lockCursor();
                }
            }

            wasKeyPressed = isPressed;
        });
    }
}