package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_CONTENT_COMPLETE_ANY_TALK)
public class ContentCompleteAnyTalk extends QuestBaseHandler {

    @Override
    public boolean execute(GameMainQuest mainQuest, QuestData.QuestCondition condition, String paramStr, int... params) {
        MainQuestData.TalkData talkData = mainQuest.getTalks().get(Integer.valueOf(paramStr));
        if(talkData == null) {return false;}
        if (condition.getParamStr().contains(paramStr)) {return true;}
        return false;
    }

}