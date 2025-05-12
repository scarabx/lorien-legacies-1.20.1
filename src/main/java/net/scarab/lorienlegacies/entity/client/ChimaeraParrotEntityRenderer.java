package net.scarab.lorienlegacies.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.scarab.lorienlegacies.entity.ChimaeraParrotEntity;
import net.scarab.lorienlegacies.entity.layer.ModModelLayers;

@Environment(EnvType.CLIENT)
public class ChimaeraParrotEntityRenderer extends MobEntityRenderer<ChimaeraParrotEntity, ChimaeraParrotEntityModel> {
    public static final Identifier DEFAULT_TEXTURE = new Identifier("lorienlegacies","textures/entity/chimaera_parrot_red_blue.png");

    public ChimaeraParrotEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChimaeraParrotEntityModel(context.getPart(ModModelLayers.CHIMAERA_PARROT)), 0.3F);
    }

    public Identifier getTexture(ChimaeraParrotEntity chimaeraParrotEntity) {
        return DEFAULT_TEXTURE;
    }

    public float getAnimationProgress(ChimaeraParrotEntity chimaeraParrotEntity, float f) {
        float g = MathHelper.lerp(f, chimaeraParrotEntity.prevFlapProgress, chimaeraParrotEntity.flapProgress);
        float h = MathHelper.lerp(f, chimaeraParrotEntity.prevMaxWingDeviation, chimaeraParrotEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }
}
