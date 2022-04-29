package emu.grasscutter.game.scenescript.types.constants;

public class Dungeon {
    public static class ChallengeRecordType {
        public static final int CHALLENGE_RECORD_TYPE_IN_TIME = 1;
        public static final int CHALLENGE_RECORD_TYPE_NONE = 0;
    }

    public static class ChallengeType {
        public static final int CHALLENGE_TIME_FLY = 4;
        public static final int CHALLENGE_TRIGGER_IN_TIME_FLY = 11;
        public static final int CHALLENGE_KILL_COUNT = 1;
        public static final int CHALLENGE_KILL_COUNT_FROZEN_LESS = 6;
        public static final int CHALLENGE_KILL_COUNT_GUARD_HP = 10;
        public static final int CHALLENGE_KILL_COUNT_IN_TIME = 2;
        public static final int CHALLENGE_NONE = 0;
        public static final int CHALLENGE_GUARD_HP = 9;
        public static final int CHALLENGE_KILL_COUNT_FAST = 5;
        public static final int CHALLENGE_KILL_MONSTER_IN_TIME = 7;
        public static final int CHALLENGE_SURVIVE = 3;
        public static final int CHALLENGE_TRIGGER_IN_TIME = 8;
    }

    public static class InvolveType {
        public static final int INVOLVE_DYNAMIC_MULTIPLE = 3;
        public static final int INVOLVE_NONE = 0;
        public static final int INVOLVE_ONLY_MULTIPLE = 4;
        public static final int INVOLVE_ONLY_SINGLE = 1;
        public static final int INVOLVE_SINGLE_MULTIPLE = 2;
    }

    public static class ChallengeCondType {
        public static final int CHALLENGE_COND_KILL_FAST = 6;
        public static final int CHALLENGE_COND_FROZEN_LESS = 10;
        public static final int CHALLENGE_COND_IN_TIME = 1;
        public static final int CHALLENGE_COND_TIME_INC = 5;
        public static final int CHALLENGE_COND_NONE = 0;
        public static final int CHALLENGE_COND_SURVIVE = 4;
        public static final int CHALLENGE_COND_DOWN_LESS = 7;
        public static final int CHALLENGE_COND_KILL_COUNT = 3;
        public static final int CHALLENGE_COND_ALL_TIME = 2;
        public static final int CHALLENGE_COND_BEATEN_LESS = 8;
        public static final int CHALLENGE_COND_TIME_DEC = 14;
        public static final int CHALLENGE_COND_TRIGGER = 12;
        public static final int CHALLENGE_COND_UNNATURAL_COUNT = 9;
        public static final int CHALLENGE_COND_GUARD_HP = 13;
        public static final int CHALLENGE_COND_KILL_MONSTER = 11;
    }

    public static class DungeonCondType {
        public static final int DUNGEON_COND_KILL_MONSTER = 3;
        public static final int DUNGEON_COND_KILL_MONSTER_COUNT = 11;
        public static final int DUNGEON_COND_KILL_TYPE_MONSTER = 7;
        public static final int DUNGEON_COND_NONE = 0;
        public static final int DUNGEON_COND_FINISH_CHALLENGE = 14;
        public static final int DUNGEON_COND_FINISH_QUEST = 9;
        public static final int DUNGEON_COND_IN_TIME = 13;
        public static final int DUNGEON_COND_KILL_GROUP_MONSTER = 5;
    }

    public static class DungeonType {
        public static final int DUNGEON_PLOT = 1;
        public static final int DUNGEON_TOWER = 6;
        public static final int DUNGEON_WEEKLY_FIGHT = 4;
        public static final int DUNGEON_BOSS = 7;
        public static final int DUNGEON_DAILY_FIGHT = 3;
        public static final int DUNGEON_DISCARDED = 5;
        public static final int DUNGEON_FIGHT = 2;
        public static final int DUNGEON_NONE = 0;
    }

    public static class SettleShowType {
        public static final int SETTLE_SHOW_BLACKSCREEN = 4;
        public static final int SETTLE_SHOW_KILL_MONSTER_COUNT = 3;
        public static final int SETTLE_SHOW_NONE = 0;
        public static final int SETTLE_SHOW_OPEN_CHEST_COUNT = 2;
        public static final int SETTLE_SHOW_TIME_COST = 1;
    }

    public static class SettleUIType {
        public static final int SETTLE_UI_AlWAYS_SHOW = 0;
        public static final int SETTLE_UI_NEVER_SHOW = 3;
        public static final int SETTLE_UI_ON_FAIL = 2;
        public static final int SETTLE_UI_ON_SUCCESS = 1;
    }

    public static class CycleDungeonType {
        public static final int CYCLE_DUNGEON_DVALIN = 1;
        public static final int CYCLE_DUNGEON_NONE = 0;
        public static final int CYCLE_DUNGEON_RELIQUARY = 5;
        public static final int CYCLE_DUNGEON_SCOIN = 6;
        public static final int CYCLE_DUNGEON_WEAPON_PROMOTE = 4;
        public static final int CYCLE_DUNGEON_WEEKLY = 7;
        public static final int CYCLE_DUNGEON_AVATAR_EXP = 2;
        public static final int CYCLE_DUNGEON_AVATAR_SKILL = 3;
    }
}
