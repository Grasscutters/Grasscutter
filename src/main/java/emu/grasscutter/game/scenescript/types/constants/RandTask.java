package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class RandTask {
	public static class RandTaskType extends TwoArgFunction {
		public static final int RAND_TASK_QUEST = 0;
		public static final int RAND_TASK_SCENE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstRandTask = new LuaTable();
			scriptConstRandTask.set("RAND_TASK_QUEST", LuaValue.valueOf(RAND_TASK_QUEST));
			scriptConstRandTask.set("RAND_TASK_SCENE", LuaValue.valueOf(RAND_TASK_SCENE));
			env.set("RandTaskType", scriptConstRandTask);
			env.get("package").get("loaded").set("RandTaskType", scriptConstRandTask);
			return env;
		}
	}
}
