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
    private double accumulatedScroll = 0;
    private boolean scrollCallbackSet = false;
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

            // Set up scroll callback once window is available
            if (!scrollCallbackSet && client.getWindow() != null) {
                GLFW.glfwSetScrollCallback(client.getWindow().getHandle(), (window, xoffset, yoffset) -> {
                    if (RadialMenuHandler.menuOpen) {
                        accumulatedScroll += yoffset;
                    }
                });
                scrollCallbackSet = true;
            }

            // Check if the rebound key is physically held down
            InputUtil.Key boundKey = KeyBindingHelper.getBoundKeyOf(openRadialMenuKey);
            boolean isPressed = false;
            if (boundKey != null) {
                isPressed = InputUtil.isKeyPressed(
                        client.getWindow().getHandle(),
                        boundKey.getCode()
                );
            }

            // Open menu on key press
            if (isPressed && !RadialMenuHandler.menuOpen) {
                RadialMenuHandler.menuOpen = true;
                // Store current mouse grab state and release mouse for cursor control
                mouseGrabbedState = client.mouse.isCursorLocked();
                client.mouse.unlockCursor();
            }

            // Close AND SELECT menu on key release (ORIGINAL BEHAVIOR)
            if (!isPressed && wasKeyPressed && RadialMenuHandler.menuOpen) {
                RadialMenuHandler.selectOption(); // Select based on cursor position
                RadialMenuHandler.closeMenu();
                // Restore mouse grab state
                if (mouseGrabbedState) {
                    client.mouse.lockCursor();
                }
            }

            wasKeyPressed = isPressed;

            // Handle mouse input when menu is open
            if (RadialMenuHandler.menuOpen) {
                // Handle accumulated scroll for page switching
                if (accumulatedScroll != 0) {
                    if (accumulatedScroll > 0) {
                        RadialMenuHandler.previousPage();
                    } else {
                        RadialMenuHandler.nextPage();
                    }
                    accumulatedScroll = 0;
                }
            } else {
                accumulatedScroll = 0; // Reset scroll when menu closes
            }
        });
    }
}