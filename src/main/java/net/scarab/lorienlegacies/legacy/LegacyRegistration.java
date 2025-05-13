package net.scarab.lorienlegacies.legacy;

import net.scarab.lorienlegacies.LorienLegaciesMod;

public class LegacyRegistration {

    public static final Legacy TELEKINESIS = register("telekinesis", new TelekinesisLegacy());

    private static Legacy register(String name, Legacy legacy) {
        return LegacyRegistry.register(name, legacy);
    }

    public static void registerLegacies() {
        LorienLegaciesMod.LOGGER.info("Registering Legacies for " + LorienLegaciesMod.MOD_ID);
    }
}
