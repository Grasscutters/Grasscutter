package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_COMPLETE_TALK)
public class ConditionCompleteTalk extends QuestBaseHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String paramStr, int... params) {
        GameMainQuest checkMainQuest = quest.getOwner().getQuestManager().getMainQuestById(condition.getParam()[0]/100);
        if (checkMainQuest == null || GameData.getMainQuestDataMap().get(checkMainQuest.getParentQuestId()).getTalks() == null) {
            Grasscutter.getLogger().debug("Warning: mainQuest {} hasn't been started yet, or has no talks", condition.getParam()[0]/100);
            return false;
        }
        MainQuestData.TalkData talkData = checkMainQuest.getTalks().get(Integer.valueOf(params[0]));
        return talkData != null || checkMainQuest.getChildQuestById(params[0]) != null;
    }

}
