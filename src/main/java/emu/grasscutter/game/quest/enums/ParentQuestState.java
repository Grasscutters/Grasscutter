package emu.grasscutter.game.quest.enums;

import lombok.Getter;

@Getter
public enum ParentQuestState {
    PARENT_QUEST_STATE_NONE(0),
    PARENT_QUEST_STATE_FINISHED(1),
    PARENT_QUEST_STATE_FAILED(2),
    PARENT_QUEST_STATE_CANCELED(3);

    private final int value;

    ParentQuestState(int id) {
        this.value = id;
    }
}
