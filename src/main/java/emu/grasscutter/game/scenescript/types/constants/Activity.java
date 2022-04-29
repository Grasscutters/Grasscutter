package emu.grasscutter.game.scenescript.types.constants;

public class Activity {
    public static class ActivityActionType {
        public static final int ACTIVITY_ACTION_NONE = 0;
        public static final int ACTIVITY_ACTION_UNLOCK_POINT = 1;
        public static final int ACTIVITY_ACTION_LOCK_POINT = 2;
    }

    public static class ActivityCondDefaultStateType {
        public static final int ACTIVITY_COND_DEFAULT_STATE_FALSE = 0;
        public static final int ACTIVITY_COND_DEFAULT_STATE_TRUE = 1;
    }

    public static class ActivityCondType {
        public static final int ACTIVITY_COND_TIME_GREATER = 1;
        public static final int ACTIVITY_COND_TIME_LESS = 2;
        public static final int ACTIVITY_COND_NONE = 0;
        public static final int ACTIVITY_COND_PLAYER_LEVEL_GREATER = 3;
    }

    public static class ActivityDropType {
        public static final int ACTIVITY_DROP_TYPE_GATHER = 4;
        public static final int ACTIVITY_DROP_TYPE_MONSTER_ID = 1;
        public static final int ACTIVITY_DROP_TYPE_MONSTER_TAG = 3;
        public static final int ACTIVITY_DROP_TYPE_NONE = 0;
        public static final int ACTIVITY_DROP_TYPE_SUB_FIELD_DROP = 5;
        public static final int ACTIVITY_DROP_TYPE_DROP_TAG = 2;
    }

    public static class ActivityType {
        public static final int ACTIVITY_GENERAL = 0;
        public static final int ACTIVITY_SEA_LAMP = 1;
    }
}
