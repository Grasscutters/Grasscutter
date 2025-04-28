package emu.grasscutter.game.dungeons.enums;

import lombok.Getter;

@Getter
public enum SettleShowType {
    SETTLE_SHOW_NONE(0),
    SETTLE_SHOW_TIME_COST(1),
    SETTLE_SHOW_OPEN_CHEST_COUNT(2),
    SETTLE_SHOW_KILL_MONSTER_COUNT(3),
    SETTLE_SHOW_BLACKSCREEN(4);

    private final int id;

    SettleShowType(int id) {
        this.id = id;
    }
}
