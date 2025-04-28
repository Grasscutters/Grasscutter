package emu.grasscutter.game.quest.enums;

import lombok.Getter;

@Getter
public enum QuestGuideType {
    QUEST_GUIDE_NONE(0),
    QUEST_GUIDE_LOCATION(1),
    QUEST_GUIDE_NPC(2);

    private final int value;

    QuestGuideType(int id) {
        this.value = id;
    }
}
