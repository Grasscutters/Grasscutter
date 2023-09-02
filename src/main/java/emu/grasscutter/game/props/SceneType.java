package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.Stream;

public enum SceneType {
    SCENE_NONE(0),
    SCENE_WORLD(1),
    SCENE_DUNGEON(2),
    SCENE_ROOM(3),
    SCENE_HOME_WORLD(4),
    SCENE_HOME_ROOM(5),
    SCENE_ACTIVITY(6);

    private static final Int2ObjectMap<SceneType> map = new Int2ObjectOpenHashMap<>();
    private static final Map<String, SceneType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.put(e.name(), e);
                        });
    }

    private final int value;

    SceneType(int value) {
        this.value = value;
    }

    public static SceneType getTypeByValue(int value) {
        return map.getOrDefault(value, SCENE_NONE);
    }

    public static SceneType getTypeByName(String name) {
        return stringMap.getOrDefault(name, SCENE_NONE);
    }

    public int getValue() {
        return value;
    }
}
