package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Activity {
	public static class ActivityCondDefaultStateType extends TwoArgFunction {
		public static final int ACTIVITY_COND_DEFAULT_STATE_FALSE = 0;
		public static final int ACTIVITY_COND_DEFAULT_STATE_TRUE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstActivity = new LuaTable();
			scriptConstActivity.set("ACTIVITY_COND_DEFAULT_STATE_FALSE", LuaValue.valueOf(ACTIVITY_COND_DEFAULT_STATE_FALSE));
			scriptConstActivity.set("ACTIVITY_COND_DEFAULT_STATE_TRUE", LuaValue.valueOf(ACTIVITY_COND_DEFAULT_STATE_TRUE));
			env.set("ActivityCondDefaultStateType", scriptConstActivity);
			env.get("package").get("loaded").set("ActivityCondDefaultStateType", scriptConstActivity);
			return env;
		}
	}
	public static class ActivityCondType extends TwoArgFunction {
		public static final int ACTIVITY_COND_NONE = 0;
		public static final int ACTIVITY_COND_TIME_GREATER = 1;
		public static final int ACTIVITY_COND_TIME_LESS = 2;
		public static final int ACTIVITY_COND_PLAYER_LEVEL_GREATER = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstActivity = new LuaTable();
			scriptConstActivity.set("ACTIVITY_COND_NONE", LuaValue.valueOf(ACTIVITY_COND_NONE));
			scriptConstActivity.set("ACTIVITY_COND_TIME_GREATER", LuaValue.valueOf(ACTIVITY_COND_TIME_GREATER));
			scriptConstActivity.set("ACTIVITY_COND_TIME_LESS", LuaValue.valueOf(ACTIVITY_COND_TIME_LESS));
			scriptConstActivity.set("ACTIVITY_COND_PLAYER_LEVEL_GREATER", LuaValue.valueOf(ACTIVITY_COND_PLAYER_LEVEL_GREATER));
			env.set("ActivityCondType", scriptConstActivity);
			env.get("package").get("loaded").set("ActivityCondType", scriptConstActivity);
			return env;
		}
	}
	public static class ActivityDropType extends TwoArgFunction {
		public static final int ACTIVITY_DROP_TYPE_NONE = 0;
		public static final int ACTIVITY_DROP_TYPE_MONSTER_ID = 1;
		public static final int ACTIVITY_DROP_TYPE_DROP_TAG = 2;
		public static final int ACTIVITY_DROP_TYPE_MONSTER_TAG = 3;
		public static final int ACTIVITY_DROP_TYPE_GATHER = 4;
		public static final int ACTIVITY_DROP_TYPE_SUB_FIELD_DROP = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstActivity = new LuaTable();
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_NONE", LuaValue.valueOf(ACTIVITY_DROP_TYPE_NONE));
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_MONSTER_ID", LuaValue.valueOf(ACTIVITY_DROP_TYPE_MONSTER_ID));
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_DROP_TAG", LuaValue.valueOf(ACTIVITY_DROP_TYPE_DROP_TAG));
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_MONSTER_TAG", LuaValue.valueOf(ACTIVITY_DROP_TYPE_MONSTER_TAG));
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_GATHER", LuaValue.valueOf(ACTIVITY_DROP_TYPE_GATHER));
			scriptConstActivity.set("ACTIVITY_DROP_TYPE_SUB_FIELD_DROP", LuaValue.valueOf(ACTIVITY_DROP_TYPE_SUB_FIELD_DROP));
			env.set("ActivityDropType", scriptConstActivity);
			env.get("package").get("loaded").set("ActivityDropType", scriptConstActivity);
			return env;
		}
	}
	public static class ActivityType extends TwoArgFunction {
		public static final int ACTIVITY_GENERAL = 0;
		public static final int ACTIVITY_SEA_LAMP = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstActivity = new LuaTable();
			scriptConstActivity.set("ACTIVITY_GENERAL", LuaValue.valueOf(ACTIVITY_GENERAL));
			scriptConstActivity.set("ACTIVITY_SEA_LAMP", LuaValue.valueOf(ACTIVITY_SEA_LAMP));
			env.set("ActivityType", scriptConstActivity);
			env.get("package").get("loaded").set("ActivityType", scriptConstActivity);
			return env;
		}
	}
	public static class ActivityActionType extends TwoArgFunction {
		public static final int ACTIVITY_ACTION_NONE = 0;
		public static final int ACTIVITY_ACTION_UNLOCK_POINT = 1;
		public static final int ACTIVITY_ACTION_LOCK_POINT = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstActivity = new LuaTable();
			scriptConstActivity.set("ACTIVITY_ACTION_NONE", LuaValue.valueOf(ACTIVITY_ACTION_NONE));
			scriptConstActivity.set("ACTIVITY_ACTION_UNLOCK_POINT", LuaValue.valueOf(ACTIVITY_ACTION_UNLOCK_POINT));
			scriptConstActivity.set("ACTIVITY_ACTION_LOCK_POINT", LuaValue.valueOf(ACTIVITY_ACTION_LOCK_POINT));
			env.set("ActivityActionType", scriptConstActivity);
			env.get("package").get("loaded").set("ActivityActionType", scriptConstActivity);
			return env;
		}
	}
}
