package emu.grasscutter.game.quest.enums;

import emu.grasscutter.scripts.constants.IntValueEnum;

public enum QuestState implements IntValueEnum {
    QUEST_STATE_NONE(0),
    QUEST_STATE_UNSTARTED(1),
    QUEST_STATE_UNFINISHED(2),
    QUEST_STATE_FINISHED(3),
    QUEST_STATE_FAILED(4),

    // Used by lua
    NONE(0),
    UNSTARTED(1),
    UNFINISHED(2),
    FINISHED(3),
    FAILED(4);

    private final int value;

    QuestState(int id) {
        this.value = id;
    }

    public int getValue() {
        return value;
    }
}
