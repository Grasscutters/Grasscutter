package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_FAIL_DUNGEON;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_FAIL_DUNGEON)
public class ContentFailDungeon extends BaseContent {

    // params[0] dungeon ID
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return condition.getParam()[0] == params[0];
    }
}
