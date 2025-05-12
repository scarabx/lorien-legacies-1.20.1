package net.scarab.lorienlegacies.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.entity.layer.ModModelLayers;

@Environment(EnvType.CLIENT)
public class ShoulderChimaeraParrotFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    private final ChimaeraParrotEntityModel model;

    public ShoulderChimaeraParrotFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context, EntityModelLoader loader) {
        super(context);
        this.model = new ChimaeraParrotEntityModel(loader.getModelPart(ModModelLayers.CHIMAERA_PARROT));
    }

    public void render(
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l
    ) {
        this.renderShoulderParrot(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, true);
        this.renderShoulderParrot(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, false);
    }

    private void renderShoulderParrot(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            T player,
            float limbAngle,
            float limbDistance,
            float headYaw,
            float headPitch,
            boolean leftShoulder
    ) {
        NbtCompound nbtCompound = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        EntityType.get(nbtCompound.getString("id")).filter(type -> type == ModEntities.CHIMAERA_PARROT).ifPresent(type -> {
            matrices.push();
            matrices.translate(leftShoulder ? 0.4F : -0.4F, player.isInSneakingPose() ? -1.3F : -1.5F, 0.0F);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(ChimaeraParrotEntityRenderer.DEFAULT_TEXTURE));
            this.model.poseOnShoulder(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, limbAngle, limbDistance, headYaw, headPitch, player.age);
            matrices.pop();
        });
    }
}
