package net.scarab.lorienlegacies.legacy_toggle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class TelekinesisToggles {

    private boolean pushToggle;
    private boolean pullToggle;
    private boolean moveToggle;

    // Constructor to initialize toggle states (default to false)
    public TelekinesisToggles(boolean pushToggle, boolean pullToggle, boolean moveToggle) {
        this.pushToggle = pushToggle;
        this.pullToggle = pullToggle;
        this.moveToggle = moveToggle;
    }

    // Getter for the push toggle
    public boolean isPushToggle() {
        return pushToggle;
    }

    // Setter for the push toggle
    public void setPushToggle(boolean pushToggle) {
        this.pushToggle = pushToggle;
    }

    // Getter for the pull toggle
    public boolean isPullToggle() {
        return pullToggle;
    }

    // Setter for the pull toggle
    public void setPullToggle(boolean pullToggle) {
        this.pullToggle = pullToggle;
    }

    // Getter for the move toggle
    public boolean isMoveToggle() {
        return moveToggle;
    }

    // Setter for the move toggle
    public void setMoveToggle(boolean moveToggle) {
        this.moveToggle = moveToggle;
    }

    // Method to toggle a specific telekinesis ability on or off
    public static void toggleAbility(World world, PlayerEntity player, TelekinesisToggles toggles, String ability) {
        if (!world.isClient) {  // Only run on the server

            boolean newState = false;  // Default state to false

            // Determine which ability is being toggled
            switch (ability.toLowerCase()) {
                case "push":
                    newState = !toggles.isPushToggle();
                    toggles.setPushToggle(newState);
                    break;
                case "pull":
                    newState = !toggles.isPullToggle();
                    toggles.setPullToggle(newState);
                    break;
                case "move":
                    newState = !toggles.isMoveToggle();
                    toggles.setMoveToggle(newState);
                    break;
                default:
                    player.sendMessage(Text.of("Invalid ability. Choose 'push', 'pull', or 'move'."), false);
                    return;
            }

            // Send a message to the player confirming the toggle change
            String message = newState ? ability + " ability enabled!" : ability + " ability disabled.";
            player.sendMessage(Text.of(message), false);
        }
    }
}