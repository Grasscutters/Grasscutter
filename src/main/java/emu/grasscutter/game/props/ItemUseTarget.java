package emu.grasscutter.game.props;

import it.unimi.dsi.fastutil.ints.*;
import java.util.stream.Stream;

public enum ItemUseTarget {
    ITEM_USE_TARGET_NONE(0),
    ITEM_USE_TARGET_CUR_AVATAR(1),
    ITEM_USE_TARGET_CUR_TEAM(2),
    ITEM_USE_TARGET_SPECIFY_AVATAR(3),
    ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR(4),
    ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR(5);

    private static final Int2ObjectMap<ItemUseTarget> map = new Int2ObjectOpenHashMap<>();

    static {
        Stream.of(values())
                .forEach(
                        e -> {
                            map.put(e.getValue(), e);
                        });
    }

    private final int value;

    ItemUseTarget(int value) {
        this.value = value;
    }

    public static ItemUseTarget getTypeByValue(int value) {
        return map.getOrDefault(value, ITEM_USE_TARGET_NONE);
    }

    public int getValue() {
        return value;
    }
}
