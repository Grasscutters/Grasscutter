package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum LifeState {
    LIFE_NONE(0),
    LIFE_ALIVE(1),
    LIFE_DEAD(2),
    LIFE_REVIVE(3);

    private static final Int2ObjectMap<LifeState> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, LifeState> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private final int value;

    LifeState(int value) {
        this.value = value;
    }

    public static LifeState getTypeByValue(int value) {
        return map.getOrDefault(value, LIFE_NONE);
    }

    public static LifeState getTypeByName(String name) {
        return stringMap.getOrDefault(name, LIFE_NONE);
    }

    public int getValue() {
        return value;
    }
}
