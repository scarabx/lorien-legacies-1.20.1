package net.scarab.lorienlegacies;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.effect.GlacenEffect;
import net.scarab.lorienlegacies.effect.LumenEffect;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.item.ModItemGroup;
import net.scarab.lorienlegacies.item.ModItems;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LorienLegaciesMod implements ModInitializer {
	public static final String MOD_ID = "lorienlegacies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		AttackEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
			if (target instanceof LivingEntity) {
				LumenEffect.burnOnHit(player, target); // Call your method!
			}
			return ActionResult.PASS;
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof LivingEntity target) {
				LumenEffect.flamingHands(player, target);
			}
			return ActionResult.PASS;
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof LivingEntity target) {
				GlacenEffect.icicles(player, target);
			}
			return ActionResult.PASS;
		});



		ModItemGroup.registerItemGroups();

		ModItems.registerModItems();

		ModBlocks.registerModBlocks();

		ModEffects.registerEffects();

		LorienLegaciesModNetworking.registerC2SPackets();

		ModEntities.registerModEntities();
	}
}