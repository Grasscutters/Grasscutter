package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_NOT_FINISH_PLOT;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;
import lombok.val;

@QuestValueContent(QUEST_CONTENT_NOT_FINISH_PLOT)
public class ContentNotFinishPlot extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val talkId = condition.getParam()[0];
        val checkMainQuest = quest.getOwner().getQuestManager().getMainQuestByTalkId(talkId);
        if (checkMainQuest == null) {
            return true;
        }
        val talkData = checkMainQuest.getTalks().get(talkId);
        if (talkId != params[0]) return false;
        return talkData == null;
    }
}
