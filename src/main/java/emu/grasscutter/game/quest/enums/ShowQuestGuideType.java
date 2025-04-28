package emu.grasscutter.game.quest.enums;

import lombok.Getter;

@Getter
public enum ShowQuestGuideType {
    QUEST_GUIDE_ITEM_ENABLE(0),
    QUEST_GUIDE_ITEM_DISABLE(1),
    QUEST_GUIDE_ITEM_MOVE_HIDE(2);

    private final int value;

    ShowQuestGuideType(int id) {
        this.value = id;
    }
}
