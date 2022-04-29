package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Dungeon {
	public static class InvolveType extends TwoArgFunction {
		public static final int INVOLVE_NONE = 0;
		public static final int INVOLVE_ONLY_SINGLE = 1;
		public static final int INVOLVE_SINGLE_MULTIPLE = 2;
		public static final int INVOLVE_DYNAMIC_MULTIPLE = 3;
		public static final int INVOLVE_ONLY_MULTIPLE = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("INVOLVE_NONE", LuaValue.valueOf(INVOLVE_NONE));
			scriptConstDungeon.set("INVOLVE_ONLY_SINGLE", LuaValue.valueOf(INVOLVE_ONLY_SINGLE));
			scriptConstDungeon.set("INVOLVE_SINGLE_MULTIPLE", LuaValue.valueOf(INVOLVE_SINGLE_MULTIPLE));
			scriptConstDungeon.set("INVOLVE_DYNAMIC_MULTIPLE", LuaValue.valueOf(INVOLVE_DYNAMIC_MULTIPLE));
			scriptConstDungeon.set("INVOLVE_ONLY_MULTIPLE", LuaValue.valueOf(INVOLVE_ONLY_MULTIPLE));
			env.set("InvolveType", scriptConstDungeon);
			env.get("package").get("loaded").set("InvolveType", scriptConstDungeon);
			return env;
		}
	}
	public static class SettleShowType extends TwoArgFunction {
		public static final int SETTLE_SHOW_NONE = 0;
		public static final int SETTLE_SHOW_TIME_COST = 1;
		public static final int SETTLE_SHOW_OPEN_CHEST_COUNT = 2;
		public static final int SETTLE_SHOW_KILL_MONSTER_COUNT = 3;
		public static final int SETTLE_SHOW_BLACKSCREEN = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("SETTLE_SHOW_NONE", LuaValue.valueOf(SETTLE_SHOW_NONE));
			scriptConstDungeon.set("SETTLE_SHOW_TIME_COST", LuaValue.valueOf(SETTLE_SHOW_TIME_COST));
			scriptConstDungeon.set("SETTLE_SHOW_OPEN_CHEST_COUNT", LuaValue.valueOf(SETTLE_SHOW_OPEN_CHEST_COUNT));
			scriptConstDungeon.set("SETTLE_SHOW_KILL_MONSTER_COUNT", LuaValue.valueOf(SETTLE_SHOW_KILL_MONSTER_COUNT));
			scriptConstDungeon.set("SETTLE_SHOW_BLACKSCREEN", LuaValue.valueOf(SETTLE_SHOW_BLACKSCREEN));
			env.set("SettleShowType", scriptConstDungeon);
			env.get("package").get("loaded").set("SettleShowType", scriptConstDungeon);
			return env;
		}
	}
	public static class SettleUIType extends TwoArgFunction {
		public static final int SETTLE_UI_AlWAYS_SHOW = 0;
		public static final int SETTLE_UI_ON_SUCCESS = 1;
		public static final int SETTLE_UI_ON_FAIL = 2;
		public static final int SETTLE_UI_NEVER_SHOW = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("SETTLE_UI_AlWAYS_SHOW", LuaValue.valueOf(SETTLE_UI_AlWAYS_SHOW));
			scriptConstDungeon.set("SETTLE_UI_ON_SUCCESS", LuaValue.valueOf(SETTLE_UI_ON_SUCCESS));
			scriptConstDungeon.set("SETTLE_UI_ON_FAIL", LuaValue.valueOf(SETTLE_UI_ON_FAIL));
			scriptConstDungeon.set("SETTLE_UI_NEVER_SHOW", LuaValue.valueOf(SETTLE_UI_NEVER_SHOW));
			env.set("SettleUIType", scriptConstDungeon);
			env.get("package").get("loaded").set("SettleUIType", scriptConstDungeon);
			return env;
		}
	}
	public static class ChallengeCondType extends TwoArgFunction {
		public static final int CHALLENGE_COND_NONE = 0;
		public static final int CHALLENGE_COND_IN_TIME = 1;
		public static final int CHALLENGE_COND_ALL_TIME = 2;
		public static final int CHALLENGE_COND_KILL_COUNT = 3;
		public static final int CHALLENGE_COND_SURVIVE = 4;
		public static final int CHALLENGE_COND_TIME_INC = 5;
		public static final int CHALLENGE_COND_KILL_FAST = 6;
		public static final int CHALLENGE_COND_DOWN_LESS = 7;
		public static final int CHALLENGE_COND_BEATEN_LESS = 8;
		public static final int CHALLENGE_COND_UNNATURAL_COUNT = 9;
		public static final int CHALLENGE_COND_FROZEN_LESS = 10;
		public static final int CHALLENGE_COND_KILL_MONSTER = 11;
		public static final int CHALLENGE_COND_TRIGGER = 12;
		public static final int CHALLENGE_COND_GUARD_HP = 13;
		public static final int CHALLENGE_COND_TIME_DEC = 14;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("CHALLENGE_COND_NONE", LuaValue.valueOf(CHALLENGE_COND_NONE));
			scriptConstDungeon.set("CHALLENGE_COND_IN_TIME", LuaValue.valueOf(CHALLENGE_COND_IN_TIME));
			scriptConstDungeon.set("CHALLENGE_COND_ALL_TIME", LuaValue.valueOf(CHALLENGE_COND_ALL_TIME));
			scriptConstDungeon.set("CHALLENGE_COND_KILL_COUNT", LuaValue.valueOf(CHALLENGE_COND_KILL_COUNT));
			scriptConstDungeon.set("CHALLENGE_COND_SURVIVE", LuaValue.valueOf(CHALLENGE_COND_SURVIVE));
			scriptConstDungeon.set("CHALLENGE_COND_TIME_INC", LuaValue.valueOf(CHALLENGE_COND_TIME_INC));
			scriptConstDungeon.set("CHALLENGE_COND_KILL_FAST", LuaValue.valueOf(CHALLENGE_COND_KILL_FAST));
			scriptConstDungeon.set("CHALLENGE_COND_DOWN_LESS", LuaValue.valueOf(CHALLENGE_COND_DOWN_LESS));
			scriptConstDungeon.set("CHALLENGE_COND_BEATEN_LESS", LuaValue.valueOf(CHALLENGE_COND_BEATEN_LESS));
			scriptConstDungeon.set("CHALLENGE_COND_UNNATURAL_COUNT", LuaValue.valueOf(CHALLENGE_COND_UNNATURAL_COUNT));
			scriptConstDungeon.set("CHALLENGE_COND_FROZEN_LESS", LuaValue.valueOf(CHALLENGE_COND_FROZEN_LESS));
			scriptConstDungeon.set("CHALLENGE_COND_KILL_MONSTER", LuaValue.valueOf(CHALLENGE_COND_KILL_MONSTER));
			scriptConstDungeon.set("CHALLENGE_COND_TRIGGER", LuaValue.valueOf(CHALLENGE_COND_TRIGGER));
			scriptConstDungeon.set("CHALLENGE_COND_GUARD_HP", LuaValue.valueOf(CHALLENGE_COND_GUARD_HP));
			scriptConstDungeon.set("CHALLENGE_COND_TIME_DEC", LuaValue.valueOf(CHALLENGE_COND_TIME_DEC));
			env.set("ChallengeCondType", scriptConstDungeon);
			env.get("package").get("loaded").set("ChallengeCondType", scriptConstDungeon);
			return env;
		}
	}
	public static class ChallengeRecordType extends TwoArgFunction {
		public static final int CHALLENGE_RECORD_TYPE_NONE = 0;
		public static final int CHALLENGE_RECORD_TYPE_IN_TIME = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("CHALLENGE_RECORD_TYPE_NONE", LuaValue.valueOf(CHALLENGE_RECORD_TYPE_NONE));
			scriptConstDungeon.set("CHALLENGE_RECORD_TYPE_IN_TIME", LuaValue.valueOf(CHALLENGE_RECORD_TYPE_IN_TIME));
			env.set("ChallengeRecordType", scriptConstDungeon);
			env.get("package").get("loaded").set("ChallengeRecordType", scriptConstDungeon);
			return env;
		}
	}
	public static class ChallengeType extends TwoArgFunction {
		public static final int CHALLENGE_NONE = 0;
		public static final int CHALLENGE_KILL_COUNT = 1;
		public static final int CHALLENGE_KILL_COUNT_IN_TIME = 2;
		public static final int CHALLENGE_SURVIVE = 3;
		public static final int CHALLENGE_TIME_FLY = 4;
		public static final int CHALLENGE_KILL_COUNT_FAST = 5;
		public static final int CHALLENGE_KILL_COUNT_FROZEN_LESS = 6;
		public static final int CHALLENGE_KILL_MONSTER_IN_TIME = 7;
		public static final int CHALLENGE_TRIGGER_IN_TIME = 8;
		public static final int CHALLENGE_GUARD_HP = 9;
		public static final int CHALLENGE_KILL_COUNT_GUARD_HP = 10;
		public static final int CHALLENGE_TRIGGER_IN_TIME_FLY = 11;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("CHALLENGE_NONE", LuaValue.valueOf(CHALLENGE_NONE));
			scriptConstDungeon.set("CHALLENGE_KILL_COUNT", LuaValue.valueOf(CHALLENGE_KILL_COUNT));
			scriptConstDungeon.set("CHALLENGE_KILL_COUNT_IN_TIME", LuaValue.valueOf(CHALLENGE_KILL_COUNT_IN_TIME));
			scriptConstDungeon.set("CHALLENGE_SURVIVE", LuaValue.valueOf(CHALLENGE_SURVIVE));
			scriptConstDungeon.set("CHALLENGE_TIME_FLY", LuaValue.valueOf(CHALLENGE_TIME_FLY));
			scriptConstDungeon.set("CHALLENGE_KILL_COUNT_FAST", LuaValue.valueOf(CHALLENGE_KILL_COUNT_FAST));
			scriptConstDungeon.set("CHALLENGE_KILL_COUNT_FROZEN_LESS", LuaValue.valueOf(CHALLENGE_KILL_COUNT_FROZEN_LESS));
			scriptConstDungeon.set("CHALLENGE_KILL_MONSTER_IN_TIME", LuaValue.valueOf(CHALLENGE_KILL_MONSTER_IN_TIME));
			scriptConstDungeon.set("CHALLENGE_TRIGGER_IN_TIME", LuaValue.valueOf(CHALLENGE_TRIGGER_IN_TIME));
			scriptConstDungeon.set("CHALLENGE_GUARD_HP", LuaValue.valueOf(CHALLENGE_GUARD_HP));
			scriptConstDungeon.set("CHALLENGE_KILL_COUNT_GUARD_HP", LuaValue.valueOf(CHALLENGE_KILL_COUNT_GUARD_HP));
			scriptConstDungeon.set("CHALLENGE_TRIGGER_IN_TIME_FLY", LuaValue.valueOf(CHALLENGE_TRIGGER_IN_TIME_FLY));
			env.set("ChallengeType", scriptConstDungeon);
			env.get("package").get("loaded").set("ChallengeType", scriptConstDungeon);
			return env;
		}
	}
	public static class CycleDungeonType extends TwoArgFunction {
		public static final int CYCLE_DUNGEON_NONE = 0;
		public static final int CYCLE_DUNGEON_DVALIN = 1;
		public static final int CYCLE_DUNGEON_AVATAR_EXP = 2;
		public static final int CYCLE_DUNGEON_AVATAR_SKILL = 3;
		public static final int CYCLE_DUNGEON_WEAPON_PROMOTE = 4;
		public static final int CYCLE_DUNGEON_RELIQUARY = 5;
		public static final int CYCLE_DUNGEON_SCOIN = 6;
		public static final int CYCLE_DUNGEON_WEEKLY = 7;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("CYCLE_DUNGEON_NONE", LuaValue.valueOf(CYCLE_DUNGEON_NONE));
			scriptConstDungeon.set("CYCLE_DUNGEON_DVALIN", LuaValue.valueOf(CYCLE_DUNGEON_DVALIN));
			scriptConstDungeon.set("CYCLE_DUNGEON_AVATAR_EXP", LuaValue.valueOf(CYCLE_DUNGEON_AVATAR_EXP));
			scriptConstDungeon.set("CYCLE_DUNGEON_AVATAR_SKILL", LuaValue.valueOf(CYCLE_DUNGEON_AVATAR_SKILL));
			scriptConstDungeon.set("CYCLE_DUNGEON_WEAPON_PROMOTE", LuaValue.valueOf(CYCLE_DUNGEON_WEAPON_PROMOTE));
			scriptConstDungeon.set("CYCLE_DUNGEON_RELIQUARY", LuaValue.valueOf(CYCLE_DUNGEON_RELIQUARY));
			scriptConstDungeon.set("CYCLE_DUNGEON_SCOIN", LuaValue.valueOf(CYCLE_DUNGEON_SCOIN));
			scriptConstDungeon.set("CYCLE_DUNGEON_WEEKLY", LuaValue.valueOf(CYCLE_DUNGEON_WEEKLY));
			env.set("CycleDungeonType", scriptConstDungeon);
			env.get("package").get("loaded").set("CycleDungeonType", scriptConstDungeon);
			return env;
		}
	}
	public static class DungeonCondType extends TwoArgFunction {
		public static final int DUNGEON_COND_NONE = 0;
		public static final int DUNGEON_COND_KILL_MONSTER = 3;
		public static final int DUNGEON_COND_KILL_GROUP_MONSTER = 5;
		public static final int DUNGEON_COND_KILL_TYPE_MONSTER = 7;
		public static final int DUNGEON_COND_FINISH_QUEST = 9;
		public static final int DUNGEON_COND_KILL_MONSTER_COUNT = 11;
		public static final int DUNGEON_COND_IN_TIME = 13;
		public static final int DUNGEON_COND_FINISH_CHALLENGE = 14;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("DUNGEON_COND_NONE", LuaValue.valueOf(DUNGEON_COND_NONE));
			scriptConstDungeon.set("DUNGEON_COND_KILL_MONSTER", LuaValue.valueOf(DUNGEON_COND_KILL_MONSTER));
			scriptConstDungeon.set("DUNGEON_COND_KILL_GROUP_MONSTER", LuaValue.valueOf(DUNGEON_COND_KILL_GROUP_MONSTER));
			scriptConstDungeon.set("DUNGEON_COND_KILL_TYPE_MONSTER", LuaValue.valueOf(DUNGEON_COND_KILL_TYPE_MONSTER));
			scriptConstDungeon.set("DUNGEON_COND_FINISH_QUEST", LuaValue.valueOf(DUNGEON_COND_FINISH_QUEST));
			scriptConstDungeon.set("DUNGEON_COND_KILL_MONSTER_COUNT", LuaValue.valueOf(DUNGEON_COND_KILL_MONSTER_COUNT));
			scriptConstDungeon.set("DUNGEON_COND_IN_TIME", LuaValue.valueOf(DUNGEON_COND_IN_TIME));
			scriptConstDungeon.set("DUNGEON_COND_FINISH_CHALLENGE", LuaValue.valueOf(DUNGEON_COND_FINISH_CHALLENGE));
			env.set("DungeonCondType", scriptConstDungeon);
			env.get("package").get("loaded").set("DungeonCondType", scriptConstDungeon);
			return env;
		}
	}
	public static class DungeonType extends TwoArgFunction {
		public static final int DUNGEON_NONE = 0;
		public static final int DUNGEON_PLOT = 1;
		public static final int DUNGEON_FIGHT = 2;
		public static final int DUNGEON_DAILY_FIGHT = 3;
		public static final int DUNGEON_WEEKLY_FIGHT = 4;
		public static final int DUNGEON_DISCARDED = 5;
		public static final int DUNGEON_TOWER = 6;
		public static final int DUNGEON_BOSS = 7;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeon = new LuaTable();
			scriptConstDungeon.set("DUNGEON_NONE", LuaValue.valueOf(DUNGEON_NONE));
			scriptConstDungeon.set("DUNGEON_PLOT", LuaValue.valueOf(DUNGEON_PLOT));
			scriptConstDungeon.set("DUNGEON_FIGHT", LuaValue.valueOf(DUNGEON_FIGHT));
			scriptConstDungeon.set("DUNGEON_DAILY_FIGHT", LuaValue.valueOf(DUNGEON_DAILY_FIGHT));
			scriptConstDungeon.set("DUNGEON_WEEKLY_FIGHT", LuaValue.valueOf(DUNGEON_WEEKLY_FIGHT));
			scriptConstDungeon.set("DUNGEON_DISCARDED", LuaValue.valueOf(DUNGEON_DISCARDED));
			scriptConstDungeon.set("DUNGEON_TOWER", LuaValue.valueOf(DUNGEON_TOWER));
			scriptConstDungeon.set("DUNGEON_BOSS", LuaValue.valueOf(DUNGEON_BOSS));
			env.set("DungeonType", scriptConstDungeon);
			env.get("package").get("loaded").set("DungeonType", scriptConstDungeon);
			return env;
		}
	}
}
