package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Quest {
	public static class QuestState extends TwoArgFunction {
		public static final int QUEST_STATE_NONE = 0;
		public static final int QUEST_STATE_UNSTARTED = 1;
		public static final int QUEST_STATE_UNFINISHED = 2;
		public static final int QUEST_STATE_FINISHED = 3;
		public static final int QUEST_STATE_FAILED = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_STATE_NONE", LuaValue.valueOf(QUEST_STATE_NONE));
			scriptConstQuest.set("QUEST_STATE_UNSTARTED", LuaValue.valueOf(QUEST_STATE_UNSTARTED));
			scriptConstQuest.set("QUEST_STATE_UNFINISHED", LuaValue.valueOf(QUEST_STATE_UNFINISHED));
			scriptConstQuest.set("QUEST_STATE_FINISHED", LuaValue.valueOf(QUEST_STATE_FINISHED));
			scriptConstQuest.set("QUEST_STATE_FAILED", LuaValue.valueOf(QUEST_STATE_FAILED));
			env.set("QuestState", scriptConstQuest);
			env.get("package").get("loaded").set("QuestState", scriptConstQuest);
			return env;
		}
	}
	public static class QuestCondType extends TwoArgFunction {
		public static final int QUEST_COND_NONE = 0;
		public static final int QUEST_COND_STATE_EQUAL = 1;
		public static final int QUEST_COND_STATE_NOT_EQUAL = 2;
		public static final int QUEST_COND_PACK_HAVE_ITEM = 3;
		public static final int QUEST_COND_AVATAR_ELEMENT_EQUAL = 4;
		public static final int QUEST_COND_AVATAR_ELEMENT_NOT_EQUAL = 5;
		public static final int QUEST_COND_AVATAR_CAN_CHANGE_ELEMENT = 6;
		public static final int QUEST_COND_CITY_LEVEL_EQUAL_GREATER = 7;
		public static final int QUEST_COND_ITEM_NUM_LESS_THAN = 8;
		public static final int QUEST_COND_DAILY_TASK_START = 9;
		public static final int QUEST_COND_OPEN_STATE_EQUAL = 10;
		public static final int QUEST_COND_DAILY_TASK_OPEN = 11;
		public static final int QUEST_COND_DAILY_TASK_REWARD_CAN_GET = 12;
		public static final int QUEST_COND_DAILY_TASK_REWARD_RECEIVED = 13;
		public static final int QUEST_COND_PLAYER_LEVEL_REWARD_CAN_GET = 14;
		public static final int QUEST_COND_EXPLORATION_REWARD_CAN_GET = 15;
		public static final int QUEST_COND_IS_WORLD_OWNER = 16;
		public static final int QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER = 17;
		public static final int QUEST_COND_SCENE_AREA_UNLOCKED = 18;
		public static final int QUEST_COND_ITEM_GIVING_ACTIVED = 19;
		public static final int QUEST_COND_ITEM_GIVING_FINISHED = 20;
		public static final int QUEST_COND_IS_DAYTIME = 21;
		public static final int QUEST_COND_CURRENT_AVATAR = 22;
		public static final int QUEST_COND_CURRENT_AREA = 23;
		public static final int QUEST_COND_QUEST_VAR_EQUAL = 24;
		public static final int QUEST_COND_QUEST_VAR_GREATER = 25;
		public static final int QUEST_COND_QUEST_VAR_LESS = 26;
		public static final int QUEST_COND_FORGE_HAVE_FINISH = 27;
		public static final int QUEST_COND_DAILY_TASK_IN_PROGRESS = 28;
		public static final int QUEST_COND_DAILY_TASK_FINISHED = 29;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_COND_NONE", LuaValue.valueOf(QUEST_COND_NONE));
			scriptConstQuest.set("QUEST_COND_STATE_EQUAL", LuaValue.valueOf(QUEST_COND_STATE_EQUAL));
			scriptConstQuest.set("QUEST_COND_STATE_NOT_EQUAL", LuaValue.valueOf(QUEST_COND_STATE_NOT_EQUAL));
			scriptConstQuest.set("QUEST_COND_PACK_HAVE_ITEM", LuaValue.valueOf(QUEST_COND_PACK_HAVE_ITEM));
			scriptConstQuest.set("QUEST_COND_AVATAR_ELEMENT_EQUAL", LuaValue.valueOf(QUEST_COND_AVATAR_ELEMENT_EQUAL));
			scriptConstQuest.set("QUEST_COND_AVATAR_ELEMENT_NOT_EQUAL", LuaValue.valueOf(QUEST_COND_AVATAR_ELEMENT_NOT_EQUAL));
			scriptConstQuest.set("QUEST_COND_AVATAR_CAN_CHANGE_ELEMENT", LuaValue.valueOf(QUEST_COND_AVATAR_CAN_CHANGE_ELEMENT));
			scriptConstQuest.set("QUEST_COND_CITY_LEVEL_EQUAL_GREATER", LuaValue.valueOf(QUEST_COND_CITY_LEVEL_EQUAL_GREATER));
			scriptConstQuest.set("QUEST_COND_ITEM_NUM_LESS_THAN", LuaValue.valueOf(QUEST_COND_ITEM_NUM_LESS_THAN));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_START", LuaValue.valueOf(QUEST_COND_DAILY_TASK_START));
			scriptConstQuest.set("QUEST_COND_OPEN_STATE_EQUAL", LuaValue.valueOf(QUEST_COND_OPEN_STATE_EQUAL));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_OPEN", LuaValue.valueOf(QUEST_COND_DAILY_TASK_OPEN));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_REWARD_CAN_GET", LuaValue.valueOf(QUEST_COND_DAILY_TASK_REWARD_CAN_GET));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_REWARD_RECEIVED", LuaValue.valueOf(QUEST_COND_DAILY_TASK_REWARD_RECEIVED));
			scriptConstQuest.set("QUEST_COND_PLAYER_LEVEL_REWARD_CAN_GET", LuaValue.valueOf(QUEST_COND_PLAYER_LEVEL_REWARD_CAN_GET));
			scriptConstQuest.set("QUEST_COND_EXPLORATION_REWARD_CAN_GET", LuaValue.valueOf(QUEST_COND_EXPLORATION_REWARD_CAN_GET));
			scriptConstQuest.set("QUEST_COND_IS_WORLD_OWNER", LuaValue.valueOf(QUEST_COND_IS_WORLD_OWNER));
			scriptConstQuest.set("QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER", LuaValue.valueOf(QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER));
			scriptConstQuest.set("QUEST_COND_SCENE_AREA_UNLOCKED", LuaValue.valueOf(QUEST_COND_SCENE_AREA_UNLOCKED));
			scriptConstQuest.set("QUEST_COND_ITEM_GIVING_ACTIVED", LuaValue.valueOf(QUEST_COND_ITEM_GIVING_ACTIVED));
			scriptConstQuest.set("QUEST_COND_ITEM_GIVING_FINISHED", LuaValue.valueOf(QUEST_COND_ITEM_GIVING_FINISHED));
			scriptConstQuest.set("QUEST_COND_IS_DAYTIME", LuaValue.valueOf(QUEST_COND_IS_DAYTIME));
			scriptConstQuest.set("QUEST_COND_CURRENT_AVATAR", LuaValue.valueOf(QUEST_COND_CURRENT_AVATAR));
			scriptConstQuest.set("QUEST_COND_CURRENT_AREA", LuaValue.valueOf(QUEST_COND_CURRENT_AREA));
			scriptConstQuest.set("QUEST_COND_QUEST_VAR_EQUAL", LuaValue.valueOf(QUEST_COND_QUEST_VAR_EQUAL));
			scriptConstQuest.set("QUEST_COND_QUEST_VAR_GREATER", LuaValue.valueOf(QUEST_COND_QUEST_VAR_GREATER));
			scriptConstQuest.set("QUEST_COND_QUEST_VAR_LESS", LuaValue.valueOf(QUEST_COND_QUEST_VAR_LESS));
			scriptConstQuest.set("QUEST_COND_FORGE_HAVE_FINISH", LuaValue.valueOf(QUEST_COND_FORGE_HAVE_FINISH));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_IN_PROGRESS", LuaValue.valueOf(QUEST_COND_DAILY_TASK_IN_PROGRESS));
			scriptConstQuest.set("QUEST_COND_DAILY_TASK_FINISHED", LuaValue.valueOf(QUEST_COND_DAILY_TASK_FINISHED));
			env.set("QuestCondType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestCondType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestContentType extends TwoArgFunction {
		public static final int QUEST_CONTENT_NONE = 0;
		public static final int QUEST_CONTENT_KILL_MONSTER = 1;
		public static final int QUEST_CONTENT_COMPLETE_TALK = 2;
		public static final int QUEST_CONTENT_MONSTER_DIE = 3;
		public static final int QUEST_CONTENT_FINISH_PLOT = 4;
		public static final int QUEST_CONTENT_OBTAIN_ITEM = 5;
		public static final int QUEST_CONTENT_TRIGGER_FIRE = 6;
		public static final int QUEST_CONTENT_CLEAR_GROUP_MONSTER = 7;
		public static final int QUEST_CONTENT_NOT_FINISH_PLOT = 8;
		public static final int QUEST_CONTENT_ENTER_DUNGEON = 9;
		public static final int QUEST_CONTENT_ENTER_MY_WORLD = 10;
		public static final int QUEST_CONTENT_FINISH_DUNGEON = 11;
		public static final int QUEST_CONTENT_DESTROY_GADGET = 12;
		public static final int QUEST_CONTENT_OBTAIN_MATERIAL_WITH_SUBTYPE = 13;
		public static final int QUEST_CONTENT_NICK_NAME = 14;
		public static final int QUEST_CONTENT_WORKTOP_SELECT = 15;
		public static final int QUEST_CONTENT_SEAL_BATTLE_RESULT = 16;
		public static final int QUEST_CONTENT_ENTER_ROOM = 17;
		public static final int QUEST_CONTENT_GAME_TIME_TICK = 18;
		public static final int QUEST_CONTENT_FAIL_DUNGEON = 19;
		public static final int QUEST_CONTENT_LUA_NOTIFY = 20;
		public static final int QUEST_CONTENT_TEAM_DEAD = 21;
		public static final int QUEST_CONTENT_COMPLETE_ANY_TALK = 22;
		public static final int QUEST_CONTENT_UNLOCK_TRANS_POINT = 23;
		public static final int QUEST_CONTENT_ADD_QUEST_PROGRESS = 24;
		public static final int QUEST_CONTENT_INTERACT_GADGET = 25;
		public static final int QUEST_CONTENT_DAILY_TASK_COMP_FINISH = 26;
		public static final int QUEST_CONTENT_FINISH_ITEM_GIVING = 27;
		public static final int QUEST_CONTENT_SKILL = 107;
		public static final int QUEST_CONTENT_CITY_LEVEL_UP = 109;
		public static final int QUEST_CONTENT_PATTERN_GROUP_CLEAR_MONSTER = 110;
		public static final int QUEST_CONTENT_ITEM_LESS_THAN = 111;
		public static final int QUEST_CONTENT_PLAYER_LEVEL_UP = 112;
		public static final int QUEST_CONTENT_DUNGEON_OPEN_STATUE = 113;
		public static final int QUEST_CONTENT_UNLOCK_AREA = 114;
		public static final int QUEST_CONTENT_OPEN_CHEST_WITH_GADGET_ID = 115;
		public static final int QUEST_CONTENT_UNLOCK_TRANS_POINT_WITH_TYPE = 116;
		public static final int QUEST_CONTENT_FINISH_DAILY_DUNGEON = 117;
		public static final int QUEST_CONTENT_FINISH_WEEKLY_DUNGEON = 118;
		public static final int QUEST_CONTENT_QUEST_VAR_EQUAL = 119;
		public static final int QUEST_CONTENT_QUEST_VAR_GREATER = 120;
		public static final int QUEST_CONTENT_QUEST_VAR_LESS = 121;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_CONTENT_NONE", LuaValue.valueOf(QUEST_CONTENT_NONE));
			scriptConstQuest.set("QUEST_CONTENT_KILL_MONSTER", LuaValue.valueOf(QUEST_CONTENT_KILL_MONSTER));
			scriptConstQuest.set("QUEST_CONTENT_COMPLETE_TALK", LuaValue.valueOf(QUEST_CONTENT_COMPLETE_TALK));
			scriptConstQuest.set("QUEST_CONTENT_MONSTER_DIE", LuaValue.valueOf(QUEST_CONTENT_MONSTER_DIE));
			scriptConstQuest.set("QUEST_CONTENT_FINISH_PLOT", LuaValue.valueOf(QUEST_CONTENT_FINISH_PLOT));
			scriptConstQuest.set("QUEST_CONTENT_OBTAIN_ITEM", LuaValue.valueOf(QUEST_CONTENT_OBTAIN_ITEM));
			scriptConstQuest.set("QUEST_CONTENT_TRIGGER_FIRE", LuaValue.valueOf(QUEST_CONTENT_TRIGGER_FIRE));
			scriptConstQuest.set("QUEST_CONTENT_CLEAR_GROUP_MONSTER", LuaValue.valueOf(QUEST_CONTENT_CLEAR_GROUP_MONSTER));
			scriptConstQuest.set("QUEST_CONTENT_NOT_FINISH_PLOT", LuaValue.valueOf(QUEST_CONTENT_NOT_FINISH_PLOT));
			scriptConstQuest.set("QUEST_CONTENT_ENTER_DUNGEON", LuaValue.valueOf(QUEST_CONTENT_ENTER_DUNGEON));
			scriptConstQuest.set("QUEST_CONTENT_ENTER_MY_WORLD", LuaValue.valueOf(QUEST_CONTENT_ENTER_MY_WORLD));
			scriptConstQuest.set("QUEST_CONTENT_FINISH_DUNGEON", LuaValue.valueOf(QUEST_CONTENT_FINISH_DUNGEON));
			scriptConstQuest.set("QUEST_CONTENT_DESTROY_GADGET", LuaValue.valueOf(QUEST_CONTENT_DESTROY_GADGET));
			scriptConstQuest.set("QUEST_CONTENT_OBTAIN_MATERIAL_WITH_SUBTYPE", LuaValue.valueOf(QUEST_CONTENT_OBTAIN_MATERIAL_WITH_SUBTYPE));
			scriptConstQuest.set("QUEST_CONTENT_NICK_NAME", LuaValue.valueOf(QUEST_CONTENT_NICK_NAME));
			scriptConstQuest.set("QUEST_CONTENT_WORKTOP_SELECT", LuaValue.valueOf(QUEST_CONTENT_WORKTOP_SELECT));
			scriptConstQuest.set("QUEST_CONTENT_SEAL_BATTLE_RESULT", LuaValue.valueOf(QUEST_CONTENT_SEAL_BATTLE_RESULT));
			scriptConstQuest.set("QUEST_CONTENT_ENTER_ROOM", LuaValue.valueOf(QUEST_CONTENT_ENTER_ROOM));
			scriptConstQuest.set("QUEST_CONTENT_GAME_TIME_TICK", LuaValue.valueOf(QUEST_CONTENT_GAME_TIME_TICK));
			scriptConstQuest.set("QUEST_CONTENT_FAIL_DUNGEON", LuaValue.valueOf(QUEST_CONTENT_FAIL_DUNGEON));
			scriptConstQuest.set("QUEST_CONTENT_LUA_NOTIFY", LuaValue.valueOf(QUEST_CONTENT_LUA_NOTIFY));
			scriptConstQuest.set("QUEST_CONTENT_TEAM_DEAD", LuaValue.valueOf(QUEST_CONTENT_TEAM_DEAD));
			scriptConstQuest.set("QUEST_CONTENT_COMPLETE_ANY_TALK", LuaValue.valueOf(QUEST_CONTENT_COMPLETE_ANY_TALK));
			scriptConstQuest.set("QUEST_CONTENT_UNLOCK_TRANS_POINT", LuaValue.valueOf(QUEST_CONTENT_UNLOCK_TRANS_POINT));
			scriptConstQuest.set("QUEST_CONTENT_ADD_QUEST_PROGRESS", LuaValue.valueOf(QUEST_CONTENT_ADD_QUEST_PROGRESS));
			scriptConstQuest.set("QUEST_CONTENT_INTERACT_GADGET", LuaValue.valueOf(QUEST_CONTENT_INTERACT_GADGET));
			scriptConstQuest.set("QUEST_CONTENT_DAILY_TASK_COMP_FINISH", LuaValue.valueOf(QUEST_CONTENT_DAILY_TASK_COMP_FINISH));
			scriptConstQuest.set("QUEST_CONTENT_FINISH_ITEM_GIVING", LuaValue.valueOf(QUEST_CONTENT_FINISH_ITEM_GIVING));
			scriptConstQuest.set("QUEST_CONTENT_SKILL", LuaValue.valueOf(QUEST_CONTENT_SKILL));
			scriptConstQuest.set("QUEST_CONTENT_CITY_LEVEL_UP", LuaValue.valueOf(QUEST_CONTENT_CITY_LEVEL_UP));
			scriptConstQuest.set("QUEST_CONTENT_PATTERN_GROUP_CLEAR_MONSTER", LuaValue.valueOf(QUEST_CONTENT_PATTERN_GROUP_CLEAR_MONSTER));
			scriptConstQuest.set("QUEST_CONTENT_ITEM_LESS_THAN", LuaValue.valueOf(QUEST_CONTENT_ITEM_LESS_THAN));
			scriptConstQuest.set("QUEST_CONTENT_PLAYER_LEVEL_UP", LuaValue.valueOf(QUEST_CONTENT_PLAYER_LEVEL_UP));
			scriptConstQuest.set("QUEST_CONTENT_DUNGEON_OPEN_STATUE", LuaValue.valueOf(QUEST_CONTENT_DUNGEON_OPEN_STATUE));
			scriptConstQuest.set("QUEST_CONTENT_UNLOCK_AREA", LuaValue.valueOf(QUEST_CONTENT_UNLOCK_AREA));
			scriptConstQuest.set("QUEST_CONTENT_OPEN_CHEST_WITH_GADGET_ID", LuaValue.valueOf(QUEST_CONTENT_OPEN_CHEST_WITH_GADGET_ID));
			scriptConstQuest.set("QUEST_CONTENT_UNLOCK_TRANS_POINT_WITH_TYPE", LuaValue.valueOf(QUEST_CONTENT_UNLOCK_TRANS_POINT_WITH_TYPE));
			scriptConstQuest.set("QUEST_CONTENT_FINISH_DAILY_DUNGEON", LuaValue.valueOf(QUEST_CONTENT_FINISH_DAILY_DUNGEON));
			scriptConstQuest.set("QUEST_CONTENT_FINISH_WEEKLY_DUNGEON", LuaValue.valueOf(QUEST_CONTENT_FINISH_WEEKLY_DUNGEON));
			scriptConstQuest.set("QUEST_CONTENT_QUEST_VAR_EQUAL", LuaValue.valueOf(QUEST_CONTENT_QUEST_VAR_EQUAL));
			scriptConstQuest.set("QUEST_CONTENT_QUEST_VAR_GREATER", LuaValue.valueOf(QUEST_CONTENT_QUEST_VAR_GREATER));
			scriptConstQuest.set("QUEST_CONTENT_QUEST_VAR_LESS", LuaValue.valueOf(QUEST_CONTENT_QUEST_VAR_LESS));
			env.set("QuestContentType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestContentType", scriptConstQuest);
			return env;
		}
	}
	public static class RandomQuestFilterType extends TwoArgFunction {
		public static final int RQ_FILTER_NONE = 0;
		public static final int RQ_FILTER_PLAYER_POS_RING = 1;
		public static final int RQ_FILTER_NPC = 2;
		public static final int RQ_FILTER_PLAYER_LEVEL = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("RQ_FILTER_NONE", LuaValue.valueOf(RQ_FILTER_NONE));
			scriptConstQuest.set("RQ_FILTER_PLAYER_POS_RING", LuaValue.valueOf(RQ_FILTER_PLAYER_POS_RING));
			scriptConstQuest.set("RQ_FILTER_NPC", LuaValue.valueOf(RQ_FILTER_NPC));
			scriptConstQuest.set("RQ_FILTER_PLAYER_LEVEL", LuaValue.valueOf(RQ_FILTER_PLAYER_LEVEL));
			env.set("RandomQuestFilterType", scriptConstQuest);
			env.get("package").get("loaded").set("RandomQuestFilterType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestGuideLayer extends TwoArgFunction {
		public static final int QUEST_GUIDE_LAYER_NONE = 0;
		public static final int QUEST_GUIDE_LAYER_UI = 1;
		public static final int QUEST_GUIDE_LAYER_SCENE = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_GUIDE_LAYER_NONE", LuaValue.valueOf(QUEST_GUIDE_LAYER_NONE));
			scriptConstQuest.set("QUEST_GUIDE_LAYER_UI", LuaValue.valueOf(QUEST_GUIDE_LAYER_UI));
			scriptConstQuest.set("QUEST_GUIDE_LAYER_SCENE", LuaValue.valueOf(QUEST_GUIDE_LAYER_SCENE));
			env.set("QuestGuideLayer", scriptConstQuest);
			env.get("package").get("loaded").set("QuestGuideLayer", scriptConstQuest);
			return env;
		}
	}
	public static class QuestType extends TwoArgFunction {
		public static final int AQ = 0;
		public static final int FQ = 1;
		public static final int LQ = 2;
		public static final int EQ = 3;
		public static final int DQ = 4;
		public static final int IQ = 5;
		public static final int VQ = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("AQ", LuaValue.valueOf(AQ));
			scriptConstQuest.set("FQ", LuaValue.valueOf(FQ));
			scriptConstQuest.set("LQ", LuaValue.valueOf(LQ));
			scriptConstQuest.set("EQ", LuaValue.valueOf(EQ));
			scriptConstQuest.set("DQ", LuaValue.valueOf(DQ));
			scriptConstQuest.set("IQ", LuaValue.valueOf(IQ));
			scriptConstQuest.set("VQ", LuaValue.valueOf(VQ));
			env.set("QuestType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestType", scriptConstQuest);
			return env;
		}
	}
	public static class TalkBeginWay extends TwoArgFunction {
		public static final int TALK_BEGIN_NONE = 0;
		public static final int TALK_BEGIN_AUTO = 1;
		public static final int TALK_BEGIN_MANUAL = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("TALK_BEGIN_NONE", LuaValue.valueOf(TALK_BEGIN_NONE));
			scriptConstQuest.set("TALK_BEGIN_AUTO", LuaValue.valueOf(TALK_BEGIN_AUTO));
			scriptConstQuest.set("TALK_BEGIN_MANUAL", LuaValue.valueOf(TALK_BEGIN_MANUAL));
			env.set("TalkBeginWay", scriptConstQuest);
			env.get("package").get("loaded").set("TalkBeginWay", scriptConstQuest);
			return env;
		}
	}
	public static class TalkRoleType extends TwoArgFunction {
		public static final int TALK_ROLE_NONE = 0;
		public static final int TALK_ROLE_NPC = 1;
		public static final int TALK_ROLE_PLAYER = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("TALK_ROLE_NONE", LuaValue.valueOf(TALK_ROLE_NONE));
			scriptConstQuest.set("TALK_ROLE_NPC", LuaValue.valueOf(TALK_ROLE_NPC));
			scriptConstQuest.set("TALK_ROLE_PLAYER", LuaValue.valueOf(TALK_ROLE_PLAYER));
			env.set("TalkRoleType", scriptConstQuest);
			env.get("package").get("loaded").set("TalkRoleType", scriptConstQuest);
			return env;
		}
	}
	public static class TalkShowType extends TwoArgFunction {
		public static final int TALK_SHOW_DEFAULT = 0;
		public static final int TALK_SHOW_FORCE_SELECT = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("TALK_SHOW_DEFAULT", LuaValue.valueOf(TALK_SHOW_DEFAULT));
			scriptConstQuest.set("TALK_SHOW_FORCE_SELECT", LuaValue.valueOf(TALK_SHOW_FORCE_SELECT));
			env.set("TalkShowType", scriptConstQuest);
			env.get("package").get("loaded").set("TalkShowType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestGuideStyle extends TwoArgFunction {
		public static final int QUEST_GUIDE_STYLE_NONE = 0;
		public static final int QUEST_GUIDE_STYLE_START = 1;
		public static final int QUEST_GUIDE_STYLE_TARGET = 2;
		public static final int QUEST_GUIDE_STYLE_FINISH = 3;
		public static final int QUEST_GUIDE_STYLE_POINT = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_GUIDE_STYLE_NONE", LuaValue.valueOf(QUEST_GUIDE_STYLE_NONE));
			scriptConstQuest.set("QUEST_GUIDE_STYLE_START", LuaValue.valueOf(QUEST_GUIDE_STYLE_START));
			scriptConstQuest.set("QUEST_GUIDE_STYLE_TARGET", LuaValue.valueOf(QUEST_GUIDE_STYLE_TARGET));
			scriptConstQuest.set("QUEST_GUIDE_STYLE_FINISH", LuaValue.valueOf(QUEST_GUIDE_STYLE_FINISH));
			scriptConstQuest.set("QUEST_GUIDE_STYLE_POINT", LuaValue.valueOf(QUEST_GUIDE_STYLE_POINT));
			env.set("QuestGuideStyle", scriptConstQuest);
			env.get("package").get("loaded").set("QuestGuideStyle", scriptConstQuest);
			return env;
		}
	}
	public static class QuestGuideType extends TwoArgFunction {
		public static final int QUEST_GUIDE_NONE = 0;
		public static final int QUEST_GUIDE_LOCATION = 1;
		public static final int QUEST_GUIDE_NPC = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_GUIDE_NONE", LuaValue.valueOf(QUEST_GUIDE_NONE));
			scriptConstQuest.set("QUEST_GUIDE_LOCATION", LuaValue.valueOf(QUEST_GUIDE_LOCATION));
			scriptConstQuest.set("QUEST_GUIDE_NPC", LuaValue.valueOf(QUEST_GUIDE_NPC));
			env.set("QuestGuideType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestGuideType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestShowType extends TwoArgFunction {
		public static final int QUEST_SHOW = 0;
		public static final int QUEST_HIDDEN = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_SHOW", LuaValue.valueOf(QUEST_SHOW));
			scriptConstQuest.set("QUEST_HIDDEN", LuaValue.valueOf(QUEST_HIDDEN));
			env.set("QuestShowType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestShowType", scriptConstQuest);
			return env;
		}
	}
	public static class ShowQuestGuideType extends TwoArgFunction {
		public static final int QUEST_GUIDE_ITEM_ENABLE = 0;
		public static final int QUEST_GUIDE_ITEM_DISABLE = 1;
		public static final int QUEST_GUIDE_ITEM_MOVE_HIDE = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_GUIDE_ITEM_ENABLE", LuaValue.valueOf(QUEST_GUIDE_ITEM_ENABLE));
			scriptConstQuest.set("QUEST_GUIDE_ITEM_DISABLE", LuaValue.valueOf(QUEST_GUIDE_ITEM_DISABLE));
			scriptConstQuest.set("QUEST_GUIDE_ITEM_MOVE_HIDE", LuaValue.valueOf(QUEST_GUIDE_ITEM_MOVE_HIDE));
			env.set("ShowQuestGuideType", scriptConstQuest);
			env.get("package").get("loaded").set("ShowQuestGuideType", scriptConstQuest);
			return env;
		}
	}
	public static class TalkHeroType extends TwoArgFunction {
		public static final int TALK_HERO_LOCAL = 0;
		public static final int TALK_HERO_MAIN = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("TALK_HERO_LOCAL", LuaValue.valueOf(TALK_HERO_LOCAL));
			scriptConstQuest.set("TALK_HERO_MAIN", LuaValue.valueOf(TALK_HERO_MAIN));
			env.set("TalkHeroType", scriptConstQuest);
			env.get("package").get("loaded").set("TalkHeroType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestExecType extends TwoArgFunction {
		public static final int QUEST_EXEC_NONE = 0;
		public static final int QUEST_EXEC_DEL_PACK_ITEM = 1;
		public static final int QUEST_EXEC_UNLOCK_POINT = 2;
		public static final int QUEST_EXEC_UNLOCK_AREA = 3;
		public static final int QUEST_EXEC_UNLOCK_FORCE = 4;
		public static final int QUEST_EXEC_LOCK_FORCE = 5;
		public static final int QUEST_EXEC_CHANGE_AVATAR_ELEMET = 6;
		public static final int QUEST_EXEC_REFRESH_GROUP_MONSTER = 7;
		public static final int QUEST_EXEC_SET_IS_FLYABLE = 8;
		public static final int QUEST_EXEC_SET_IS_WEATHER_LOCKED = 9;
		public static final int QUEST_EXEC_SET_IS_GAME_TIME_LOCKED = 10;
		public static final int QUEST_EXEC_SET_IS_TRANSFERABLE = 11;
		public static final int QUEST_EXEC_GRANT_TRIAL_AVATAR = 12;
		public static final int QUEST_EXEC_OPEN_BORED = 13;
		public static final int QUEST_EXEC_ROLLBACK_QUEST = 14;
		public static final int QUEST_EXEC_NOTIFY_GROUP_LUA = 15;
		public static final int QUEST_EXEC_SET_OPEN_STATE = 16;
		public static final int QUEST_EXEC_LOCK_POINT = 17;
		public static final int QUEST_EXEC_DEL_PACK_ITEM_BATCH = 18;
		public static final int QUEST_EXEC_REFRESH_GROUP_SUITE = 19;
		public static final int QUEST_EXEC_REMOVE_TRIAL_AVATAR = 20;
		public static final int QUEST_EXEC_SET_GAME_TIME = 21;
		public static final int QUEST_EXEC_SET_WEATHER_GADGET = 22;
		public static final int QUEST_EXEC_ADD_QUEST_PROGRESS = 23;
		public static final int QUEST_EXEC_NOTIFY_DAILY_TASK = 24;
		public static final int QUEST_EXEC_CREATE_PATTERN_GROUP = 25;
		public static final int QUEST_EXEC_REMOVE_PATTERN_GROUP = 26;
		public static final int QUEST_EXEC_REFRESH_GROUP_SUITE_RANDOM = 27;
		public static final int QUEST_EXEC_ACTIVE_ITEM_GIVING = 28;
		public static final int QUEST_EXEC_DEL_ALL_SPECIFIC_PACK_ITEM = 29;
		public static final int QUEST_EXEC_ROLLBACK_PARENT_QUEST = 30;
		public static final int QUEST_EXEC_LOCK_AVATAR_TEAM = 31;
		public static final int QUEST_EXEC_UNLOCK_AVATAR_TEAM = 32;
		public static final int QUEST_EXEC_UPDATE_QUEST_VAR = 33;
		public static final int QUEST_EXEC_UPDATE_PARENT_QUEST_REWARD_INDEX = 34;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_EXEC_NONE", LuaValue.valueOf(QUEST_EXEC_NONE));
			scriptConstQuest.set("QUEST_EXEC_DEL_PACK_ITEM", LuaValue.valueOf(QUEST_EXEC_DEL_PACK_ITEM));
			scriptConstQuest.set("QUEST_EXEC_UNLOCK_POINT", LuaValue.valueOf(QUEST_EXEC_UNLOCK_POINT));
			scriptConstQuest.set("QUEST_EXEC_UNLOCK_AREA", LuaValue.valueOf(QUEST_EXEC_UNLOCK_AREA));
			scriptConstQuest.set("QUEST_EXEC_UNLOCK_FORCE", LuaValue.valueOf(QUEST_EXEC_UNLOCK_FORCE));
			scriptConstQuest.set("QUEST_EXEC_LOCK_FORCE", LuaValue.valueOf(QUEST_EXEC_LOCK_FORCE));
			scriptConstQuest.set("QUEST_EXEC_CHANGE_AVATAR_ELEMET", LuaValue.valueOf(QUEST_EXEC_CHANGE_AVATAR_ELEMET));
			scriptConstQuest.set("QUEST_EXEC_REFRESH_GROUP_MONSTER", LuaValue.valueOf(QUEST_EXEC_REFRESH_GROUP_MONSTER));
			scriptConstQuest.set("QUEST_EXEC_SET_IS_FLYABLE", LuaValue.valueOf(QUEST_EXEC_SET_IS_FLYABLE));
			scriptConstQuest.set("QUEST_EXEC_SET_IS_WEATHER_LOCKED", LuaValue.valueOf(QUEST_EXEC_SET_IS_WEATHER_LOCKED));
			scriptConstQuest.set("QUEST_EXEC_SET_IS_GAME_TIME_LOCKED", LuaValue.valueOf(QUEST_EXEC_SET_IS_GAME_TIME_LOCKED));
			scriptConstQuest.set("QUEST_EXEC_SET_IS_TRANSFERABLE", LuaValue.valueOf(QUEST_EXEC_SET_IS_TRANSFERABLE));
			scriptConstQuest.set("QUEST_EXEC_GRANT_TRIAL_AVATAR", LuaValue.valueOf(QUEST_EXEC_GRANT_TRIAL_AVATAR));
			scriptConstQuest.set("QUEST_EXEC_OPEN_BORED", LuaValue.valueOf(QUEST_EXEC_OPEN_BORED));
			scriptConstQuest.set("QUEST_EXEC_ROLLBACK_QUEST", LuaValue.valueOf(QUEST_EXEC_ROLLBACK_QUEST));
			scriptConstQuest.set("QUEST_EXEC_NOTIFY_GROUP_LUA", LuaValue.valueOf(QUEST_EXEC_NOTIFY_GROUP_LUA));
			scriptConstQuest.set("QUEST_EXEC_SET_OPEN_STATE", LuaValue.valueOf(QUEST_EXEC_SET_OPEN_STATE));
			scriptConstQuest.set("QUEST_EXEC_LOCK_POINT", LuaValue.valueOf(QUEST_EXEC_LOCK_POINT));
			scriptConstQuest.set("QUEST_EXEC_DEL_PACK_ITEM_BATCH", LuaValue.valueOf(QUEST_EXEC_DEL_PACK_ITEM_BATCH));
			scriptConstQuest.set("QUEST_EXEC_REFRESH_GROUP_SUITE", LuaValue.valueOf(QUEST_EXEC_REFRESH_GROUP_SUITE));
			scriptConstQuest.set("QUEST_EXEC_REMOVE_TRIAL_AVATAR", LuaValue.valueOf(QUEST_EXEC_REMOVE_TRIAL_AVATAR));
			scriptConstQuest.set("QUEST_EXEC_SET_GAME_TIME", LuaValue.valueOf(QUEST_EXEC_SET_GAME_TIME));
			scriptConstQuest.set("QUEST_EXEC_SET_WEATHER_GADGET", LuaValue.valueOf(QUEST_EXEC_SET_WEATHER_GADGET));
			scriptConstQuest.set("QUEST_EXEC_ADD_QUEST_PROGRESS", LuaValue.valueOf(QUEST_EXEC_ADD_QUEST_PROGRESS));
			scriptConstQuest.set("QUEST_EXEC_NOTIFY_DAILY_TASK", LuaValue.valueOf(QUEST_EXEC_NOTIFY_DAILY_TASK));
			scriptConstQuest.set("QUEST_EXEC_CREATE_PATTERN_GROUP", LuaValue.valueOf(QUEST_EXEC_CREATE_PATTERN_GROUP));
			scriptConstQuest.set("QUEST_EXEC_REMOVE_PATTERN_GROUP", LuaValue.valueOf(QUEST_EXEC_REMOVE_PATTERN_GROUP));
			scriptConstQuest.set("QUEST_EXEC_REFRESH_GROUP_SUITE_RANDOM", LuaValue.valueOf(QUEST_EXEC_REFRESH_GROUP_SUITE_RANDOM));
			scriptConstQuest.set("QUEST_EXEC_ACTIVE_ITEM_GIVING", LuaValue.valueOf(QUEST_EXEC_ACTIVE_ITEM_GIVING));
			scriptConstQuest.set("QUEST_EXEC_DEL_ALL_SPECIFIC_PACK_ITEM", LuaValue.valueOf(QUEST_EXEC_DEL_ALL_SPECIFIC_PACK_ITEM));
			scriptConstQuest.set("QUEST_EXEC_ROLLBACK_PARENT_QUEST", LuaValue.valueOf(QUEST_EXEC_ROLLBACK_PARENT_QUEST));
			scriptConstQuest.set("QUEST_EXEC_LOCK_AVATAR_TEAM", LuaValue.valueOf(QUEST_EXEC_LOCK_AVATAR_TEAM));
			scriptConstQuest.set("QUEST_EXEC_UNLOCK_AVATAR_TEAM", LuaValue.valueOf(QUEST_EXEC_UNLOCK_AVATAR_TEAM));
			scriptConstQuest.set("QUEST_EXEC_UPDATE_QUEST_VAR", LuaValue.valueOf(QUEST_EXEC_UPDATE_QUEST_VAR));
			scriptConstQuest.set("QUEST_EXEC_UPDATE_PARENT_QUEST_REWARD_INDEX", LuaValue.valueOf(QUEST_EXEC_UPDATE_PARENT_QUEST_REWARD_INDEX));
			env.set("QuestExecType", scriptConstQuest);
			env.get("package").get("loaded").set("QuestExecType", scriptConstQuest);
			return env;
		}
	}
	public static class QuestGuideAuto extends TwoArgFunction {
		public static final int QUEST_GUIDE_AUTO_NONE = 0;
		public static final int QUEST_GUIDE_AUTO_ENABLE = 1;
		public static final int QUEST_GUIDE_AUTO_DISABLE = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstQuest = new LuaTable();
			scriptConstQuest.set("QUEST_GUIDE_AUTO_NONE", LuaValue.valueOf(QUEST_GUIDE_AUTO_NONE));
			scriptConstQuest.set("QUEST_GUIDE_AUTO_ENABLE", LuaValue.valueOf(QUEST_GUIDE_AUTO_ENABLE));
			scriptConstQuest.set("QUEST_GUIDE_AUTO_DISABLE", LuaValue.valueOf(QUEST_GUIDE_AUTO_DISABLE));
			env.set("QuestGuideAuto", scriptConstQuest);
			env.get("package").get("loaded").set("QuestGuideAuto", scriptConstQuest);
			return env;
		}
	}
}
