package net.scarab.lorienlegacies;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.effect.active_effects.GlacenEffect;
import net.scarab.lorienlegacies.effect.active_effects.LumenEffect;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.event.LorienLegacyEventHandler;
import net.scarab.lorienlegacies.item.ModItemGroup;
import net.scarab.lorienlegacies.item.ModItems;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import net.scarab.lorienlegacies.potion.ModPotions;
import net.scarab.lorienlegacies.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler.resetStress;

public class LorienLegaciesMod implements ModInitializer {
	public static final String MOD_ID = "lorienlegacies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof LivingEntity target) {
				GlacenEffect.icicles(player, target);
				GlacenEffect.iceHands(player, target);
				LumenEffect.flamingHands(player, target);
				LumenEffect.burnOnHit(player, target);
			}
			return ActionResult.PASS;
		});

		// Registering the server tick event to run stressManager on each player every tick
		ServerTickEvents.START_SERVER_TICK.register(server -> {
			server.getPlayerManager().getPlayerList().forEach(player -> {
				if (player instanceof ServerPlayerEntity) {
					LegacyBestowalHandler.stressManager(player);
				}
			});
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (player.getHealth() <= 0) {
					resetStress(player.getUuid());
				}
			}
		});

		ModItemGroup.registerItemGroups();

		ModItems.registerModItems();

		ModBlocks.registerModBlocks();

		ModEffects.registerEffects();

		LorienLegaciesModNetworking.registerC2SPackets();

		ModEntities.registerModEntities();

		LorienLegacyEventHandler.register();

		ModPotions.registerPotions();

		ModRegistries.registerModStuffs();

		MorphHandler.registerMorphHandler();

		LegacyBestowalHandler.registerLegacyBestowalHandler();
	}
}