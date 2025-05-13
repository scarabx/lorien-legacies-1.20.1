package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.scarab.lorienlegacies.legacy.TelekinesisManager;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisToggles;
import net.scarab.lorienlegacies.legacy_toggle.TelekinesisTogglesAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class LegacyTogglesPlayerEntityMixin implements TelekinesisTogglesAccess {

    // Telekinesis toggles data
    private TelekinesisToggles telekinesisToggles = new TelekinesisToggles(false, false, false);

    @Override
    public TelekinesisToggles getTelekinesisToggles() {
        return telekinesisToggles;
    }

    @Override
    public void setTelekinesisToggles(TelekinesisToggles toggles) {
        this.telekinesisToggles = toggles;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        // Ensure that this is running for a ServerPlayerEntity
        if ((Object) this instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
            // Call TelekinesisManager to handle telekinesis effects
            TelekinesisManager.handleTelekinesis(player);
        }
    }
}

