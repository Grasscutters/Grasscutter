package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.proto.BargainResultTypeOuterClass.BargainResultType;

@QuestValueContent(QuestContent.QUEST_CONTENT_ITEM_LESS_THAN_BARGAIN)
public final class ContentBargainLessThan extends BaseContent {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var bargain = quest.getOwner()
                .getPlayerProgress()
                .getBargains()
                .get(condition.getParam()[0]);
        if (bargain == null) return false;

        return bargain.getResult() == BargainResultType.BARGAIN_SINGLE_FAIL;
    }
}
