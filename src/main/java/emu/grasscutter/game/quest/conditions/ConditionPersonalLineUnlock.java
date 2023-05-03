package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import emu.grasscutter.game.quest.enums.QuestCond;

@QuestValueCond(QuestCond.QUEST_COND_PERSONAL_LINE_UNLOCK)
public class ConditionPersonalLineUnlock extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        var personalLineId = condition.getParam()[0];
        return owner.getPersonalLineList().contains(personalLineId);
    }
}
