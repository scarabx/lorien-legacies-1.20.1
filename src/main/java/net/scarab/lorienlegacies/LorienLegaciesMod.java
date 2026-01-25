package net.scarab.lorienlegacies;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.scarab.lorienlegacies.block.ModBlocks;
import net.scarab.lorienlegacies.chimaera.MorphHandler;
import net.scarab.lorienlegacies.command.ModCommands;
import net.scarab.lorienlegacies.effect.active_effects.GlacenEffect;
import net.scarab.lorienlegacies.effect.active_effects.LumenEffect;
import net.scarab.lorienlegacies.effect.ModEffects;
import net.scarab.lorienlegacies.entity.ModEntities;
import net.scarab.lorienlegacies.event.LorienLegacyEventHandler;
import net.scarab.lorienlegacies.item.DiamondDaggerItem;
import net.scarab.lorienlegacies.item.ModItemGroup;
import net.scarab.lorienlegacies.item.ModItems;
import net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;
import net.scarab.lorienlegacies.potion.ModPotions;
import net.scarab.lorienlegacies.util.ModLootTableModifiers;
import net.scarab.lorienlegacies.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.scarab.lorienlegacies.legacy_bestowal.LegacyBestowalHandler.resetStress;

public class LorienLegaciesMod implements ModInitializer {
	public static final String MOD_ID = "lorienlegacies";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static int wristWrappedSlot = -1;

	@Override
	public void onInitialize() {

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.player;
			player.sendMessage(
					Text.empty()
							.append(Text.literal("[Lorien Legacies] ").formatted(Formatting.GOLD, Formatting.BOLD))
							.append(Text.literal("Use ").formatted(Formatting.YELLOW))
							.append(Text.literal("/ll modhelp index ").formatted(Formatting.AQUA, Formatting.BOLD))
							.append(Text.literal("to find specific help pages. ").formatted(Formatting.YELLOW))
							.append(Text.literal("Use ").formatted(Formatting.YELLOW))
							.append(Text.literal("/ll modhelp <page number>").formatted(Formatting.AQUA, Formatting.BOLD))
							.append(Text.literal(" for instructions on how to use the mod. ").formatted(Formatting.YELLOW))
							.append(Text.literal("NEVER DRINK MILK OR YOU WILL LOSE YOUR LEGACY!").formatted(Formatting.RED)),
					false
			);
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient && entity instanceof LivingEntity target) {
				GlacenEffect.icicles(player, target);
				GlacenEffect.iceHands(player, target);
				LumenEffect.flamingHands(player, target);
				if (!player.hasStatusEffect(ModEffects.TIRED)
						&& !player.hasStatusEffect(ModEffects.ACTIVE_LEGACY_INHIBITION)) {
					LumenEffect.burnOnHit(player, target);
				}
			}
			return ActionResult.PASS;
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (player.hasStatusEffect(ModEffects.ACTIVE_TACTILE_CONSCIOUSNESS_TRANSFER)) {
				ItemStack stack = player.getStackInHand(hand);
				if (stack.getItem() instanceof RangedWeaponItem) {
					return TypedActionResult.fail(stack); // Cancel the use
				}
			}
			return TypedActionResult.pass(player.getStackInHand(hand));
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

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ClientPlayerEntity player = client.player;
			if (player == null) return;

			// Look for the wrist wrapped dagger slot in hotbar (0-8)
			int foundSlot = -1;
			for (int i = 0; i < 9; i++) {
				ItemStack stack = player.getInventory().getStack(i);
				if (stack.getItem() instanceof DiamondDaggerItem dagger && dagger.isWristWrapped(stack)) {
					foundSlot = i;
					break;
				}
			}

			// Update wristWrappedSlot accordingly
			wristWrappedSlot = foundSlot;

			int selectedSlot = player.getInventory().selectedSlot;

			if (wristWrappedSlot != -1 && selectedSlot != wristWrappedSlot) {
				// Force back to wrist wrapped dagger slot client-side
				player.getInventory().selectedSlot = wristWrappedSlot;
			}
		});

		ModCommands.register();

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

		ModLootTableModifiers.modifyLootTables();
	}
}
