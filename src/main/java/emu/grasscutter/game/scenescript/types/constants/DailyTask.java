package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DailyTask {
	public static class ConditionType extends TwoArgFunction {
		public static final int CONDITION_NONE = 0;
		public static final int CONDITION_QUEST = 1;
		public static final int CONDITION_PLAYER_LEVEL = 2;
		public static final int CONDITION_VAR_EQ = 3;
		public static final int CONDITION_VAR_NE = 4;
		public static final int CONDITION_VAR_GT = 5;
		public static final int CONDITION_VAR_LT = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDailyTask = new LuaTable();
			scriptConstDailyTask.set("CONDITION_NONE", LuaValue.valueOf(CONDITION_NONE));
			scriptConstDailyTask.set("CONDITION_QUEST", LuaValue.valueOf(CONDITION_QUEST));
			scriptConstDailyTask.set("CONDITION_PLAYER_LEVEL", LuaValue.valueOf(CONDITION_PLAYER_LEVEL));
			scriptConstDailyTask.set("CONDITION_VAR_EQ", LuaValue.valueOf(CONDITION_VAR_EQ));
			scriptConstDailyTask.set("CONDITION_VAR_NE", LuaValue.valueOf(CONDITION_VAR_NE));
			scriptConstDailyTask.set("CONDITION_VAR_GT", LuaValue.valueOf(CONDITION_VAR_GT));
			scriptConstDailyTask.set("CONDITION_VAR_LT", LuaValue.valueOf(CONDITION_VAR_LT));
			env.set("ConditionType", scriptConstDailyTask);
			env.get("package").get("loaded").set("ConditionType", scriptConstDailyTask);
			return env;
		}
	}
	public static class DailyTaskActionType extends TwoArgFunction {
		public static final int DAILY_TASK_ACTION_NONE = 0;
		public static final int DAILY_TASK_ACTION_SET_VAR = 1;
		public static final int DAILY_TASK_ACTION_INC_VAR = 2;
		public static final int DAILY_TASK_ACTION_DEC_VAR = 3;
		public static final int DAILY_TASK_ACTION_ADD_SURE_POOL = 4;
		public static final int DAILY_TASK_ACTION_ADD_POSSIBLE_POOL = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDailyTask = new LuaTable();
			scriptConstDailyTask.set("DAILY_TASK_ACTION_NONE", LuaValue.valueOf(DAILY_TASK_ACTION_NONE));
			scriptConstDailyTask.set("DAILY_TASK_ACTION_SET_VAR", LuaValue.valueOf(DAILY_TASK_ACTION_SET_VAR));
			scriptConstDailyTask.set("DAILY_TASK_ACTION_INC_VAR", LuaValue.valueOf(DAILY_TASK_ACTION_INC_VAR));
			scriptConstDailyTask.set("DAILY_TASK_ACTION_DEC_VAR", LuaValue.valueOf(DAILY_TASK_ACTION_DEC_VAR));
			scriptConstDailyTask.set("DAILY_TASK_ACTION_ADD_SURE_POOL", LuaValue.valueOf(DAILY_TASK_ACTION_ADD_SURE_POOL));
			scriptConstDailyTask.set("DAILY_TASK_ACTION_ADD_POSSIBLE_POOL", LuaValue.valueOf(DAILY_TASK_ACTION_ADD_POSSIBLE_POOL));
			env.set("DailyTaskActionType", scriptConstDailyTask);
			env.get("package").get("loaded").set("DailyTaskActionType", scriptConstDailyTask);
			return env;
		}
	}
	public static class DailyTaskCondType extends TwoArgFunction {
		public static final int DAILY_TASK_COND_NONE = 0;
		public static final int DAILY_TASK_COND_VAR_EQ = 1;
		public static final int DAILY_TASK_COND_VAR_NE = 2;
		public static final int DAILY_TASK_COND_VAR_GT = 3;
		public static final int DAILY_TASK_COND_VAR_LT = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDailyTask = new LuaTable();
			scriptConstDailyTask.set("DAILY_TASK_COND_NONE", LuaValue.valueOf(DAILY_TASK_COND_NONE));
			scriptConstDailyTask.set("DAILY_TASK_COND_VAR_EQ", LuaValue.valueOf(DAILY_TASK_COND_VAR_EQ));
			scriptConstDailyTask.set("DAILY_TASK_COND_VAR_NE", LuaValue.valueOf(DAILY_TASK_COND_VAR_NE));
			scriptConstDailyTask.set("DAILY_TASK_COND_VAR_GT", LuaValue.valueOf(DAILY_TASK_COND_VAR_GT));
			scriptConstDailyTask.set("DAILY_TASK_COND_VAR_LT", LuaValue.valueOf(DAILY_TASK_COND_VAR_LT));
			env.set("DailyTaskCondType", scriptConstDailyTask);
			env.get("package").get("loaded").set("DailyTaskCondType", scriptConstDailyTask);
			return env;
		}
	}
	public static class DailyTaskFinishType extends TwoArgFunction {
		public static final int DAILY_FINISH_NONE = 0;
		public static final int DAILY_FINISH_MONSTER_ID_NUM = 1;
		public static final int DAILY_FINISH_GADGET_ID_NUM = 2;
		public static final int DAILY_FINISH_MONSTER_CONFIG_NUM = 3;
		public static final int DAILY_FINISH_GADGET_CONFIG_NUM = 4;
		public static final int DAILY_FINISH_MONSTER_NUM = 5;
		public static final int DAILY_FINISH_CHEST_CONFIG = 6;
		public static final int DAILY_FINISH_GATHER = 7;
		public static final int DAILY_FINISH_CHALLENGE = 8;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDailyTask = new LuaTable();
			scriptConstDailyTask.set("DAILY_FINISH_NONE", LuaValue.valueOf(DAILY_FINISH_NONE));
			scriptConstDailyTask.set("DAILY_FINISH_MONSTER_ID_NUM", LuaValue.valueOf(DAILY_FINISH_MONSTER_ID_NUM));
			scriptConstDailyTask.set("DAILY_FINISH_GADGET_ID_NUM", LuaValue.valueOf(DAILY_FINISH_GADGET_ID_NUM));
			scriptConstDailyTask.set("DAILY_FINISH_MONSTER_CONFIG_NUM", LuaValue.valueOf(DAILY_FINISH_MONSTER_CONFIG_NUM));
			scriptConstDailyTask.set("DAILY_FINISH_GADGET_CONFIG_NUM", LuaValue.valueOf(DAILY_FINISH_GADGET_CONFIG_NUM));
			scriptConstDailyTask.set("DAILY_FINISH_MONSTER_NUM", LuaValue.valueOf(DAILY_FINISH_MONSTER_NUM));
			scriptConstDailyTask.set("DAILY_FINISH_CHEST_CONFIG", LuaValue.valueOf(DAILY_FINISH_CHEST_CONFIG));
			scriptConstDailyTask.set("DAILY_FINISH_GATHER", LuaValue.valueOf(DAILY_FINISH_GATHER));
			scriptConstDailyTask.set("DAILY_FINISH_CHALLENGE", LuaValue.valueOf(DAILY_FINISH_CHALLENGE));
			env.set("DailyTaskFinishType", scriptConstDailyTask);
			env.get("package").get("loaded").set("DailyTaskFinishType", scriptConstDailyTask);
			return env;
		}
	}
	public static class DailyTaskType extends TwoArgFunction {
		public static final int DAILY_TASK_QUEST = 0;
		public static final int DAILY_TASK_SCENE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDailyTask = new LuaTable();
			scriptConstDailyTask.set("DAILY_TASK_QUEST", LuaValue.valueOf(DAILY_TASK_QUEST));
			scriptConstDailyTask.set("DAILY_TASK_SCENE", LuaValue.valueOf(DAILY_TASK_SCENE));
			env.set("DailyTaskType", scriptConstDailyTask);
			env.get("package").get("loaded").set("DailyTaskType", scriptConstDailyTask);
			return env;
		}
	}
}
