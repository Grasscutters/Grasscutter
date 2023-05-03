package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import emu.grasscutter.game.quest.enums.QuestCond;
import lombok.val;

@QuestValueCond(QuestCond.QUEST_COND_HISTORY_GOT_ANY_ITEM)
public class ConditionHistoryGotAnyItem extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val itemId = condition.getParam()[0];
        return owner.getPlayerProgress().hasPlayerObtainedItemHistorically(itemId);
    }
}
