package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_QUEST_VAR_GREATER;

import emu.grasscutter.game.quest.QuestValueCond;

@QuestValueCond(QUEST_COND_QUEST_VAR_GREATER)
public class ConditionQuestVarGreater extends BaseConditionQuestVar {

    @Override
    protected boolean doCompare(int variable, int cond) {
        return variable > cond;
    }
}
