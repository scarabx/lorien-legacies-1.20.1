package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.scarab.lorienlegacies.item.ModItems;

import static net.scarab.lorienlegacies.effect.ModEffects.*;

public class KineticDetonationEffect extends StatusEffect {

    public KineticDetonationEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (!entity.getWorld().isClient()
                && entity.hasStatusEffect(KINETIC_DETONATION)
                && entity.hasStatusEffect(TOGGLE_KINETIC_DETONATION)
                && !entity.hasStatusEffect(KINETIC_DETONATION_COOLDOWN)
                && !entity.hasStatusEffect(TIRED)
                && !entity.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

            if (entity instanceof PlayerEntity player) {
                ItemStack mainHand = player.getMainHandStack();
                if (mainHand.isOf(Items.COBBLESTONE) || mainHand.isOf(Items.DIRT) || mainHand.isOf(Items.SAND) || mainHand.isOf(Items.GRAVEL)) {
                    boolean hasKineticProjectile = false;
                    for (ItemStack stack : player.getInventory().main) {
                        if (stack.getItem() == ModItems.KINETIC_PROJECTILE) {
                            hasKineticProjectile = true;
                            break;
                        }
                    }
                    if (!hasKineticProjectile) {
                        mainHand.decrement(1);
                        player.getInventory().insertStack(new ItemStack(ModItems.KINETIC_PROJECTILE));
                    }
                }
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}