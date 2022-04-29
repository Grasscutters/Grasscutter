package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class CommonScript {
	public static class EventType extends TwoArgFunction {
		public static final int EVENT_NONE = 0;
		public static final int EVENT_ANY_MONSTER_DIE = 1;
		public static final int EVENT_ANY_GADGET_DIE = 2;
		public static final int EVENT_VARIABLE_CHANGE = 3;
		public static final int EVENT_ENTER_REGION = 4;
		public static final int EVENT_LEAVE_REGION = 5;
		public static final int EVENT_GADGET_CREATE = 6;
		public static final int EVENT_GADGET_STATE_CHANGE = 7;
		public static final int EVENT_DUNGEON_SETTLE = 8;
		public static final int EVENT_SELECT_OPTION = 9;
		public static final int EVENT_CLIENT_EXECUTE = 10;
		public static final int EVENT_ANY_MONSTER_LIVE = 11;
		public static final int EVENT_SPECIFIC_MONSTER_HP_CHANGE = 12;
		public static final int EVENT_CITY_LEVELUP_UNLOCK_DUNGEON_ENTRY = 13;
		public static final int EVENT_DUNGEON_BROADCAST_ONTIMER = 14;
		public static final int EVENT_TIMER_EVENT = 15;
		public static final int EVENT_CHALLENGE_SUCCESS = 16;
		public static final int EVENT_CHALLENGE_FAIL = 17;
		public static final int EVENT_SEAL_BATTLE_BEGIN = 18;
		public static final int EVENT_SEAL_BATTLE_END = 19;
		public static final int EVENT_GATHER = 20;
		public static final int EVENT_QUEST_FINISH = 21;
		public static final int EVENT_MONSTER_BATTLE = 22;
		public static final int EVENT_CITY_LEVELUP = 23;
		public static final int EVENT_CUTSCENE_END = 24;
		public static final int EVENT_AVATAR_NEAR_PLATFORM = 25;
		public static final int EVENT_PLATFORM_REACH_POINT = 26;
		public static final int EVENT_UNLOCK_TRANS_POINT = 27;
		public static final int EVENT_QUEST_START = 28;
		public static final int EVENT_GROUP_LOAD = 29;
		public static final int EVENT_GROUP_REFRESH = 30;
		public static final int EVENT_DUNGEON_REWARD_GET = 31;
		public static final int EVENT_SPECIFIC_GADGET_HP_CHANGE = 32;
		public static final int EVENT_MONSTER_TIDE_OVER = 33;
		public static final int EVENT_MONSTER_TIDE_CREATE = 34;
		public static final int EVENT_MONSTER_TIDE_DIE = 35;
		public static final int EVENT_SEALAMP_PHASE_CHANGE = 36;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommonScript = new LuaTable();
			scriptConstCommonScript.set("EVENT_NONE", LuaValue.valueOf(EVENT_NONE));
			scriptConstCommonScript.set("EVENT_ANY_MONSTER_DIE", LuaValue.valueOf(EVENT_ANY_MONSTER_DIE));
			scriptConstCommonScript.set("EVENT_ANY_GADGET_DIE", LuaValue.valueOf(EVENT_ANY_GADGET_DIE));
			scriptConstCommonScript.set("EVENT_VARIABLE_CHANGE", LuaValue.valueOf(EVENT_VARIABLE_CHANGE));
			scriptConstCommonScript.set("EVENT_ENTER_REGION", LuaValue.valueOf(EVENT_ENTER_REGION));
			scriptConstCommonScript.set("EVENT_LEAVE_REGION", LuaValue.valueOf(EVENT_LEAVE_REGION));
			scriptConstCommonScript.set("EVENT_GADGET_CREATE", LuaValue.valueOf(EVENT_GADGET_CREATE));
			scriptConstCommonScript.set("EVENT_GADGET_STATE_CHANGE", LuaValue.valueOf(EVENT_GADGET_STATE_CHANGE));
			scriptConstCommonScript.set("EVENT_DUNGEON_SETTLE", LuaValue.valueOf(EVENT_DUNGEON_SETTLE));
			scriptConstCommonScript.set("EVENT_SELECT_OPTION", LuaValue.valueOf(EVENT_SELECT_OPTION));
			scriptConstCommonScript.set("EVENT_CLIENT_EXECUTE", LuaValue.valueOf(EVENT_CLIENT_EXECUTE));
			scriptConstCommonScript.set("EVENT_ANY_MONSTER_LIVE", LuaValue.valueOf(EVENT_ANY_MONSTER_LIVE));
			scriptConstCommonScript.set("EVENT_SPECIFIC_MONSTER_HP_CHANGE", LuaValue.valueOf(EVENT_SPECIFIC_MONSTER_HP_CHANGE));
			scriptConstCommonScript.set("EVENT_CITY_LEVELUP_UNLOCK_DUNGEON_ENTRY", LuaValue.valueOf(EVENT_CITY_LEVELUP_UNLOCK_DUNGEON_ENTRY));
			scriptConstCommonScript.set("EVENT_DUNGEON_BROADCAST_ONTIMER", LuaValue.valueOf(EVENT_DUNGEON_BROADCAST_ONTIMER));
			scriptConstCommonScript.set("EVENT_TIMER_EVENT", LuaValue.valueOf(EVENT_TIMER_EVENT));
			scriptConstCommonScript.set("EVENT_CHALLENGE_SUCCESS", LuaValue.valueOf(EVENT_CHALLENGE_SUCCESS));
			scriptConstCommonScript.set("EVENT_CHALLENGE_FAIL", LuaValue.valueOf(EVENT_CHALLENGE_FAIL));
			scriptConstCommonScript.set("EVENT_SEAL_BATTLE_BEGIN", LuaValue.valueOf(EVENT_SEAL_BATTLE_BEGIN));
			scriptConstCommonScript.set("EVENT_SEAL_BATTLE_END", LuaValue.valueOf(EVENT_SEAL_BATTLE_END));
			scriptConstCommonScript.set("EVENT_GATHER", LuaValue.valueOf(EVENT_GATHER));
			scriptConstCommonScript.set("EVENT_QUEST_FINISH", LuaValue.valueOf(EVENT_QUEST_FINISH));
			scriptConstCommonScript.set("EVENT_MONSTER_BATTLE", LuaValue.valueOf(EVENT_MONSTER_BATTLE));
			scriptConstCommonScript.set("EVENT_CITY_LEVELUP", LuaValue.valueOf(EVENT_CITY_LEVELUP));
			scriptConstCommonScript.set("EVENT_CUTSCENE_END", LuaValue.valueOf(EVENT_CUTSCENE_END));
			scriptConstCommonScript.set("EVENT_AVATAR_NEAR_PLATFORM", LuaValue.valueOf(EVENT_AVATAR_NEAR_PLATFORM));
			scriptConstCommonScript.set("EVENT_PLATFORM_REACH_POINT", LuaValue.valueOf(EVENT_PLATFORM_REACH_POINT));
			scriptConstCommonScript.set("EVENT_UNLOCK_TRANS_POINT", LuaValue.valueOf(EVENT_UNLOCK_TRANS_POINT));
			scriptConstCommonScript.set("EVENT_QUEST_START", LuaValue.valueOf(EVENT_QUEST_START));
			scriptConstCommonScript.set("EVENT_GROUP_LOAD", LuaValue.valueOf(EVENT_GROUP_LOAD));
			scriptConstCommonScript.set("EVENT_GROUP_REFRESH", LuaValue.valueOf(EVENT_GROUP_REFRESH));
			scriptConstCommonScript.set("EVENT_DUNGEON_REWARD_GET", LuaValue.valueOf(EVENT_DUNGEON_REWARD_GET));
			scriptConstCommonScript.set("EVENT_SPECIFIC_GADGET_HP_CHANGE", LuaValue.valueOf(EVENT_SPECIFIC_GADGET_HP_CHANGE));
			scriptConstCommonScript.set("EVENT_MONSTER_TIDE_OVER", LuaValue.valueOf(EVENT_MONSTER_TIDE_OVER));
			scriptConstCommonScript.set("EVENT_MONSTER_TIDE_CREATE", LuaValue.valueOf(EVENT_MONSTER_TIDE_CREATE));
			scriptConstCommonScript.set("EVENT_MONSTER_TIDE_DIE", LuaValue.valueOf(EVENT_MONSTER_TIDE_DIE));
			scriptConstCommonScript.set("EVENT_SEALAMP_PHASE_CHANGE", LuaValue.valueOf(EVENT_SEALAMP_PHASE_CHANGE));
			env.set("EventType", scriptConstCommonScript);
			env.get("package").get("loaded").set("EventType", scriptConstCommonScript);
			return env;
		}
	}
	public static class GadgetType extends TwoArgFunction {
		public static final int GADGET_NONE = 0;
		public static final int GADGET_WORLD_CHECT = 1;
		public static final int GADGET_DUNGEON_SECRET_CHEST = 2;
		public static final int GADGET_DUNGEON_PASS_CHEST = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommonScript = new LuaTable();
			scriptConstCommonScript.set("GADGET_NONE", LuaValue.valueOf(GADGET_NONE));
			scriptConstCommonScript.set("GADGET_WORLD_CHECT", LuaValue.valueOf(GADGET_WORLD_CHECT));
			scriptConstCommonScript.set("GADGET_DUNGEON_SECRET_CHEST", LuaValue.valueOf(GADGET_DUNGEON_SECRET_CHEST));
			scriptConstCommonScript.set("GADGET_DUNGEON_PASS_CHEST", LuaValue.valueOf(GADGET_DUNGEON_PASS_CHEST));
			env.set("GadgetType", scriptConstCommonScript);
			env.get("package").get("loaded").set("GadgetType", scriptConstCommonScript);
			return env;
		}
	}
	public static class GroupKillPolicy extends TwoArgFunction {
		public static final int GROUP_KILL_NONE = 0;
		public static final int GROUP_KILL_ALL = 1;
		public static final int GROUP_KILL_MONSTER = 2;
		public static final int GROUP_KILL_GADGET = 3;
		public static final int GROUP_KILL_NPC = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommonScript = new LuaTable();
			scriptConstCommonScript.set("GROUP_KILL_NONE", LuaValue.valueOf(GROUP_KILL_NONE));
			scriptConstCommonScript.set("GROUP_KILL_ALL", LuaValue.valueOf(GROUP_KILL_ALL));
			scriptConstCommonScript.set("GROUP_KILL_MONSTER", LuaValue.valueOf(GROUP_KILL_MONSTER));
			scriptConstCommonScript.set("GROUP_KILL_GADGET", LuaValue.valueOf(GROUP_KILL_GADGET));
			scriptConstCommonScript.set("GROUP_KILL_NPC", LuaValue.valueOf(GROUP_KILL_NPC));
			env.set("GroupKillPolicy", scriptConstCommonScript);
			env.get("package").get("loaded").set("GroupKillPolicy", scriptConstCommonScript);
			return env;
		}
	}
	public static class PlatformRotType extends TwoArgFunction {
		public static final int PLATFORM_ROT_NONE = 0;
		public static final int PLATFORM_ROT_SPEED = 1;
		public static final int PLATFORM_ROT_ROUND = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommonScript = new LuaTable();
			scriptConstCommonScript.set("PLATFORM_ROT_NONE", LuaValue.valueOf(PLATFORM_ROT_NONE));
			scriptConstCommonScript.set("PLATFORM_ROT_SPEED", LuaValue.valueOf(PLATFORM_ROT_SPEED));
			scriptConstCommonScript.set("PLATFORM_ROT_ROUND", LuaValue.valueOf(PLATFORM_ROT_ROUND));
			env.set("PlatformRotType", scriptConstCommonScript);
			env.get("package").get("loaded").set("PlatformRotType", scriptConstCommonScript);
			return env;
		}
	}
	public static class RegionShape extends TwoArgFunction {
		public static final int REGION_NONE = 0;
		public static final int REGION_SPHERE = 1;
		public static final int REGION_CUBIC = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommonScript = new LuaTable();
			scriptConstCommonScript.set("REGION_NONE", LuaValue.valueOf(REGION_NONE));
			scriptConstCommonScript.set("REGION_SPHERE", LuaValue.valueOf(REGION_SPHERE));
			scriptConstCommonScript.set("REGION_CUBIC", LuaValue.valueOf(REGION_CUBIC));
			env.set("RegionShape", scriptConstCommonScript);
			env.get("package").get("loaded").set("RegionShape", scriptConstCommonScript);
			return env;
		}
	}
}
