package net.scarab.lorienlegacies.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.scarab.lorienlegacies.stats.KillTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (!(self instanceof Monster)) return;

        if (source.getAttacker() instanceof ServerPlayerEntity player) {
            player.sendMessage(Text.literal(player.getName().getString() + " killed a hostile mob: " + self.getType().getName().getString()), false);
            KillTracker.onMobKilled(player);
        }
    }
}
