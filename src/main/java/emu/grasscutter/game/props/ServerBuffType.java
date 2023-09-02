package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.stream.Stream;

public enum ServerBuffType {
    SERVER_BUFF_NONE(0),
    SERVER_BUFF_AVATAR(1),
    SERVER_BUFF_TEAM(2),
    SERVER_BUFF_TOWER(3);

    private static final Int2ObjectMap<ServerBuffType> map = new Int2ObjectOpenHashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                        });
    }

    private final int value;

    ServerBuffType(int value) {
        this.value = value;
    }

    public static ServerBuffType getTypeByValue(int value) {
        return map.getOrDefault(value, SERVER_BUFF_NONE);
    }

    public int getValue() {
        return value;
    }
}
