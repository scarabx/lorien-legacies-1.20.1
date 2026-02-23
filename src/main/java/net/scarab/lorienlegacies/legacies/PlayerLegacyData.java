package net.scarab.lorienlegacies.legacies;

public class PlayerLegacyData {
    public LegacyManager.Legacy legacy;
    public boolean active;

    public PlayerLegacyData(LegacyManager.Legacy legacy) {
        this.legacy = legacy;
        this.active = false;
    }
}