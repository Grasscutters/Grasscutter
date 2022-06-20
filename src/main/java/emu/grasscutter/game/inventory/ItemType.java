package emu.grasscutter.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum ItemType {
    ITEM_NONE(0),
    ITEM_VIRTUAL(1),
    ITEM_MATERIAL(2),
    ITEM_RELIQUARY(3),
    ITEM_WEAPON(4),
    ITEM_DISPLAY(5),
    ITEM_FURNITURE(6);

    private final int value;
    private static final Int2ObjectMap<ItemType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, ItemType> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    ItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ItemType getTypeByValue(int value) {
        return map.getOrDefault(value, ITEM_NONE);
    }

    public static ItemType getTypeByName(String name) {
        return stringMap.getOrDefault(name, ITEM_NONE);
    }
}
