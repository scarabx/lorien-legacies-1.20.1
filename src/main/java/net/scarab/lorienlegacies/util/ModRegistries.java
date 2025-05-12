package net.scarab.lorienlegacies.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.scarab.lorienlegacies.entity.ChimaeraParrotEntity;
import net.scarab.lorienlegacies.entity.ModEntities;

public class ModRegistries {

    public static void registerModStuffs() {
        registerAttributes();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.CHIMAERA_PARROT, ChimaeraParrotEntity.createChimaeraParrotAttributes());
    }
}
