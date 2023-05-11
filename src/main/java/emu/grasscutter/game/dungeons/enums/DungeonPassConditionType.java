package emu.grasscutter.game.dungeons.enums;

import emu.grasscutter.scripts.constants.IntValueEnum;
import lombok.Getter;

public enum DungeonPassConditionType implements IntValueEnum {
    DUNGEON_COND_NONE(0),
    DUNGEON_COND_KILL_MONSTER(3),
    DUNGEON_COND_KILL_GROUP_MONSTER(5),
    DUNGEON_COND_KILL_TYPE_MONSTER(7),
    DUNGEON_COND_FINISH_QUEST(9),
    DUNGEON_COND_KILL_MONSTER_COUNT(11), // TODO handle count
    DUNGEON_COND_IN_TIME(13), // Missing triggers and tracking
    DUNGEON_COND_FINISH_CHALLENGE(14),
    DUNGEON_COND_END_MULTISTAGE_PLAY(15) // Missing
;

    @Getter private final int id;

    DungeonPassConditionType(int id) {
        this.id = id;
    }

    @Override
    public int getValue() {
        return id;
    }
}
