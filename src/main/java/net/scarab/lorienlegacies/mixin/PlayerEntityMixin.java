package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.scarab.lorienlegacies.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow @Final public PlayerScreenHandler playerScreenHandler;

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.hasStatusEffect(ModEffects.IMPENETRABLE_SKIN) &&
                player.hasStatusEffect(ModEffects.TOGGLE_IMPENETRABLE_SKIN)) {

            Entity attacker = source.getSource();

            boolean isMobAttack = attacker instanceof MobEntity || attacker instanceof PlayerEntity;
            boolean isProjectile = attacker instanceof ProjectileEntity;

            boolean isFall = source.isOf(DamageTypes.FALL);

            if (isMobAttack || isProjectile || isFall) {
                cir.setReturnValue(false);
            }
        }

        // Cancel suffocation damage if Intangibility is active
        if (player.hasStatusEffect(ModEffects.INTANGIBILITY) && source.isOf(DamageTypes.IN_WALL)) {
            cir.setReturnValue(false);
        }
    }
}
