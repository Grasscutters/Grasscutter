package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_INTERACT_GADGET;

@QuestValueContent(QUEST_CONTENT_INTERACT_GADGET)
public class ContentInteractGadget extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return params[0] == condition.getParam()[0];
    }
}
