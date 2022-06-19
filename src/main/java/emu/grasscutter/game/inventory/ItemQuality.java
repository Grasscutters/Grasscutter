package emu.grasscutter.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum ItemQuality {
    QUALITY_NONE(0),
    QUALITY_WHITE(1),
    QUALITY_GREEN(2),
    QUALITY_BLUE(3),
    QUALITY_PURPLE(4),
    QUALITY_ORANGE(5),
    QUALITY_ORANGE_SP(105);

    private final int value;
    private static final Int2ObjectMap<ItemQuality> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ItemQuality> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    ItemQuality(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ItemQuality getTypeByValue(int value) {
        return map.getOrDefault(value, QUALITY_NONE);
    }

    public static ItemQuality getTypeByName(String name) {
        return stringMap.getOrDefault(name, QUALITY_NONE);
    }
}
