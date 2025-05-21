package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    /**
     * Tick-based deflection for nearby players with Impenetrable Skin (and both active).
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void deflectNearImpenetrablePlayer(CallbackInfo ci) {
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;

        for (Entity entity : projectile.getWorld().getOtherEntities(projectile, projectile.getBoundingBox().expand(0.3))) {
            if (entity instanceof PlayerEntity player &&
                    player.hasStatusEffect(ModEffects.PONDUS)) {

                boolean hasImpenetrableSkin = player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED);
                boolean hasIntangibility = player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY) && !player.hasStatusEffect(ModEffects.TIRED);

                // Only deflect if Impenetrable Skin is active or both are active
                if ((hasImpenetrableSkin && !hasIntangibility) || (hasImpenetrableSkin && hasIntangibility)) {
                    deflectProjectileFrom(projectile, player.getPos());
                    break; // Stop checking once a relevant player is found
                }
            }
        }
    }

    /**
     * On-hit deflection safeguard in case projectile already hits before tick deflection.
     */
    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void deflectIfTargetHasImpenetrableSkin(EntityHitResult hitResult, CallbackInfo ci) {
        Entity target = hitResult.getEntity();

        if (target instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS)) {

            boolean hasImpenetrableSkin = player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED);
            boolean hasIntangibility = player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY) && !player.hasStatusEffect(ModEffects.TIRED);

            // If only Impenetrable Skin is active, or both are active, deflect the projectile
            if ((hasImpenetrableSkin && !hasIntangibility) || (hasImpenetrableSkin && hasIntangibility)) {
                ProjectileEntity projectile = (ProjectileEntity) (Object) this;
                deflectProjectileFrom(projectile, target.getPos());
                ci.cancel(); // Prevent normal hit behavior
            }
        }
    }

    /**
     * Utility method to deflect a projectile away from a point.
     */
    private void deflectProjectileFrom(ProjectileEntity projectile, Vec3d fromPosition) {
        Vec3d velocity = projectile.getVelocity();
        Vec3d direction = projectile.getPos().subtract(fromPosition).normalize();
        Vec3d deflected = velocity.subtract(direction.multiply(2 * velocity.dotProduct(direction)));

        // Add random variation to the deflection for realism
        deflected = deflected.add(new Vec3d(
                Math.random() * 0.1 - 0.05,
                Math.random() * 0.1 - 0.05,
                Math.random() * 0.1 - 0.05
        ));

        projectile.setVelocity(deflected);
    }

    /**
     * Prevent projectiles from hitting intangible players, or bounce off if Impenetrable Skin is active.
     */
    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void preventHitIfIntangible(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS)) {

            boolean hasImpenetrableSkin = player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED);
            boolean hasIntangibility = player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY) && !player.hasStatusEffect(ModEffects.TIRED);

            // If only Intangibility is active, allow projectiles to pass through
            if (hasIntangibility && !hasImpenetrableSkin) {
                cir.setReturnValue(false); // Prevent projectile hit (pass through)
            }

            // If only Impenetrable Skin is active, or both are active, allow deflection (bounce off)
            else if (hasImpenetrableSkin && !hasIntangibility || (hasImpenetrableSkin && hasIntangibility)) {
                cir.setReturnValue(true); // Allow deflection
            }
        }
    }

    /**
     * Prevent projectile hit detection for intangible players or allow bouncing if only Impenetrable Skin is active.
     */
    @Inject(method = "onEntityHit", at = @At("HEAD"), cancellable = true)
    private void skipHitIfTargetIntangible(EntityHitResult hitResult, CallbackInfo ci) {
        Entity target = hitResult.getEntity();

        if (target instanceof PlayerEntity player &&
                player.hasStatusEffect(ModEffects.PONDUS)) {

            boolean hasImpenetrableSkin = player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN) && !player.hasStatusEffect(ModEffects.TIRED);
            boolean hasIntangibility = player.hasStatusEffect(ModEffects.TOGGLE_INTANGIBILITY) && !player.hasStatusEffect(ModEffects.TIRED);

            // If Intangibility is active, skip the hit (pass through), else if Impenetrable Skin is active or both, bounce off
            if (hasIntangibility && !hasImpenetrableSkin) {
                ci.cancel(); // Don't apply projectile hit (pass through)
            } else if (hasImpenetrableSkin && !hasIntangibility || (hasImpenetrableSkin && hasIntangibility)) {
                // The deflection logic will handle this case
            }
        }
    }
}
