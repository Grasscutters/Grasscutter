package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;

@QuestValue(QuestTrigger.QUEST_CONTENT_SKILL)
public class ContentSkill extends BaseContent {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String paramStr, int... params) {
        return (condition.getParam()[0] == params[0]) && (condition.getParam()[1] == params[1]);
    }
}
