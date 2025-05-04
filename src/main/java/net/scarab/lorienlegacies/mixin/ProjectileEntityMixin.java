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

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    /**
     * Tick-based deflection for nearby players with Impenetrable Skin.
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void deflectNearImpenetrablePlayer(CallbackInfo ci) {
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;

        for (Entity entity : projectile.getWorld().getOtherEntities(projectile, projectile.getBoundingBox().expand(0.3))) {
            if (entity instanceof PlayerEntity player &&
                    player.hasStatusEffect(ModEffects.PONDUS) &&
                    player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {

                deflectProjectileFrom(projectile, player.getPos());
                break;
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
                player.hasStatusEffect(ModEffects.PONDUS) &&
                player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {

            ProjectileEntity projectile = (ProjectileEntity) (Object) this;
            deflectProjectileFrom(projectile, target.getPos());
            ci.cancel();
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
}
