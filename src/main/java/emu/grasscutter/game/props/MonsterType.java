package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum MonsterType {
    MONSTER_NONE(0),
    MONSTER_ORDINARY(1),
    MONSTER_BOSS(2),
    MONSTER_ENV_ANIMAL(3),
    MONSTER_LITTLE_MONSTER(4),
    MONSTER_FISH(5);

    private static final Int2ObjectMap<MonsterType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, MonsterType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private final int value;

    MonsterType(int value) {
        this.value = value;
    }

    public static MonsterType getTypeByValue(int value) {
        return map.getOrDefault(value, MONSTER_NONE);
    }

    public static MonsterType getTypeByName(String name) {
        return stringMap.getOrDefault(name, MONSTER_NONE);
    }

    public int getValue() {
        return value;
    }
}
