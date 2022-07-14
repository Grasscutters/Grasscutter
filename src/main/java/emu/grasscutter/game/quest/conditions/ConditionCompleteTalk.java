package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_COMPLETE_TALK)
public class ConditionCompleteTalk extends QuestBaseHandler {

    @Override
    public boolean execute(GameMainQuest mainQuest, QuestData.QuestCondition condition, String paramStr, int... params) {
        MainQuestData.TalkData talkData = mainQuest.getTalks().get(Integer.valueOf(params[0]));
        if(talkData == null) {return false;}
        return true;
    }

}
