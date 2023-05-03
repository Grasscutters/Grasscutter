package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import lombok.val;

public abstract class BaseConditionQuestVar extends BaseCondition {

    protected abstract boolean doCompare(int variable, int cond);

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val index = condition.getParam()[0];
        val targetValue = condition.getParam()[1];
        val questVarValue = getQuestVar(owner, questData, index);

        Grasscutter.getLogger().debug("questVar {} : {}", index, questVarValue);

        if (questVarValue < 0) {
            return false;
        }
        return doCompare(questVarValue, targetValue);
    }

    protected int getQuestVar(Player owner, QuestData questData, int index) {
        val mainQuest = owner.getQuestManager().getMainQuestById(questData.getMainId());
        if (mainQuest == null) {
            Grasscutter.getLogger().debug("mainQuest for quest var not available yet");
            return -1;
        }
        val questVars = mainQuest.getQuestVars();
        if (index >= questVars.length) {
            Grasscutter.getLogger()
                    .error(
                            "questVar out of bounds for {} index {} size {}",
                            questData.getSubId(),
                            index,
                            questVars.length);
            return -2;
        }
        return questVars[index];
    }
}
