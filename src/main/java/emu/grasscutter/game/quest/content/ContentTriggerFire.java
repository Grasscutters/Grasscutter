package emu.grasscutter.game.quest.content;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.data.excels.TriggerExcelConfigData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_CONTENT_TRIGGER_FIRE)
public class ContentTriggerFire extends QuestBaseHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String paramStr, int... params) {
        if(quest.getTriggers().containsKey(Integer.valueOf(params[0]))) {
            return quest.getTriggers().getOrDefault(Integer.valueOf(params[0]), false);
        } else {
            Grasscutter.getLogger().error("quest {} doesn't have trigger {}", quest.getSubQuestId(), params[0]);
            return false;
        }
    }
}
