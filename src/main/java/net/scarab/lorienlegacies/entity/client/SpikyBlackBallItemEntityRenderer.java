package net.scarab.lorienlegacies.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.scarab.lorienlegacies.entity.SpikyBlackBallEntity;
import net.scarab.lorienlegacies.entity.SpikyYellowBallEntity;

public class SpikyBlackBallItemEntityRenderer extends FlyingItemEntityRenderer<SpikyBlackBallEntity> {

    public SpikyBlackBallItemEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
