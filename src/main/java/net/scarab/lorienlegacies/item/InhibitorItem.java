package net.scarab.lorienlegacies.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.inhibitor.tooltip.shift.1"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.inhibitor.tooltip.shift.2"));
            tooltip.add(Text.translatable("tooltip.lorienlegacies.inhibitor.tooltip.shift.3"));
        } else {
            tooltip.add(Text.translatable("tooltip.lorienlegacies.item.tooltip"));
        }
    }
}
