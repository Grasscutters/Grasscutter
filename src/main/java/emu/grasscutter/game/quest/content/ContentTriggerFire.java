package emu.grasscutter.game.quest.content;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
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
        if(quest.getTriggers().containsKey(quest.getTriggerNameById(params[0]))) {
            //We don't want to put a new key here
            return quest.getTriggers().get(quest.getTriggerNameById(params[0]));
        } else {
            Grasscutter.getLogger().error("quest {} doesn't have trigger {}", quest.getSubQuestId(), params[0]);
            return false;
        }
    }
}
