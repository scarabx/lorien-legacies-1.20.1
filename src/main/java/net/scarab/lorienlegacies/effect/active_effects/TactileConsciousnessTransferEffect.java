package net.scarab.lorienlegacies.effect.active_effects;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

import static net.scarab.lorienlegacies.effect.ModEffects.TIRED;
import static net.scarab.lorienlegacies.effect.ModEffects.TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER;

public class TactileConsciousnessTransferEffect extends StatusEffect {

    public TactileConsciousnessTransferEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity player) {
            transferConsciousness(player);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static void transferConsciousness (PlayerEntity player) {

        if (player.getWorld().isClient()
            && player.hasStatusEffect(TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER)
            && !player.hasStatusEffect(TIRED)) {

            double maxDistance = 10.0;
            Vec3d eyePos = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F);
            Vec3d reachVec = eyePos.add(lookVec.multiply(maxDistance));
            World world = player.getWorld();
            Box box = player.getBoundingBox().stretch(lookVec.multiply(maxDistance)).expand(1.0);

            Entity lookedAtEntity = null;
            double closestDistance = maxDistance * maxDistance;

            for (Entity entity : world.getOtherEntities(player, box, e -> e instanceof LivingEntity && e.isAlive())) {
                Box entityBox = entity.getBoundingBox().expand(0.3);
                Optional<Vec3d> optional = entityBox.raycast(eyePos, reachVec);
                if (optional.isPresent()) {
                    double distance = eyePos.squaredDistanceTo(optional.get());
                    if (distance < closestDistance) {
                        lookedAtEntity = entity;
                        closestDistance = distance;
                    }
                }
            }

            // Change camera to the entity being looked at
            if (lookedAtEntity != null) {
                MinecraftClient.getInstance().setCameraEntity(lookedAtEntity);
            }
        }
    }
}
