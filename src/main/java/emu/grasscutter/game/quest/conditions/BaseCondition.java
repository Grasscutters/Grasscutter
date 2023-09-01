package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_UNKNOWN;

@QuestValueCond(QUEST_COND_UNKNOWN)
public class BaseCondition {

    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        return false;
    }
}
