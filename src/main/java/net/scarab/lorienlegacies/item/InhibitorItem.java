package net.scarab.lorienlegacies.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static net.scarab.lorienlegacies.effect.ModEffects.LEGACY_INHIBITION;

public class InhibitorItem extends Item {

    public InhibitorItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (target instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(LEGACY_INHIBITION, Integer.MAX_VALUE, 0, false, false, false));
        }
        stack.damage(1, attacker, (entity) -> entity.sendToolBreakStatus(entity.getActiveHand()));
        return super.postHit(stack, target, attacker);
    }
}
