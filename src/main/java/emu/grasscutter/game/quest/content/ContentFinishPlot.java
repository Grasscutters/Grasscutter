package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_FINISH_PLOT;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_FINISH_PLOT)
public class ContentFinishPlot extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var talkData = quest.getMainQuest().getTalks().get(params[0]);
        var subQuest = quest.getMainQuest().getChildQuestById(params[0]);
        return (talkData != null && subQuest != null || condition.getParamStr().equals(paramStr))
                && condition.getParam()[0] == params[0];
    }
}
