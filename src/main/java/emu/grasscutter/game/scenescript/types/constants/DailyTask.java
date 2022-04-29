package emu.grasscutter.game.scenescript.types.constants;

public class DailyTask {
    public static class DailyTaskActionType {
        public static final int DAILY_TASK_ACTION_ADD_POSSIBLE_POOL = 5;
        public static final int DAILY_TASK_ACTION_ADD_SURE_POOL = 4;
        public static final int DAILY_TASK_ACTION_DEC_VAR = 3;
        public static final int DAILY_TASK_ACTION_INC_VAR = 2;
        public static final int DAILY_TASK_ACTION_NONE = 0;
        public static final int DAILY_TASK_ACTION_SET_VAR = 1;
    }

    public static class DailyTaskCondType {
        public static final int DAILY_TASK_COND_NONE = 0;
        public static final int DAILY_TASK_COND_VAR_EQ = 1;
        public static final int DAILY_TASK_COND_VAR_GT = 3;
        public static final int DAILY_TASK_COND_VAR_LT = 4;
        public static final int DAILY_TASK_COND_VAR_NE = 2;
    }

    public static class DailyTaskFinishType {
        public static final int DAILY_FINISH_MONSTER_CONFIG_NUM = 3;
        public static final int DAILY_FINISH_MONSTER_ID_NUM = 1;
        public static final int DAILY_FINISH_MONSTER_NUM = 5;
        public static final int DAILY_FINISH_CHEST_CONFIG = 6;
        public static final int DAILY_FINISH_GADGET_CONFIG_NUM = 4;
        public static final int DAILY_FINISH_GADGET_ID_NUM = 2;
        public static final int DAILY_FINISH_GATHER = 7;
        public static final int DAILY_FINISH_NONE = 0;
        public static final int DAILY_FINISH_CHALLENGE = 8;
    }

    public static class DailyTaskType {
        public static final int DAILY_TASK_SCENE = 1;
        public static final int DAILY_TASK_QUEST = 0;
    }

    public static class ConditionType {
        public static final int CONDITION_VAR_GT = 5;
        public static final int CONDITION_VAR_LT = 6;
        public static final int CONDITION_VAR_NE = 4;
        public static final int CONDITION_NONE = 0;
        public static final int CONDITION_PLAYER_LEVEL = 2;
        public static final int CONDITION_QUEST = 1;
        public static final int CONDITION_VAR_EQ = 3;
    }
}
