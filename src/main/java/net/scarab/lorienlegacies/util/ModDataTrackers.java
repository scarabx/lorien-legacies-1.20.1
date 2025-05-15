package net.scarab.lorienlegacies.util;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class ModDataTrackers {

    // Define your tracked data keys
    public static final TrackedData<Boolean> SKIP_STAMINA_REMOVAL =
            DataTracker.registerData(net.minecraft.entity.player.PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
}
