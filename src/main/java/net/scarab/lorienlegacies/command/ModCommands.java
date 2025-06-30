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

    private record HelpPageInfo(String indexTitle, String pageHeading) {}

    private static final Map<Integer, HelpPageInfo> PAGE_TITLES = new HashMap<>();

    static {
        PAGE_TITLES.put(1, new HelpPageInfo("Stress", "Stress"));
        PAGE_TITLES.put(2, new HelpPageInfo("Abilities-Activation", "Abilities"));
        PAGE_TITLES.put(3, new HelpPageInfo("Abilities-List-Accelix,Avex,Glacen,Kinetic Detonation,Lumen,Novis and Noxen", "Abilities"));
        PAGE_TITLES.put(4, new HelpPageInfo("Abilities-List-Pondus,Regeneras,Sturma,Submari,Tactile Consciousness Transfer,Telekinesis,Teletras and Ximic", "Abilities"));
        PAGE_TITLES.put(5, new HelpPageInfo("Abilities-Usage-Accelix,Avex and Glacen", "Abilities"));
        PAGE_TITLES.put(6, new HelpPageInfo("Abilities-Usage-Kinetic Detonation and Lumen", "Abilities"));
        PAGE_TITLES.put(7, new HelpPageInfo("Abilities-Usage-Lumen,Novis,Noxen and Pondus", "Abilities"));
        PAGE_TITLES.put(8, new HelpPageInfo("Abilities-Usage-Pondus,Regeneras and Sturma", "Abilities"));
        PAGE_TITLES.put(9, new HelpPageInfo("Abilities-Usage-Sturma and Submari", "Abilities"));
        PAGE_TITLES.put(10, new HelpPageInfo("Abilities-Usage-Tactile Consciousness Transfer and Telekinesis", "Abilities"));
        PAGE_TITLES.put(11, new HelpPageInfo("Abilities-Usage-Telekinesis", "Abilities"));
        PAGE_TITLES.put(12, new HelpPageInfo("Abilities-Usage-Telekinesis,Teletras and Ximic", "Abilities"));
        PAGE_TITLES.put(13, new HelpPageInfo("Chimaeras-Creation", "Chimaeras"));
        PAGE_TITLES.put(14, new HelpPageInfo("Chimaeras-Commanding", "Chimaeras"));
        PAGE_TITLES.put(15, new HelpPageInfo("Loric Items-Function and Location-Green Stone, X Ray Stone and Diamond Dagger", "Loric Items"));
        PAGE_TITLES.put(16, new HelpPageInfo("Loric Items-Location-Silver Pipe, Leather Sleeve, Spiky Yellow Ball and Strand of Green Stones", "Loric Items"));
        PAGE_TITLES.put(17, new HelpPageInfo("Loric Items-Location-Red Bracelet", "Loric Items"));
        PAGE_TITLES.put(18, new HelpPageInfo("Inhibitors-Usage", "Inhibitors"));
        PAGE_TITLES.put(19, new HelpPageInfo("Inhibitors-Crafting", "Inhibitors"));
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("ll")
                    .then(CommandManager.literal("modhelp")
                            .executes(ctx -> {
                                sendHelpPage(ctx.getSource(), 1);
                                return 1;
                            })
                            .then(CommandManager.literal("index")
                                    .executes(ctx -> {
                                        sendHelpIndex(ctx.getSource());
                                        return 1;
                                    }))
                            .then(CommandManager.argument("page", IntegerArgumentType.integer(1, 19))
                                    .executes(ctx -> {
                                        int page = IntegerArgumentType.getInteger(ctx, "page");
                                        sendHelpPage(ctx.getSource(), page);
                                        return 1;
                                    }))
                    ));
        });
    }

    private static void sendHelpIndex(ServerCommandSource source) {
        source.sendFeedback(() -> Text.literal("=== Lorien Legacies Help Index ===")
                .styled(style -> style.withColor(Formatting.GOLD).withBold(true)), false);

        PAGE_TITLES.forEach((page, info) -> {
            source.sendFeedback(() -> Text.literal("Page " + page + ": " + info.indexTitle())
                    .styled(style -> style.withColor(Formatting.YELLOW)), false);
        });

        source.sendFeedback(() -> Text.literal("Use /ll modhelp <page> to view a specific page.")
                .styled(style -> style.withColor(Formatting.GRAY).withItalic(true)), false);
    }

    private static void sendHelpPage(ServerCommandSource source, int page) {
        HelpPageInfo info = PAGE_TITLES.get(page);
        String title = (info != null) ? info.pageHeading() : "Unknown";

        source.sendFeedback(() -> Text.literal("=== Lorien Legacies Help - " + title + " (Page " + page + ") ===")
                .styled(style -> style.withColor(Formatting.GOLD).withBold(true)), false);

        switch (page) {
            case 1 -> {
                source.sendFeedback(() -> Text.literal("Acquire 100 stress points by hitting hostiles or by being hit by hostiles for a 33% chance of gaining a random legacy.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("When low on health you get cyclically effected with 10 seconds of stamina and 5 seconds of tired. During tired, you cannot use your legacies.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("When activating Pondus Impenetrable Skin or Pondus Intangibility, you get cyclically effected with 10 seconds of Pondus stamina and 5 seconds of Pondus cooldown during which you cannot use Impenetrable Skin or Intangibility.").styled(s -> s.withColor(Formatting.RED)), false);
            }
            case 2 -> {
                source.sendFeedback(() -> Text.literal("Activate legacy abilities with a radial menu opened using the R key.").styled(s -> s.withColor(Formatting.BLUE)), false);
                source.sendFeedback(() -> Text.literal("Fireball, Iceball, and Lightning are activated via the radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Leftclick abilities auto-remove each other.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Telekinesis Push, Pull, Deflect, and Morph, Mark and Call Chimaera are activated with the radial menu and triggered with rightclick.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Rightclick abilities auto-remove each other.").styled(s -> s.withColor(Formatting.YELLOW)), false);
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
                source.sendFeedback(() -> Text.literal("Telekineis: Pull, Push, Move and Deflect.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Teletras: Teleportation.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Ximic: Acquire all powers.").styled(s -> s.withColor(Formatting.WHITE)), false);
            }
            case 5 -> {
                source.sendFeedback(() -> Text.literal("Accelix: Run fast. Auto activates when sprinting.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Avex: Hold/toggle shift and jump to activate.").styled(s -> s.withColor(Formatting.BLUE)), false);
                source.sendFeedback(() -> Text.literal("Glacen Iceball: Shoot iceball. Activated with radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Glacen Ice Hands: Freeze target on hit. Activated with radial menu.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Glacen Freeze Water: Freeze water you look at. Activated with radial menu.").styled(s -> s.withColor(Formatting.AQUA)), false);
            }
            case 6 -> {
                source.sendFeedback(() -> Text.literal("Kinetic Detonation: Mainhand item is converted into a throwable, explosive item. Activated with radial menu.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Lumen Fireball: Shoot fireball. Activated with radial menu and triggered with leftclick.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Lumen Human Fireball: Set entities and blocks around you on fire. Activated with radial menu").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Lumen Fire Hands : Set target on fire on hit. Activated with radial menu.").styled(s -> s.withColor(Formatting.YELLOW)), false);
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
                source.sendFeedback(() -> Text.literal("Sturma Weather Control: Switch between weather control modes in the order of Rain, Thunder and Clear. Depends on specific weather conditions. Activated with radial menu.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Submari: Passively breath underwater.").styled(s -> s.withColor(Formatting.BLUE)), false);
            }
            case 10 -> {
                source.sendFeedback(() -> Text.literal("Tactile Consciousness Transfer: Attach to looked at entity, become invisible, become blocked from using weapons and move mob around. Controlled hostile entities is hostile towards all other entities. Control players to hijack their legacies. Activated with radial menu (TCT option) and deactivated by selecting TCT option twice.").styled(s -> s.withColor(Formatting.AQUA)), false);
                source.sendFeedback(() -> Text.literal("Telekinesis Pull: Pull looked at entity towards you. Activated with radial menu and triggered with rightclick.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
            }
            case 11 -> {
                source.sendFeedback(() -> Text.literal("Telekinesis Push: Push looked at entity away from you. Activated with radial menu and triggered with rightclick.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Telekinesis Move: Move looked at entity around. Control with look direction. Activated with radial menu.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
            }
            case 12 -> {
                source.sendFeedback(() -> Text.literal("Telekinesis Deflect: Deflect projectiles and damage for 1 second after which you are effected with 5 seconds of Deflect cooldown, during which you can't deflect projectiles and damage. Activated with radial menu and triggered with rightclick.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Teletras: Teleport to looked at block. Activated with radial menu and triggered with rightclick.").styled(s -> s.withColor(Formatting.DARK_PURPLE)), false);
                source.sendFeedback(() -> Text.literal("Ximic: Acquire all legacies. Choose desired legacies in radial menu.").styled(s -> s.withColor(Formatting.WHITE)), false);
            }
            case 13 -> {
                source.sendFeedback(() -> Text.literal("When being bestowed upon a legacy, you receive Chimaera Essence. Splash a tamed wolf, parrot or horse or a axolotl to effect it with Chimaera Essence.").styled(s -> s.withColor(Formatting.DARK_GREEN)), false);
                source.sendFeedback(() -> Text.literal("Select Chimaera Command options in radial menu. Options include Morph, Mark and Call. Chimaera Commands are triggered with rightclick. Chimaera Commands only work on Chimaera Essence effected pets.").styled(s -> s.withColor(Formatting.DARK_GREEN)), false);
            }
            case 14 -> {
                source.sendFeedback(() -> Text.literal("Morph: Pet morphs into another pet when player looks at pet when pressing rightclick, wolf > parrot > axolotl > horse.").styled(s -> s.withColor(Formatting.DARK_GREEN)), false);
                source.sendFeedback(() -> Text.literal("Mark: When Chimaera is in wolf form, wolf attacks entity player looks at when pressing rightclick.").styled(s -> s.withColor(Formatting.DARK_GREEN)), false);
                source.sendFeedback(() -> Text.literal("Call: Chimaera teleports to you on rightclick.").styled(s -> s.withColor(Formatting.DARK_GREEN)), false);
            }
            case 15 -> {
                source.sendFeedback(() -> Text.literal("Powerful combat, utility and defense items.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Items are found as structure loot.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Usage instructions can be found in item tooltips.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Green Stone: Has a 75% chance to be found as Ocean Ruin loot.").styled(s -> s.withColor(Formatting.GREEN)), false);
                source.sendFeedback(() -> Text.literal("X Ray Stone: Has a 66% chance to be found as Shipwreck.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Diamond Dagger: Has a 50% chance to be found as Desert Temple loot.").styled(s -> s.withColor(Formatting.BLUE)), false);
            }
            case 16 -> {
                source.sendFeedback(() -> Text.literal("Leather Sleeve: Has a 25% chance to be found as Ancient City loot.").styled(s -> s.withColor(Formatting.GRAY)), false);
                source.sendFeedback(() -> Text.literal("Silver Pipe: Has a 10% chance to be found as Buried Treasure loot.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Spiky Yellow Ball: Has a 10% chance to be found as Igloo loot.").styled(s -> s.withColor(Formatting.YELLOW)), false);
                source.sendFeedback(() -> Text.literal("Strand of Green Stones: Has a 5% chance to be found as Jungle Temple loot.").styled(s -> s.withColor(Formatting.GREEN)), false);
            }
            case 17 -> {
                source.sendFeedback(() -> Text.literal("Red Bracelet: Has a 1% chance to be found as Woodland Mansion loot.").styled(s -> s.withColor(Formatting.RED)), false);
            }
            case 18 -> {
                source.sendFeedback(() -> Text.literal("Inhibits a player's Legacies.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Includes three items, Shock Collar, Inhibitor and Inhibitor Remote.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Shock Collar is single use and inhibits a player for 5 seconds.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Inhibitor is single use and effects a player with an infinite effect that enables inhibiting the player for 5 seconds as long as the infinite effect is active.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Infinite effect can only be removed by a Deinhibitor.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("More information can be found in item tooltips.").styled(s -> s.withColor(Formatting.RED)), false);
            }
            case 19 -> {
                source.sendFeedback(() -> Text.literal("Shock Collar crafting recipe: 3 diamonds in a v shape with a ender eye in the middle.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Inhibitor crafting recipe: 2 netherite ingots from middle top to middle bottom with a nether star in between.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Inhibitor Remote crafting recipe: 4 netherite ingots in a star shape with a nether star in the middle.").styled(s -> s.withColor(Formatting.RED)), false);
                source.sendFeedback(() -> Text.literal("Deinhibitor crafting recipe: 8 netherite ingots covering the entire crafting grid except for a nether star in the middle.").styled(s -> s.withColor(Formatting.RED)), false);
            }
            default -> {
                source.sendFeedback(() -> Text.literal("No help content for page " + page).styled(s -> s.withColor(Formatting.DARK_RED)), false);
            }
        }
    }
}
