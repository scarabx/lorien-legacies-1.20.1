package net.scarab.lorienlegacies.client.gui;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;

import java.util.*;

public class RadialMenuHandler {
    public static boolean menuOpen = false;
    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static final List<String> pageNames = new ArrayList<>();
    private static final List<List<String>> optionsPages = new ArrayList<>();
    private static final List<List<Identifier>> packetPages = new ArrayList<>();
    private static final List<Integer> pageColors = new ArrayList<>();
    private static int currentPage = 0;

    // Store toggled state
    //private static final Set<Identifier> toggledOptions = new HashSet<>();

    static {
        // Page 1 - Combat Abilities
        pageNames.add("Combat");
        List<String> combatOptions = List.of(
                "Fireball",
                "Flaming Hands",
                "Kinetic Detonation",
                "Push",
                "Pull",
                "Move",
                "Deflect",
                "Lightning",
                "Icicles",
                "Ice Hands",
                "Iceball",
                "Human Fireball"
        );
        List<Identifier> combatPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_SHOOT_FIREBALL_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FLAMING_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_KINETIC_DETONATION,
                LorienLegaciesModNetworking.TELEKINESIS_PUSH_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_PULL_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_MOVE_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_DEFLECT_PACKET,
                LorienLegaciesModNetworking.TOGGLE_LIGHTNING_STRIKE_PACKET,
                LorienLegaciesModNetworking.ICICLES_PACKET,
                LorienLegaciesModNetworking.TOGGLE_ICE_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_SHOOT_ICEBALL_PACKET,
                LorienLegaciesModNetworking.HUMAN_FIREBALL_PACKET
        );
        optionsPages.add(combatOptions);
        packetPages.add(combatPackets);
        pageColors.add(0xFF0000); // Red

        // Page 2 - Movement Abilities
        pageNames.add("Movement");
        List<String> movementOptions = List.of(
                "Intangifly",
                "Teleport"
        );
        List<Identifier> movementPackets = List.of(
                LorienLegaciesModNetworking.INTANGIFLY_PACKET,
                LorienLegaciesModNetworking.TOGGLE_TELETRAS_PACKET
        );
        optionsPages.add(movementOptions);
        packetPages.add(movementPackets);
        pageColors.add(0xFFFF00); // Yellow

        // Page 3 - Defense Abilities
        pageNames.add("Defense");
        List<String> defenseOptions = List.of(
                "Impenetrable Skin",
                "Intangibility"
        );
        List<Identifier> defensePackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_IMPENETRABLE_SKIN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_INTANGIBILITY_PACKET
        );
        optionsPages.add(defenseOptions);
        packetPages.add(defensePackets);
        pageColors.add(0xFF4682B4); // Steel Blue

        // Page 4 - Utility Abilities
        pageNames.add("Utility");
        List<String> utilityOptions = List.of(
                "Freeze Water",
                "Rain",
                "Thunder",
                "Clear",
                "Novis",
                "TCT"
        );
        List<Identifier> utilityPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_FREEZE_WATER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_CONJURE_RAIN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_CONJURE_THUNDER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_CONJURE_CLEAR_WEATHER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_NOVIS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_TACTILE_CONSCIOUSNESS_TRANSFER_PACKET
        );
        optionsPages.add(utilityOptions);
        packetPages.add(utilityPackets);
        pageColors.add(0x808080); // Gray

        // Page 5 - Chimaera Control
        pageNames.add("Chimaera");
        List<String> chimaeraOptions = List.of(
                "Morph",
                "Mark",
                "Call"
        );
        List<Identifier> chimaeraPackets = List.of(
                LorienLegaciesModNetworking.CHIMAERA_MORPH_PACKET,
                LorienLegaciesModNetworking.MARK_TARGET_FOR_WOLF_PACKET,
                LorienLegaciesModNetworking.CHIMAERA_CALL_PACKET
        );
        optionsPages.add(chimaeraOptions);
        packetPages.add(chimaeraPackets);
        pageColors.add(0x00FF00); // Green

        // Page 6 - Ximic
        pageNames.add("Ximic");
        List<String> ximicOptions = List.of(
                "Accelix",
                "Avex",
                "Glacen",
                "Lumen",
                "Novis",
                "Noxen",
                "Pondus",
                "Regeneras",
                "Sturma",
                "Tactile Consciousness Transfer",
                "Telekinesis",
                "Kinetic Detonation",
                "Teletras",
                "Submari"
        );
        List<Identifier> ximicPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_XIMIC_ACCELIX_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_AVEX_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_GLACEN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_LUMEN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_NOVIS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_NOXEN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_PONDUS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_REGENERAS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_STURMA_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_TACTILE_CONSCIOUSNESS_TRANSFER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_TELEKINESIS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_KINETIC_DETONATION_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_TELETRAS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_XIMIC_SUBMARI_PACKET
        );
        optionsPages.add(ximicOptions);
        packetPages.add(ximicPackets);
        pageColors.add(0xFFFFFF); // White
    }

    public static void closeMenu() {
        menuOpen = false;
    }

    public static void nextPage() {
        currentPage = (currentPage + 1) % pageNames.size();
    }

    public static void render(DrawContext drawContext, float tickDelta) {
        if (!menuOpen || client.player == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        double mouseX = client.mouse.getX() * screenWidth / client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * screenHeight / client.getWindow().getHeight();

        List<String> options = optionsPages.get(currentPage);
        List<Identifier> packets = packetPages.get(currentPage);
        String pageName = pageNames.get(currentPage);
        int headingColor = pageColors.get(currentPage);

        int baseRadius = 60;
        int radius = baseRadius + Math.min(40, options.size() * 10);

        drawContext.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal(pageName),
                centerX,
                centerY - radius - 30,
                headingColor
        );

        int optionCount = options.size();
        double angleStep = 2 * Math.PI / Math.max(optionCount, 1);

        for (int i = 0; i < optionCount; i++) {
            String option = options.get(i);
            Identifier packetId = packets.get(i);

            double angle = angleStep * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.hypot(mouseX - optionX, mouseY - optionY) < 20;

            // Only hovered options are highlighted blue; others are white
            int color = hovered ? 0xFF0000FF : 0xFFFFFFFF;

            drawContext.drawCenteredTextWithShadow(
                    client.textRenderer,
                    Text.literal(option),
                    optionX,
                    optionY,
                    color
            );
        }
    }

    public static void selectOption() {
        if (!menuOpen || client.player == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        int baseRadius = 60;
        List<String> options = optionsPages.get(currentPage);
        List<Identifier> packets = packetPages.get(currentPage);
        int radius = baseRadius + Math.min(40, options.size() * 10);

        double mouseX = client.mouse.getX() * screenWidth / client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * screenHeight / client.getWindow().getHeight();

        int optionCount = options.size();
        double angleStep = 2 * Math.PI / Math.max(optionCount, 1);

        for (int i = 0; i < optionCount; i++) {
            double angle = angleStep * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.hypot(mouseX - optionX, mouseY - optionY) < 20;

            if (hovered) {
                Identifier packetId = packets.get(i);

                ClientPlayNetworking.send(packetId, new PacketByteBuf(Unpooled.buffer()));
                closeMenu();
                MinecraftClient.getInstance().setScreen(null);
                break;
            }
        }
    }
}
