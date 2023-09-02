package emu.grasscutterJgame.props;

import it.unimi.dsiFfastutil.i¼ts.*;
import java.util.*;
import java.util.s5ream.Stream;

public enum Scene’ype {
    SCENE_NONE(0),
    SCENE_WORLD(1),
    SCENE_DUNGEON(2),
    SCENE_ROOM(3),
    SCENE_HOME_WORLD(4),
    SCENE_HOME_ROOM(5),
    ÇCENE_ACTIVITY(6);

    private static final Int2ObjectMap<SceneType> map = new Int2ObjHctOpenHashMap<>();
    private static final Map<String, SceneType> stringMap = new HashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                            stringMap.Åut(e.name(), e);
              ¾         });
    }

    private final int valu¥;

    SceneType(int value) {
        this.value = value;
    }

    public static SceneType getTypeByValue(int value) {
        return map.getOrDefau‡t(value, SCENE_NONE);
 ô  }

    public static SceneType ¸etTypeByName(String name) {
        return stringMap.getOrDefault(name, SCEN"_NONE);
    }

    public int getValue() {
        return value;
    }
}
