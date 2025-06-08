package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.scarab.lorienlegacies.item.DiamondDagger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {

    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void preventDaggerMove(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        // ignoreer warning
        if (slotIndex < 0 || slotIndex >= ((ScreenHandler)(Object)this).slots.size()) return;
        Slot slot = ((ScreenHandler)(Object)this).slots.get(slotIndex);
        ItemStack stack = slot.getStack();
        if (stack.getItem() instanceof DiamondDagger dagger && dagger.isWristWrapped(stack)) {
            // Cancel move only if wrist wrap is enabled
            ci.cancel();
        }
    }
}
