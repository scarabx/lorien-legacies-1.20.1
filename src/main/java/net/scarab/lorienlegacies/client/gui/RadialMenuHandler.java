package net.scarab.lorienlegacies.client.gui;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.network.LorienLegaciesModNetworking;

import java.util.ArrayList;
import java.util.List;

public class RadialMenuHandler {
    public static boolean menuOpen = false;
    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static final List<String> pageNames = new ArrayList<>();
    private static final List<List<String>> optionsPages = new ArrayList<>();
    private static final List<List<Identifier>> packetPages = new ArrayList<>();
    private static final List<Integer> pageColors = new ArrayList<>();
    private static int currentPage = 0;

    static {
        // Page 1 - Combat Abilities
        pageNames.add("Combat");
        List<String> combatOptions = List.of(
                "Shoot Fireball",
                "Human Fireball",
                "Toggle Flaming Hands",
                "Shoot Iceball",
                "Icicles",
                "Toggle Ice Hands",
                "Fortem"
        );
        List<Identifier> combatPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_SHOOT_FIREBALL_PACKET,
                LorienLegaciesModNetworking.HUMAN_FIREBALL_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FLAMING_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_SHOOT_ICEBALL_PACKET,
                LorienLegaciesModNetworking.ICICLES_PACKET,
                LorienLegaciesModNetworking.TOGGLE_ICE_HANDS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FORTEM_PACKET
        );
        optionsPages.add(combatOptions);
        packetPages.add(combatPackets);
        pageColors.add(0xFF0000); // Red for combat abilities

        // Page 2 - Movement Abilities
        pageNames.add("Movement");
        List<String> movementOptions = List.of(
                "Accelix"
        );
        List<Identifier> movementPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_ACCELIX_PACKET
        );
        optionsPages.add(movementOptions);
        packetPages.add(movementPackets);
        pageColors.add(0xFFFF00); // Yellow for movement abilities

        // Page 3 - Utility Abilities
        pageNames.add("Utility");
        List<String> utilityOptions = List.of(
                "Freeze Water",
                "Noxen",
                "Novis",
                "Regeneras"
        );
        List<Identifier> utilityPackets = List.of(
                LorienLegaciesModNetworking.TOGGLE_FREEZE_WATER_PACKET,
                LorienLegaciesModNetworking.TOGGLE_NOXEN_PACKET,
                LorienLegaciesModNetworking.TOGGLE_NOVIS_PACKET,
                LorienLegaciesModNetworking.TOGGLE_REGENERAS_PACKET
        );
        optionsPages.add(utilityOptions);
        packetPages.add(utilityPackets);
        pageColors.add(0x808080); // Grey for utility abilities
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
        String pageName = pageNames.get(currentPage);
        int headingColor = pageColors.get(currentPage);

        // Adjust radius dynamically based on number of options
        int baseRadius = 60;
        int radius = baseRadius + Math.min(40, options.size() * 10);

        // Draw heading
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

            double angle = angleStep * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.hypot(mouseX - optionX, mouseY - optionY) < 20;
            int color = hovered ? 0xFF00FFFF : 0xFFFFFFFF;

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
        int radius = baseRadius + Math.min(40, options.size() * 10);

        double mouseX = client.mouse.getX() * screenWidth / client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * screenHeight / client.getWindow().getHeight();

        List<Identifier> packets = packetPages.get(currentPage);
        int optionCount = options.size();
        double angleStep = 2 * Math.PI / Math.max(optionCount, 1);

        for (int i = 0; i < optionCount; i++) {
            double angle = angleStep * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.hypot(mouseX - optionX, mouseY - optionY) < 20;

            if (hovered) {
                ClientPlayNetworking.send(packets.get(i), new PacketByteBuf(Unpooled.buffer()));
                closeMenu();
                MinecraftClient.getInstance().setScreen(null);
                break;
            }
        }
    }
}
