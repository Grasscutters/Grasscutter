package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import lombok.val;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_CLEAR_GROUP_MONSTER;

@QuestValueContent(QUEST_CONTENT_CLEAR_GROUP_MONSTER)
public class ContentClearGroupMonster extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val groupId = condition.getParam()[0];

        return quest.getOwner().getScene().getScriptManager().isClearedGroupMonsters(groupId);
    }
}
