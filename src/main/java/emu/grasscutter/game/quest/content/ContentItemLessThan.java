package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_ITEM_LESS_THAN;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import lombok.val;

@QuestValueContent(QUEST_CONTENT_ITEM_LESS_THAN)
public class ContentItemLessThan extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val itemId = condition.getParam()[0];
        val targetAmount = condition.getParam()[1];
        val amount = quest.getOwner().getInventory().getItemCountById(itemId);
        return amount < targetAmount;
    }
}
