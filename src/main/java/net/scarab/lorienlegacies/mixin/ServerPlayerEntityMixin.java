package net.scarab.lorienlegacies.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.item.DiamondDaggerItem;
import net.scarab.lorienlegacies.item.LeatherSleeveItem;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        nbt.putInt("lorien_stress", LegacyBestowalHandler.getStress(player));
        ItemStack mainHandStack = player.getMainHandStack();
        if (mainHandStack.getItem() instanceof DiamondDaggerItem) {
            boolean wristWrapped = mainHandStack.hasNbt() && mainHandStack.getNbt().getBoolean(DiamondDaggerItem.WRIST_WRAPPED_KEY);
            nbt.putBoolean(DiamondDaggerItem.WRIST_WRAPPED_KEY, wristWrapped);
        }
        if (mainHandStack.getItem() instanceof LeatherSleeveItem) {
            boolean extended = mainHandStack.hasNbt() && mainHandStack.getNbt().getBoolean(LeatherSleeveItem.EXTENDED_KEY);
            nbt.putBoolean(LeatherSleeveItem.EXTENDED_KEY, extended);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (nbt.contains("lorien_stress")) {
            LegacyBestowalHandler.setStress(player, nbt.getInt("lorien_stress"));
        }
        if (nbt.contains(DiamondDaggerItem.WRIST_WRAPPED_KEY)) {
            boolean wristWrapped = nbt.getBoolean(DiamondDaggerItem.WRIST_WRAPPED_KEY);
            ItemStack mainHandStack = player.getMainHandStack();
            if (mainHandStack.getItem() instanceof DiamondDaggerItem) {
                mainHandStack.getOrCreateNbt().putBoolean(DiamondDaggerItem.WRIST_WRAPPED_KEY, wristWrapped);
            }
        }
        if (nbt.contains(LeatherSleeveItem.EXTENDED_KEY)) {
            boolean extended = nbt.getBoolean(LeatherSleeveItem.EXTENDED_KEY);
            ItemStack mainHandStack = player.getMainHandStack();
            if (mainHandStack.getItem() instanceof LeatherSleeveItem) {
                mainHandStack.getOrCreateNbt().putBoolean(LeatherSleeveItem.EXTENDED_KEY, extended);
            }
        }
    }
}
