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
    private static int currentPage = 0;

    static {
        // Page 1 - Fire Abilities
        pageNames.add("Fire Abilities");
        List<String> fireOptions = List.of(
                "Shoot Fireball",
                "Human Fireball",
                "Toggle Flaming Hands"
        );
        List<Identifier> firePackets = List.of(
                LorienLegaciesModNetworking.SHOOT_FIREBALL_PACKET,
                LorienLegaciesModNetworking.HUMAN_FIREBALL_PACKET,
                LorienLegaciesModNetworking.TOGGLE_FLAMING_HANDS_PACKET
        );
        optionsPages.add(fireOptions);
        packetPages.add(firePackets);

        // Page 2 - Ice Abilities
        pageNames.add("Ice Abilities");
        List<String> iceOptions = List.of(
                "Shoot Iceball",
                "Icicles",
                "Toggle Ice Hands",
                "Freeze Water"
        );
        List<Identifier> icePackets = List.of(
                LorienLegaciesModNetworking.SHOOT_ICEBALL_PACKET,
                LorienLegaciesModNetworking.ICICLES_PACKET,
                LorienLegaciesModNetworking.TOGGLE_ICE_HANDS_PACKET,
                LorienLegaciesModNetworking.FREEZE_WATER_PACKET
        );
        optionsPages.add(iceOptions);
        packetPages.add(icePackets);
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
        int radius = 60;

        double mouseX = client.mouse.getX() * (double) screenWidth / (double) client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * (double) screenHeight / (double) client.getWindow().getHeight();

        List<String> options = optionsPages.get(currentPage);

        // Draw Page Heading
        String pageName = pageNames.get(currentPage);
        drawContext.drawCenteredTextWithShadow(
                client.textRenderer,
                Text.literal(pageName),
                centerX,
                centerY - 100,
                0xFFFFFFFF
        );

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);

            double angle = (2 * Math.PI / options.size()) * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.sqrt(Math.pow(mouseX - optionX, 2) + Math.pow(mouseY - optionY, 2)) < 20;
            int color = hovered ? 0xFF00FFFF : 0xFFFFFFFF; // Bright blue if hovered, otherwise white

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
        int radius = 60;

        double mouseX = client.mouse.getX() * (double) screenWidth / (double) client.getWindow().getWidth();
        double mouseY = client.mouse.getY() * (double) screenHeight / (double) client.getWindow().getHeight();

        List<String> options = optionsPages.get(currentPage);
        List<Identifier> packets = packetPages.get(currentPage);

        for (int i = 0; i < options.size(); i++) {
            double angle = (2 * Math.PI / options.size()) * i - Math.PI / 2;
            int optionX = centerX + (int) (radius * Math.cos(angle));
            int optionY = centerY + (int) (radius * Math.sin(angle));

            boolean hovered = Math.sqrt(Math.pow(mouseX - optionX, 2) + Math.pow(mouseY - optionY, 2)) < 20;

            if (hovered) {
                ClientPlayNetworking.send(packets.get(i), new PacketByteBuf(Unpooled.buffer()));
                closeMenu();
                MinecraftClient.getInstance().setScreen(null); // Close after selecting
                break;
            }
        }
    }
}
