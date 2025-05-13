package net.scarab.lorienlegacies.legacy;

import java.util.HashMap;
import java.util.Map;

public class LegacyRegistry {

    private static final Map<String, Legacy> LEGACY_MAP = new HashMap<>();

    public static Legacy register(String name, Legacy legacy) {
        LEGACY_MAP.put(name, legacy);
        return legacy;
    }

    public static Legacy get(String name) {
        return LEGACY_MAP.get(name);
    }

    public static Map<String, Legacy> getAll() {
        return LEGACY_MAP;
    }
}
