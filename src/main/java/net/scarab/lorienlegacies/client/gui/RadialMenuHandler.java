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
    private static final Set<Identifier> toggledOptions = new HashSet<>();

    static {
        // Page 1 - Combat Abilities
        pageNames.add("Combat");
        List<String> combatOptions = List.of(
                "Shoot Fireball",
                "Push",
                "Pull",
                "Move",
                "Human Fireball",
                "Toggle Flaming Hands",
                "Shoot Iceball",
                "Icicles",
                "Toggle Ice Hands",
                "Fortem"
        );
        List<Identifier> combatPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_SHOOT_FIREBALL_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_PUSH_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_PULL_PACKET,
                LorienLegaciesModNetworking.TELEKINESIS_MOVE_PACKET,
                LorienLegaciesModNetworking.HUMAN_FIREBALL_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FLAMING_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_SHOOT_ICEBALL_PACKET,
                LorienLegaciesModNetworking.ICICLES_PACKET,
                LorienLegaciesModNetworking.TOGGLE_ICE_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FORTEM_PACKET
        );
        optionsPages.add(combatOptions);
        packetPages.add(combatPackets);
        pageColors.add(0xFF0000); // Red

        // Page 1 - Movement Abilities
        pageNames.add("Movement");
        List<String> movementOptions = List.of(
                "Intangifly"
        );
        List<Identifier> movementPackets = List.of(
                LorienLegaciesModNetworking.INTANGIFLY_PACKET
        );
        optionsPages.add(movementOptions);
        packetPages.add(movementPackets);
        pageColors.add(0xFF0000); // Red

        // Page 2 - Defense Abilities
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

        // Page 3 - Utility Abilities
        pageNames.add("Utility");
        List<String> utilityOptions = List.of(
                "Freeze Water",
                "Novis"
        );
        List<Identifier> utilityPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_FREEZE_WATER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_NOVIS_PACKET
        );
        optionsPages.add(utilityOptions);
        packetPages.add(utilityPackets);
        pageColors.add(0x808080); // Gray

        // Page 4 - Chimaera Control
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
            boolean toggled = toggledOptions.contains(packetId);

            // Both hovered and selected options are regular blue
            int color = (hovered || toggled) ? 0xFF0000FF : 0xFFFFFFFF;

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

                // Toggle selection state
                if (toggledOptions.contains(packetId)) {
                    toggledOptions.remove(packetId);
                } else {
                    toggledOptions.add(packetId);
                }

                ClientPlayNetworking.send(packetId, new PacketByteBuf(Unpooled.buffer()));
                closeMenu();
                MinecraftClient.getInstance().setScreen(null);
                break;
            }
        }
    }
}
