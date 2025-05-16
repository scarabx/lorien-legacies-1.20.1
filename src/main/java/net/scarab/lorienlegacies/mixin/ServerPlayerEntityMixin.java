package net.scarab.lorienlegacies.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
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

        // Save stress and legacy cooldown data
        int stress = LegacyBestowalHandler.getStress(player);
        long lastLegacyTime = LegacyBestowalHandler.getLastLegacyTime(player);

        nbt.putInt("lorien_stress", stress);
        nbt.putLong("lorien_lastLegacyTime", lastLegacyTime);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        // Load stress and legacy cooldown data
        if (nbt.contains("lorien_stress")) {
            LegacyBestowalHandler.setStress(player, nbt.getInt("lorien_stress"));
        }
        if (nbt.contains("lorien_lastLegacyTime")) {
            LegacyBestowalHandler.setLastLegacyTime(player, nbt.getLong("lorien_lastLegacyTime"));
        }
    }
}
