package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_PACK_HAVE_ITEM;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

@QuestValueCond(QUEST_COND_PACK_HAVE_ITEM)
public class ConditionPackHaveItem extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val itemId = condition.getParam()[0];
        val targetAmount = condition.getParam()[1];
        val checkItem = owner.getInventory().getItemByGuid(itemId);
        return checkItem != null && checkItem.getCount() >= targetAmount;
    }
}
