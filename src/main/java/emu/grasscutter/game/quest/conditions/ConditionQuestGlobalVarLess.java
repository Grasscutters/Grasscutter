package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_QUEST_GLOBAL_VAR_LESS)
public class ConditionQuestGlobalVarLess extends QuestBaseHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String paramStr, int... params) {
        Integer questGlobalVarValue = quest.getMainQuest().getQuestManager().getQuestGlobalVarValue(Integer.valueOf(params[0]));
        Grasscutter.getLogger().debug("questGlobarVar {} : {}", params[0],questGlobalVarValue);
        return questGlobalVarValue.intValue() < params[1];
    }
}