package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_QUEST_VAR_LESS;

import emu.grasscutter.game.quest.QuestValueCond;

@QuestValueCond(QUEST_COND_QUEST_VAR_LESS)
public class ConditionQuestVarLess extends BaseConditionQuestVar {

    @Override
    protected boolean doCompare(int variable, int cond) {
        return variable < cond;
    }
}
