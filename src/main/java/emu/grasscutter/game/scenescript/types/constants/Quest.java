package emu.grasscutter.game.scenescript.types.constants;

public class Quest {
    public static class TalkRoleType {
        public static final int TALK_ROLE_NONE = 0;
        public static final int TALK_ROLE_NPC = 1;
        public static final int TALK_ROLE_PLAYER = 2;
    }

    public static class QuestGuideAuto {
        public static final int QUEST_GUIDE_AUTO_DISABLE = 2;
        public static final int QUEST_GUIDE_AUTO_ENABLE = 1;
        public static final int QUEST_GUIDE_AUTO_NONE = 0;
    }

    public static class QuestGuideStyle {
        public static final int QUEST_GUIDE_STYLE_FINISH = 3;
        public static final int QUEST_GUIDE_STYLE_NONE = 0;
        public static final int QUEST_GUIDE_STYLE_POINT = 4;
        public static final int QUEST_GUIDE_STYLE_START = 1;
        public static final int QUEST_GUIDE_STYLE_TARGET = 2;
    }

    public static class QuestGuideType {
        public static final int QUEST_GUIDE_LOCATION = 1;
        public static final int QUEST_GUIDE_NONE = 0;
        public static final int QUEST_GUIDE_NPC = 2;
    }

    public static class QuestShowType {
        public static final int QUEST_HIDDEN = 1;
        public static final int QUEST_SHOW = 0;
    }

    public static class ShowQuestGuideType {
        public static final int QUEST_GUIDE_ITEM_DISABLE = 1;
        public static final int QUEST_GUIDE_ITEM_ENABLE = 0;
        public static final int QUEST_GUIDE_ITEM_MOVE_HIDE = 2;
    }

    public static class QuestContentType {
        public static final int QUEST_CONTENT_UNLOCK_TRANS_POINT_WITH_TYPE = 116;
        public static final int QUEST_CONTENT_ADD_QUEST_PROGRESS = 24;
        public static final int QUEST_CONTENT_ENTER_ROOM = 17;
        public static final int QUEST_CONTENT_PLAYER_LEVEL_UP = 112;
        public static final int QUEST_CONTENT_SKILL = 107;
        public static final int QUEST_CONTENT_TEAM_DEAD = 21;
        public static final int QUEST_CONTENT_QUEST_VAR_GREATER = 120;
        public static final int QUEST_CONTENT_TRIGGER_FIRE = 6;
        public static final int QUEST_CONTENT_DUNGEON_OPEN_STATUE = 113;
        public static final int QUEST_CONTENT_ENTER_DUNGEON = 9;
        public static final int QUEST_CONTENT_FINISH_DAILY_DUNGEON = 117;
        public static final int QUEST_CONTENT_NOT_FINISH_PLOT = 8;
        public static final int QUEST_CONTENT_QUEST_VAR_EQUAL = 119;
        public static final int QUEST_CONTENT_CITY_LEVEL_UP = 109;
        public static final int QUEST_CONTENT_ITEM_LESS_THAN = 111;
        public static final int QUEST_CONTENT_QUEST_VAR_LESS = 121;
        public static final int QUEST_CONTENT_ENTER_MY_WORLD = 10;
        public static final int QUEST_CONTENT_UNLOCK_AREA = 114;
        public static final int QUEST_CONTENT_WORKTOP_SELECT = 15;
        public static final int QUEST_CONTENT_GAME_TIME_TICK = 18;
        public static final int QUEST_CONTENT_KILL_MONSTER = 1;
        public static final int QUEST_CONTENT_NONE = 0;
        public static final int QUEST_CONTENT_OBTAIN_ITEM = 5;
        public static final int QUEST_CONTENT_OBTAIN_MATERIAL_WITH_SUBTYPE = 13;
        public static final int QUEST_CONTENT_LUA_NOTIFY = 20;
        public static final int QUEST_CONTENT_PATTERN_GROUP_CLEAR_MONSTER = 110;
        public static final int QUEST_CONTENT_CLEAR_GROUP_MONSTER = 7;
        public static final int QUEST_CONTENT_COMPLETE_ANY_TALK = 22;
        public static final int QUEST_CONTENT_COMPLETE_TALK = 2;
        public static final int QUEST_CONTENT_FAIL_DUNGEON = 19;
        public static final int QUEST_CONTENT_FINISH_ITEM_GIVING = 27;
        public static final int QUEST_CONTENT_FINISH_PLOT = 4;
        public static final int QUEST_CONTENT_UNLOCK_TRANS_POINT = 23;
        public static final int QUEST_CONTENT_MONSTER_DIE = 3;
        public static final int QUEST_CONTENT_NICK_NAME = 14;
        public static final int QUEST_CONTENT_OPEN_CHEST_WITH_GADGET_ID = 115;
        public static final int QUEST_CONTENT_DAILY_TASK_COMP_FINISH = 26;
        public static final int QUEST_CONTENT_DESTROY_GADGET = 12;
        public static final int QUEST_CONTENT_FINISH_DUNGEON = 11;
        public static final int QUEST_CONTENT_FINISH_WEEKLY_DUNGEON = 118;
        public static final int QUEST_CONTENT_INTERACT_GADGET = 25;
        public static final int QUEST_CONTENT_SEAL_BATTLE_RESULT = 16;
    }

    public static class QuestExecType {
        public static final int QUEST_EXEC_CREATE_PATTERN_GROUP = 25;
        public static final int QUEST_EXEC_DEL_ALL_SPECIFIC_PACK_ITEM = 29;
        public static final int QUEST_EXEC_SET_IS_GAME_TIME_LOCKED = 10;
        public static final int QUEST_EXEC_UNLOCK_AVATAR_TEAM = 32;
        public static final int QUEST_EXEC_UPDATE_QUEST_VAR = 33;
        public static final int QUEST_EXEC_REFRESH_GROUP_SUITE = 19;
        public static final int QUEST_EXEC_REMOVE_PATTERN_GROUP = 26;
        public static final int QUEST_EXEC_SET_GAME_TIME = 21;
        public static final int QUEST_EXEC_SET_IS_WEATHER_LOCKED = 9;
        public static final int QUEST_EXEC_ACTIVE_ITEM_GIVING = 28;
        public static final int QUEST_EXEC_LOCK_POINT = 17;
        public static final int QUEST_EXEC_REFRESH_GROUP_MONSTER = 7;
        public static final int QUEST_EXEC_ROLLBACK_QUEST = 14;
        public static final int QUEST_EXEC_SET_IS_FLYABLE = 8;
        public static final int QUEST_EXEC_SET_OPEN_STATE = 16;
        public static final int QUEST_EXEC_UNLOCK_AREA = 3;
        public static final int QUEST_EXEC_CHANGE_AVATAR_ELEMET = 6;
        public static final int QUEST_EXEC_LOCK_AVATAR_TEAM = 31;
        public static final int QUEST_EXEC_NONE = 0;
        public static final int QUEST_EXEC_OPEN_BORED = 13;
        public static final int QUEST_EXEC_REMOVE_TRIAL_AVATAR = 20;
        public static final int QUEST_EXEC_UNLOCK_FORCE = 4;
        public static final int QUEST_EXEC_UPDATE_PARENT_QUEST_REWARD_INDEX = 34;
        public static final int QUEST_EXEC_ADD_QUEST_PROGRESS = 23;
        public static final int QUEST_EXEC_LOCK_FORCE = 5;
        public static final int QUEST_EXEC_REFRESH_GROUP_SUITE_RANDOM = 27;
        public static final int QUEST_EXEC_SET_WEATHER_GADGET = 22;
        public static final int QUEST_EXEC_UNLOCK_POINT = 2;
        public static final int QUEST_EXEC_NOTIFY_DAILY_TASK = 24;
        public static final int QUEST_EXEC_DEL_PACK_ITEM = 1;
        public static final int QUEST_EXEC_DEL_PACK_ITEM_BATCH = 18;
        public static final int QUEST_EXEC_GRANT_TRIAL_AVATAR = 12;
        public static final int QUEST_EXEC_NOTIFY_GROUP_LUA = 15;
        public static final int QUEST_EXEC_ROLLBACK_PARENT_QUEST = 30;
        public static final int QUEST_EXEC_SET_IS_TRANSFERABLE = 11;
    }

    public static class QuestState {
        public static final int QUEST_STATE_FAILED = 4;
        public static final int QUEST_STATE_FINISHED = 3;
        public static final int QUEST_STATE_NONE = 0;
        public static final int QUEST_STATE_UNFINISHED = 2;
        public static final int QUEST_STATE_UNSTARTED = 1;
    }

    public static class QuestGuideLayer {
        public static final int QUEST_GUIDE_LAYER_NONE = 0;
        public static final int QUEST_GUIDE_LAYER_SCENE = 2;
        public static final int QUEST_GUIDE_LAYER_UI = 1;
    }

    public static class TalkHeroType {
        public static final int TALK_HERO_LOCAL = 0;
        public static final int TALK_HERO_MAIN = 1;
    }

    public static class QuestCondType {
        public static final int QUEST_COND_CURRENT_AVATAR = 22;
        public static final int QUEST_COND_DAILY_TASK_REWARD_RECEIVED = 13;
        public static final int QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER = 17;
        public static final int QUEST_COND_STATE_NOT_EQUAL = 2;
        public static final int QUEST_COND_QUEST_VAR_GREATER = 25;
        public static final int QUEST_COND_QUEST_VAR_LESS = 26;
        public static final int QUEST_COND_AVATAR_ELEMENT_NOT_EQUAL = 5;
        public static final int QUEST_COND_DAILY_TASK_IN_PROGRESS = 28;
        public static final int QUEST_COND_DAILY_TASK_REWARD_CAN_GET = 12;
        public static final int QUEST_COND_OPEN_STATE_EQUAL = 10;
        public static final int QUEST_COND_PLAYER_LEVEL_REWARD_CAN_GET = 14;
        public static final int QUEST_COND_SCENE_AREA_UNLOCKED = 18;
        public static final int QUEST_COND_CURRENT_AREA = 23;
        public static final int QUEST_COND_DAILY_TASK_OPEN = 11;
        public static final int QUEST_COND_DAILY_TASK_START = 9;
        public static final int QUEST_COND_EXPLORATION_REWARD_CAN_GET = 15;
        public static final int QUEST_COND_IS_DAYTIME = 21;
        public static final int QUEST_COND_AVATAR_ELEMENT_EQUAL = 4;
        public static final int QUEST_COND_CITY_LEVEL_EQUAL_GREATER = 7;
        public static final int QUEST_COND_DAILY_TASK_FINISHED = 29;
        public static final int QUEST_COND_PACK_HAVE_ITEM = 3;
        public static final int QUEST_COND_STATE_EQUAL = 1;
        public static final int QUEST_COND_AVATAR_CAN_CHANGE_ELEMENT = 6;
        public static final int QUEST_COND_FORGE_HAVE_FINISH = 27;
        public static final int QUEST_COND_ITEM_GIVING_ACTIVED = 19;
        public static final int QUEST_COND_ITEM_GIVING_FINISHED = 20;
        public static final int QUEST_COND_ITEM_NUM_LESS_THAN = 8;
        public static final int QUEST_COND_IS_WORLD_OWNER = 16;
        public static final int QUEST_COND_NONE = 0;
        public static final int QUEST_COND_QUEST_VAR_EQUAL = 24;
    }

    public static class QuestType {
        public static final int FQ = 1;
        public static final int IQ = 5;
        public static final int LQ = 2;
        public static final int VQ = 6;
        public static final int AQ = 0;
        public static final int DQ = 4;
        public static final int EQ = 3;
    }

    public static class RandomQuestFilterType {
        public static final int RQ_FILTER_NONE = 0;
        public static final int RQ_FILTER_NPC = 2;
        public static final int RQ_FILTER_PLAYER_LEVEL = 3;
        public static final int RQ_FILTER_PLAYER_POS_RING = 1;
    }

    public static class TalkBeginWay {
        public static final int TALK_BEGIN_AUTO = 1;
        public static final int TALK_BEGIN_MANUAL = 2;
        public static final int TALK_BEGIN_NONE = 0;
    }

    public static class TalkShowType {
        public static final int TALK_SHOW_DEFAULT = 0;
        public static final int TALK_SHOW_FORCE_SELECT = 1;
    }
}
