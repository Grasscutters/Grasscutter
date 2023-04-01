package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_ANY_MANUAL_TRANSPORT;

@QuestValueContent(QUEST_CONTENT_ANY_MANUAL_TRANSPORT)
public class ContentAnyManualTransport extends BaseContent {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return true;
    }
}
