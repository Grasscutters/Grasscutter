package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_COMPLETE_ANY_TALK;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import java.util.Arrays;
import lombok.val;

@QuestValueContent(QUEST_CONTENT_COMPLETE_ANY_TALK)
public class ContentCompleteAnyTalk extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var conditionTalk =
                Arrays.stream(condition.getParamStr().split(",")).mapToInt(Integer::parseInt).toArray();

        for (var talkId : conditionTalk) {
            val checkMainQuest = quest.getOwner().getQuestManager().getMainQuestByTalkId(talkId);
            if (checkMainQuest == null) {
                if (talkId == params[0]) return true;
                continue;
            }

            val talkData = checkMainQuest.getTalks().get(talkId);
            if (talkData != null) {
                return true;
            }
        }
        return false;
    }
}
