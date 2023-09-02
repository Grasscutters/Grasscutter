package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_TRIGGER_FIRE;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import lombok.val;

@QuestValueContent(QUEST_CONTENT_TRIGGER_FIRE)
public class ContentTriggerFire extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val triggerId = condition.getParam()[0];
        val triggerName = quest.getTriggerNameById(triggerId);
        return quest.getTriggers().getOrDefault(triggerName, false);
    }
}
