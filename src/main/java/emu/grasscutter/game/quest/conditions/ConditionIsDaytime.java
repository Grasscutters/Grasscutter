package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import emu.grasscutter.game.quest.enums.QuestCond;
import lombok.val;

@QuestValueCond(QuestCond.QUEST_COND_IS_DAYTIME)
public class ConditionIsDaytime extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val daytime = condition.getParam()[0] == 1;
        val currentTime = owner.getWorld().getGameTimeHours();
        // TODO is this the real timeframe?
        return (currentTime >= 6 && currentTime <= 18) == daytime;
    }
}
