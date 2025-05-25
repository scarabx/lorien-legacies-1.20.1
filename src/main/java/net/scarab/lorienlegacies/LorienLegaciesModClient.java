package net.scarab.lorienlegacies;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.client.gui.RadialMenuHandler;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.entity.client.*;
import net.scarab.lorienlegacies.entity.layer.ModModelLayers;

public class LorienLegaciesModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CHIMAERA_PARROT, ChimaeraParrotEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ICICLES, IciclesEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.THROWN_ICEBALL_PROJECTILE, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.ICICLES, IciclesEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.CHIMAERA_PARROT, ChimaeraParrotEntityRenderer::new);

    }
}
