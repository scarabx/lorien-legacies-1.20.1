package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public float prevHorizontalSpeed;

    // Prevent player from colliding with entities
    @Inject(method = "collidesWith", at = @At("HEAD"), cancellable = true)
    private void intangibleNoEntityCollision(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)
                && !player.hasStatusEffect(ModEffects.TIRED)) {
            cir.setReturnValue(false);
        }
    }

    // Prevent being selected as a collidable entity
    @Inject(method = "isCollidable", at = @At("HEAD"), cancellable = true)
    private void intangibleNotCollidable(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)
                && !player.hasStatusEffect(ModEffects.TIRED)) {
            cir.setReturnValue(false);
        }
    }

    // Prevent projectiles (or other logic) from raycasting to player
    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void intangibleCannotBeHit(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY)
                && !player.hasStatusEffect(ModEffects.TIRED)) {
            cir.setReturnValue(false);
        }
    }
}
