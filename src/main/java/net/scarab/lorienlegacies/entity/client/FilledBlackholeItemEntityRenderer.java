package net.scarab.lorienlegacies.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.scarab.lorienlegacies.entity.EmptyBlackHoleEntity;
import net.scarab.lorienlegacies.entity.FilledBlackHoleEntity;

public class FilledBlackholeItemEntityRenderer extends FlyingItemEntityRenderer<FilledBlackHoleEntity> {
    public FilledBlackholeItemEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
