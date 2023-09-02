package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_FINISH_DUNGEON;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_FINISH_DUNGEON)
public class ContentFinishDungeon extends BaseContent {
    // params[0] dungeon ID, params[1] unknown

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var dungeonId = condition.getParam()[0];
        return quest.getOwner().getPlayerProgress().getCompletedDungeons().contains(dungeonId);
    }
}
