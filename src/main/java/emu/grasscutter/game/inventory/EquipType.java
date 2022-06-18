package emu.grasscutter.game.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum EquipType {
    EQUIP_NONE(0),
    EQUIP_BRACER(1),
    EQUIP_NECKLACE(2),
    EQUIP_SHOES(3),
    EQUIP_RING(4),
    EQUIP_DRESS(5),
    EQUIP_WEAPON(6);

    private final int value;
    private static final Int2ObjectMap<EquipType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, EquipType> stringMap = new HashMap<>();

    static {
        Stream.of(values()).forEach(e -> {
            map.put(e.getValue(), e);
            stringMap.put(e.name(), e);
        });
    }

    EquipType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EquipType getTypeByValue(int value) {
        return map.getOrDefault(value, EQUIP_NONE);
    }

    public static EquipType getTypeByName(String name) {
        return stringMap.getOrDefault(name, EQUIP_NONE);
    }
}
