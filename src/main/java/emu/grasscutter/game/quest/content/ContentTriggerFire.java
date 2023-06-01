package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_TRIGGER_FIRE;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

@QuestValueContent(QUEST_CONTENT_TRIGGER_FIRE)
public class ContentTriggerFire extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        if (quest.getTriggers().containsKey(quest.getTriggerNameById(params[0]))) {
            // We don't want to put a new key here
            return quest.getTriggers().get(quest.getTriggerNameById(params[0]));
        } else {
            Grasscutter.getLogger()
                    .debug("Quest {} doesn't have trigger {} registered.", quest.getSubQuestId(), params[0]);
            return false;
        }
    }
}
