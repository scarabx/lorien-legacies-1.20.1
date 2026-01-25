package net.scarab.lorienlegacies.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* I understand this class

Injection methods in mixins that entails cancelling code behaviour, uses CallbackInfo ci

Injection methods in mixins that entails modifying code behaviour uses CallbackInfoReturnable cir

Mixin that prevents players with CONVERTED status effect from attacking entities and breaking blocks

ClientPlayerInteractionManager gives mixin access to player client actions

Client is the trigger, server is the gun that shoots, e.g. client detects leftclick/attack,

server checks if attacked entity is attackable and applies damage to attacked entity */

@Mixin(ClientPlayerInteractionManager.class)

// Mixin entails disabling attack, block break and milk drink

public class BlockMilkDrinkMixin {

    /* Prevents drinking milk

    Uses CallbackInfoReturnable cir because method entails drink milk on use/vanilla Minecraft

    behaviour becomes don't drink milk on use/don't use vanilla Minecraft behaviour

    Injection method injects behaviour into vanilla Minecraft's interactItem method code at the start

    of the method ( at = @At("HEAD") ) before vanilla Minecraft's interactItem method code is called

    thus enabling coder to add custom behaviour to their liking to vanilla Minecraft's interactItem method code

    and cancellable = true enables method to cancel vanilla Minecraft's interactItem method code */

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)

    private void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        // Get stack in player's hand

        ItemStack stack = player.getStackInHand(hand);

        // Check if the above determined stack is a Milk Bucket

        if (stack.getItem() == Items.MILK_BUCKET) {

            // Cancels drink milk bucket on use/rightclick

            cir.setReturnValue(ActionResult.FAIL);

        }
    }
}