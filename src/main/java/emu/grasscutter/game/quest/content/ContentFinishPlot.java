package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_CONTENT_FINISH_PLOT)
public class ContentFinishPlot extends QuestBaseHandler {

	@Override
	public boolean execute(GameMainQuest mainQuest, QuestCondition condition, String paramStr, int... params) {
        MainQuestData.TalkData talkData = mainQuest.getTalks().get(Integer.valueOf(params[0]));
        GameQuest subQuest = mainQuest.getChildQuestById(params[0]);
        if(talkData == null && subQuest == null) {return false;}
        return true;
	}

}
