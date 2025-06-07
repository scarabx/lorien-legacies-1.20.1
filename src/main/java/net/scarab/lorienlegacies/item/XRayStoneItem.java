package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

import static net.scarab.lorienlegacies.effect.ModEffects.X_RAY_STONE_COOLDOWN;

public class XRayStoneItem extends Item {

    public XRayStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!user.getWorld().isClient() && !user.hasStatusEffect(X_RAY_STONE_COOLDOWN)) {

            // Apply glowing effect
            double radius = 30.0F;
            List<LivingEntity> nearbyEntities = world.getEntitiesByClass(
                    LivingEntity.class,
                    user.getBoundingBox().expand(radius),
                    entity -> entity != user && entity.isAlive()
            );
            for (LivingEntity entity : nearbyEntities) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false, false));
                user.addStatusEffect(new StatusEffectInstance(X_RAY_STONE_COOLDOWN, 6000, 0, false, false, false));
            }
        }

        ItemStack stack = user.getStackInHand(hand);

        return TypedActionResult.success(stack, world.isClient());
    }
}

