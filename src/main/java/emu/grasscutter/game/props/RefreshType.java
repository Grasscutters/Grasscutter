package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum RefreshType {
    REFRESH_NONE(0),
    REFRESH_INTERVAL(1),
    REFRESH_DAILY(2),
    REFRESH_WEEKlY(3),
    REFRESH_DAYBEGIN_INTERVAL(4);

    private final int value;
    private static final Int2ObjectMap<RefreshType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, RefreshType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private RefreshType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RefreshType getTypeByValue(int value) {
        return map.getOrDefault(value, REFRESH_NONE);
    }

    public static RefreshType getTypeByName(String name) {
        return stringMap.getOrDefault(name, REFRESH_NONE);
    }
}
