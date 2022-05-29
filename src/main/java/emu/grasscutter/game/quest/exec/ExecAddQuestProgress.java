package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
@QuestValue(QuestTrigger.QUEST_EXEC_ADD_QUEST_PROGRESS)
public class ExecAddQuestProgress extends QuestBaseHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, int... params) {
        return false;
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String... params) {
        return false;
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExec condition, String... params) {
        quest.getOwner().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_ADD_QUEST_PROGRESS,params);
        return false;
    }
}
