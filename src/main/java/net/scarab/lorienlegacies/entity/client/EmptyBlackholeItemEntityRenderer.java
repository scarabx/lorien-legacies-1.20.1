package net.scarab.lorienlegacies.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.scarab.lorienlegacies.entity.EmptyBlackHoleEntity;
import net.scarab.lorienlegacies.entity.JoustStaffEntity;

public class EmptyBlackholeItemEntityRenderer extends FlyingItemEntityRenderer<EmptyBlackHoleEntity> {
    public EmptyBlackholeItemEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
