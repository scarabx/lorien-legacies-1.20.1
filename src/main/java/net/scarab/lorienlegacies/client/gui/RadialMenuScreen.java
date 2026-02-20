package net.scarab.lorienlegacies.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class RadialMenuScreen extends Screen {

    public RadialMenuScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);
        RadialMenuHandler.render(drawContext, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (amount > 0) {
            RadialMenuHandler.nextPage();
            return true;
        }
        if (amount < 0) {
            RadialMenuHandler.previousPage();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
