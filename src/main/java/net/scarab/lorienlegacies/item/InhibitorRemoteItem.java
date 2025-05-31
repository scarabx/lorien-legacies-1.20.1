package net.scarab.lorienlegacies.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;
import static net.scarab.lorienlegacies.effect.ModEffects.LEGACY_INHIBITION;

public class InhibitorRemoteItem extends Item {

    public InhibitorRemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient()) {
            double maxDistance = 10.0;
            Vec3d eyePos = user.getCameraPosVec(1.0F);
            Vec3d lookVec = user.getRotationVec(1.0F);
            Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
            Box box = user.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);
            Entity lookedAtEntity = null;
            double closestDistance = maxDistance * maxDistance;
            for (Entity entity : world.getOtherEntities(user, box, e -> e instanceof PlayerEntity && e.isAlive() && e != user)) {
                Box entityBox = entity.getBoundingBox().expand(0.3);
                Optional<Vec3d> optional = entityBox.raycast(eyePos, reachVec);
                if (optional.isPresent()) {
                    double distance = eyePos.squaredDistanceTo(optional.get());
                    if (distance < closestDistance) {
                        lookedAtEntity = entity;
                        closestDistance = distance;
                    }
                }
            }
            if (lookedAtEntity instanceof PlayerEntity target && target.hasStatusEffect(LEGACY_INHIBITION)) {
                target.addStatusEffect(new StatusEffectInstance(ACTIVE_LEGACY_INHIBITION, 100, 0));
            }
        }
        // Return success and consume the item if you want
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }
}
