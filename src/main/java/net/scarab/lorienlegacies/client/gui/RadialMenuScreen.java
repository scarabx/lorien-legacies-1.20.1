package net.scarab.lorienlegacies.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class RadialMenuScreen extends Screen {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public RadialMenuScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        RadialMenuHandler.render(drawContext, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            RadialMenuHandler.selectOption();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            RadialMenuHandler.nextPage();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_R || keyCode == GLFW.GLFW_KEY_ESCAPE) {
            RadialMenuHandler.closeMenu();
            client.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
