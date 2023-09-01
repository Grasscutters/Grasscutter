package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_COMPLETE_TALK;

@QuestValueCond(QUEST_COND_COMPLETE_TALK)
public class ConditionCompleteTalk extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val talkId = condition.getParam()[0];
        val checkMainQuest = owner.getQuestManager().getMainQuestByTalkId(talkId);
        if (checkMainQuest == null) {
            return talkId == params[0];
        }

        val talkData = checkMainQuest.getTalks().get(talkId);
        return talkData != null;
    }
}
