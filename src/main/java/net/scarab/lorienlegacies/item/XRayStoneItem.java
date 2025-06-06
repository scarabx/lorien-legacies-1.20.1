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

public class XRayStoneItem extends Item {

    public XRayStoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            // Check if cooldown active
            if (user.getItemCooldownManager().isCoolingDown(this)) {
                // Optionally notify player or just fail silently
                return TypedActionResult.fail(stack);
            }
            double radius = 30.0F;
            List<LivingEntity> nearbyEntities = world.getEntitiesByClass(
                    LivingEntity.class,
                    user.getBoundingBox().expand(radius),
                    entity -> entity != user && entity.isAlive()
            );
            for (LivingEntity entity : nearbyEntities) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false, false));
            }
            // Apply cooldown: 6000 ticks = 5 minutes
            user.getItemCooldownManager().set(this, 6000);
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}