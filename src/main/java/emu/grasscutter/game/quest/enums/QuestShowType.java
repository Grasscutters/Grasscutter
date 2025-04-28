package emu.grasscutter.game.quest.enums;

import lombok.Getter;

@Getter
public enum QuestShowType {
    QUEST_SHOW(0),
    QUEST_HIDDEN(1);

    private final int value;

    QuestShowType(int id) {
        this.value = id;
    }
}
