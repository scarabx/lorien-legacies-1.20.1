package net.scarab.lorienlegacies;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.math.Vec3d;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.entity.client.*;
import net.scarab.lorienlegacies.entity.layer.ModModelLayers;
import net.scarab.lorienlegacies.util.ModModelPredicateProvider;

import static net.scarab.lorienlegacies.effect.ModEffects.PONDUS;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_INTANGIBILITY;

public class LorienLegaciesModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CHIMAERA_PARROT, ChimaeraParrotEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ICICLES, IciclesEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.THROWN_ICEBALL_PROJECTILE, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.THROWN_KINETIC_PROJECTILE, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.THROWN_SHOCK_COLLAR_PROJECTILE, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.ICICLES, IciclesEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.CHIMAERA_PARROT, ChimaeraParrotEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.JOUST_STAFF, JoustStaffItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPIKY_YELLOW_BALL, SpikyYellowBallItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.SPIKY_BLACK_BALL, SpikyBlackBallItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.EMPTY_BLACK_HOLE, EmptyBlackholeItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.FILLED_BLACK_HOLE, FilledBlackholeItemEntityRenderer::new);

        ModModelPredicateProvider.registerModModels();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Only activate if player exists and has required status effects
            if (client.player != null && client.player.hasStatusEffect(PONDUS) && client.player.hasStatusEffect(TOGGLE_INTANGIBILITY)) {

                // Base movement parameters for normal flight
                double baseMultiplier = 0.05;  // Acceleration factor
                double maxSpeed = 1.5;        // Terminal velocity

                // Handle active forward movement
                if (client.options.forwardKey.isPressed()) {
                    // Get player's look direction vector
                    Vec3d look = client.player.getRotationVec(1.0F);
                    // Calculate new velocity with acceleration
                    Vec3d velocity = client.player.getVelocity().add(look.multiply(baseMultiplier));
                    // Enforce speed limit
                    if (velocity.length() > maxSpeed) {
                        velocity = velocity.normalize().multiply(maxSpeed);
                    }
                    client.player.setVelocity(velocity);
                }
            }
        });
    }
}

