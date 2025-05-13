package net.scarab.lorienlegacies.legacy;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

public class Legacy {

    protected TrackedData<Boolean> legacyTracker;

    public Legacy() {
        this.legacyTracker = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public boolean hasLegacy(PlayerEntity player) {
        return player.getDataTracker().get(legacyTracker);
    }

    public void setLegacy(PlayerEntity player, boolean value) {
        player.getDataTracker().set(legacyTracker, value);
    }

    public TrackedData<Boolean> getLegacyTracker() {
        return legacyTracker;
    }
}
