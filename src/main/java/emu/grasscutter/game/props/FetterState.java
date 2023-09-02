package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum FetterState {
    NONE(0),
    NOT_OPEN(1),
    OPEN(1),
    FINISH(3);

    private static final Int2ObjectMap<FetterState> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, FetterState> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private final int value;

    FetterState(int value) {
        this.value = value;
    }

    public static FetterState getTypeByValue(int value) {
        return map.getOrDefault(value, NONE);
    }

    public static FetterState getTypeByName(String name) {
        return stringMap.getOrDefault(name, NONE);
    }

    public int getValue() {
        return value;
    }
}
