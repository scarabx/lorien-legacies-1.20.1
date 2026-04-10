package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;
import static net.scarab.lorienlegacies.effect.ModEffects.ACTIVE_LEGACY_INHIBITION;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_SHOOT_FIREBALL;

@Mixin(SmallFireballEntity.class)
public class SmallFireballEntityMixin {

    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {

        SmallFireballEntity fireball = (SmallFireballEntity)(Object)this;

        Entity owner = fireball.getOwner();

        if (owner instanceof PlayerEntity player) {

            if (player.hasStatusEffect(ModEffects.LUMEN) && player.hasStatusEffect(TOGGLE_SHOOT_FIREBALL) && !player.hasStatusEffect(TIRED) && !player.hasStatusEffect(ACTIVE_LEGACY_INHIBITION)) {

                Entity target = entityHitResult.getEntity();

                if (target instanceof LivingEntity livingEntity) {

                    livingEntity.setOnFireFor(5);

                    livingEntity.damage(player.getDamageSources().generic(), 18.0f);

                    ci.cancel();

                }
            }
        }
    }
}