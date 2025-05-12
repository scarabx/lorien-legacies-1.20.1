package net.scarab.lorienlegacies.entity.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;

public class ModModelLayers {

    public static final EntityModelLayer CHIMAERA_PARROT =
            new EntityModelLayer(new Identifier(LorienLegaciesMod.MOD_ID, "chimaera_parrot"), "main");

    public static final EntityModelLayer ICICLES =
            new EntityModelLayer(new Identifier(LorienLegaciesMod.MOD_ID, "icicles"), "main");
}
