package emu.grasscutter.game.props;

import emu.grasscutter.scripts.constants.IntValueEnum;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum CampTargetType implements IntValueEnum {
    None(0),
    Alliance(1),
    Enemy(2),
    Self(3),
    SelfCamp(4),
    All(5),
    AllExceptSelf(6),
    AllianceIncludeSelf(7);

    private final int value;
    private static final Int2ObjectMap<CampTargetType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, CampTargetType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private CampTargetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CampTargetType getTypeByValue(int value) {
        return map.getOrDefault(value, None);
    }

    public static CampTargetType getTypeByName(String name) {
        return stringMap.getOrDefault(name, None);
    }
}
