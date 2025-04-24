package net.scarab.lorienlegacies.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.scarab.lorienlegacies.entity.IciclesEntity;
import net.scarab.lorienlegacies.entity.layer.ModModelLayers;

@Environment(EnvType.CLIENT)
public class IciclesEntityRenderer extends EntityRenderer<IciclesEntity> {
    private static final Identifier TEXTURE = new Identifier("lorienlegacies","textures/entity/icicles.png");
    private final IciclesEntityModel<IciclesEntity> model;

    public IciclesEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new IciclesEntityModel<>(context.getPart(ModModelLayers.ICICLES));
    }

    public void render(IciclesEntity iciclesEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float h = iciclesEntity.getAnimationProgress(g);
        if (h != 0.0F) {
            float j = 2.0F;
            if (h > 0.9F) {
                j *= (1.0F - h) / 0.1F;
            }

            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F - iciclesEntity.getYaw()));
            matrixStack.scale(-j, -j, j);
            float k = 0.03125F;
            matrixStack.translate(0.0, -0.626, 0.0);
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setAngles(iciclesEntity, h, 0.0F, 0.0F, iciclesEntity.getYaw(), iciclesEntity.getPitch());
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
            super.render(iciclesEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }
    }

    public Identifier getTexture(IciclesEntity iciclesEntity) {
        return TEXTURE;
    }
}
