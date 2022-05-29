package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
@QuestValue(QuestTrigger.QUEST_CONTENT_ADD_QUEST_PROGRESS)
public class ContentAddQuestProgress extends QuestBaseHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, int... params) {
        return condition.getParam()[0]==params[0];
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String... params) {
        return  String.valueOf(condition.getParam()[0]).equals(params[0]);
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExec condition, String... params) {
        return false;
    }
}
