package net.scarab.lorienlegacies.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

public class ModCommands {

    private static final Map<Integer, String> PAGE_TITLES = new HashMap<>();

    static {
        PAGE_TITLES.put(1, "Cooldowns");
        PAGE_TITLES.put(2, "Abilities");
        PAGE_TITLES.put(3, "Abilities");
        PAGE_TITLES.put(4, "Abilities");
        PAGE_TITLES.put(5, "Abilities");
        PAGE_TITLES.put(6, "Abilities");
        PAGE_TITLES.put(7, "Abilities");
        PAGE_TITLES.put(8, "Abilities");
        PAGE_TITLES.put(9, "Abilities");
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("modhelp")
                    .executes(ctx -> {
                        sendHelpPage(ctx.getSource(), 1);
                        return 1;
                    })
                    .then(CommandManager.argument("page", IntegerArgumentType.integer(1, 9))
                            .executes(ctx -> {
                                int page = IntegerArgumentType.getInteger(ctx, "page");
                                sendHelpPage(ctx.getSource(), page);
                                return 1;
                            }))
            );
        });
    }

    private static void sendHelpPage(ServerCommandSource source, int page) {
        String title = PAGE_TITLES.getOrDefault(page, "Unknown");

        source.sendFeedback(() -> Text.literal("=== Lorien Legacies Help - " + title + " (Page " + page + ") ===")
                .styled(style -> style.withColor(Formatting.GOLD).withBold(true)), false);

        switch (page) {
            case 1 -> {
                source.sendFeedback(() -> Text.literal("Acquire 100 stress points by hitting hostiles or by being hit by hostiles for a 33% chance of gaining a random legacy.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("When low on health you get cyclically effected with 10 seconds of stamina and 5 seconds of tired. During tired, you cannot use your legacies.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("When activating Pondus Impenetrable Skin or Pondus Intangibility you get cyclically effected with 10s of Pondus stamina and 5 seconds of Pondus cooldown during which you cannot use Impenetrable Skin or Intangibility.").styled(s -> s.withColor(Formatting.RED)), false);
            }
            case 2 -> {
                source.sendFeedback(() -> Text.literal("Activate legacy abilities with a radial menu opened using the R key.").styled(s -> s.withColor(Formatting.BLUE)), false);
                source.sendFeedback(() -> Text.literal("Fireball, Iceball, and Lightning are activated via the radial menu and triggered with left-click.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Left-click abilities auto-remove each other.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Telekinesis Push, Pull, Deflect, and Morph/Mark/Call Chimaera are activated with the radial menu and triggered with right-click.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Right-click abilities auto-remove each other.").styled(s -> s.withColor(Formatting.YELLOW)), false);
            }
            case 3 -> {
                source.sendFeedback(() -> Text.literal("Accelix: Run fast.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Avex: Superman pose flight and slow falling.").styled(s -> s.withColor(Formatting.BLUE)), false);
                source.sendFeedback(() -> Text.literal("Glacen: Iceball, Ice Hands and Freeze Water.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Kinetic Detonation: Convert items into throwable, explosive projectiles.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Lumen: Fireball, Flaming Hands, Human Fireball and Fire Resistance.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Novis: Invisibility.").styled(s -> s.withColor(Formatting.WHITE)), false);
                source.sendFeedback(() -> Text.literal("Noxen: Night Vision.").styled(s -> s.withColor(Formatting.GREEN)), false);
            }
            case 4 -> {
                source.sendFeedback(() -> Text.literal("Pondus: Impenetrable Skin and Intangibility.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Regeneras: Heal fast.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Sturma: Strike foes with lightning and control the weather.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Submari: Breathe underwater.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Tactile Consciousness Transfer: Possess entities.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Teletras: Teleportation.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Ximic: Acquire all powers.").styled(s -> s.withColor(Formatting.WHITE)), false);
            }
            case 5 -> {
                source.sendFeedback(() -> Text.literal("Accelix: Activate with radial menu.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Avex: Hold/toggle shift and jump to activate.").styled(s -> s.withColor(Formatting.BLUE)), false);
                source.sendFeedback(() -> Text.literal("Glacen Iceball: Shoot iceball. Activated with radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Glacen Ice Hands: Freeze target on hit. Activated with radial menu.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Glacen Freeze Water: Freeze water you look at. Activated with radial menu.").styled(s -> s.withColor(Formatting.AQUA)), false);
            }
            case 6 -> {
                source.sendFeedback(() -> Text.literal("Kinetic Detonation: Mainhand item is converted into a throwable, explosive item. Activated with radial menu.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Lumen Fireball: Shoot fireball. Activated with radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Lumen Human Fireball: Set entities and blocks around you on fire").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Lumen Fire Hands : Freeze target on hit. Activated with radial menu.").styled(s -> s.withColor(Formatting.YELLOW)), false);
            }
            case 7 -> {
                source.sendFeedback(() -> Text.literal("Lumen grants passive fire resistance and you set entities you hit on fire when on fire.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Novis: Become invisible. Activated with radial menu.").styled(s -> s.withColor(Formatting.WHITE)), false);
                source.sendFeedback(() -> Text.literal("Noxen: Gives night vision. Auto activates at night, in darkness, inside blocks and underwater.").styled(s -> s.withColor(Formatting.GREEN)), false);
                source.sendFeedback(() -> Text.literal("Pondus Impenetrable Skin: Grants near-invincibility and strength and projectiles bounce off you.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
            }
            case 8 -> {
                source.sendFeedback(() -> Text.literal("Pondus Intangibility: Enables passing through blocks and entities and gives creative style flight that is infinite inside blocks and deactivates after 5 seconds outside blocks. Grants invincibility and projectiles pass through you.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Regeneras: Automatically heal fast when low on health.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Sturma Lightning: Strike your foes with lightning. Activated with radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.YELLOW)), false);
            }
            case 9 -> {
                source.sendFeedback(() -> Text.literal("Sturma Weather Control: Switch between weather control modes in the order of Rain, Thunder and Clear. Activated with radial menu.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Submari: Passively breath underwater.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Tactile Consciousness Transfer: Attach to looked at entity, become invisible, become blocked from using weapons and move mob around.").styled(s -> s.withColor(Formatting.YELLOW)), false);
            }
            default -> {
                source.sendFeedback(() -> Text.literal("No help content for page " + page).styled(s -> s.withColor(Formatting.DARK_RED)), false);
            }
        }
    }
}
