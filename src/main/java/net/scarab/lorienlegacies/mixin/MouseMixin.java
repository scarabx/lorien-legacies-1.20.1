package net.scarab.lorienlegacies.mixin;

import net.minecraft.client.Mouse;
import net.scarab.lorienlegacies.client.gui.RadialMenuHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        // Only intercept when menu is open
        if (RadialMenuHandler.menuOpen) {
            // Handle page switching
            if (vertical > 0) {
                RadialMenuHandler.previousPage();
            } else if (vertical < 0) {
                RadialMenuHandler.nextPage();
            }

            // Cancel so it doesn't affect game
            ci.cancel();
        }
        // When menu is closed, do nothing - let the event pass through to Minecraft's normal handling
    }
}