package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Tower {
	public static class TowerBuffLastingType extends TwoArgFunction {
		public static final int TOWER_BUFF_LASTING_NONE = 0;
		public static final int TOWER_BUFF_LASTING_FLOOR = 1;
		public static final int TOWER_BUFF_LASTING_IMMEDIATE = 2;
		public static final int TOWER_BUFF_LASTING_LEVEL = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstTower = new LuaTable();
			scriptConstTower.set("TOWER_BUFF_LASTING_NONE", LuaValue.valueOf(TOWER_BUFF_LASTING_NONE));
			scriptConstTower.set("TOWER_BUFF_LASTING_FLOOR", LuaValue.valueOf(TOWER_BUFF_LASTING_FLOOR));
			scriptConstTower.set("TOWER_BUFF_LASTING_IMMEDIATE", LuaValue.valueOf(TOWER_BUFF_LASTING_IMMEDIATE));
			scriptConstTower.set("TOWER_BUFF_LASTING_LEVEL", LuaValue.valueOf(TOWER_BUFF_LASTING_LEVEL));
			env.set("TowerBuffLastingType", scriptConstTower);
			env.get("package").get("loaded").set("TowerBuffLastingType", scriptConstTower);
			return env;
		}
	}
	public static class TowerCondType extends TwoArgFunction {
		public static final int TOWER_COND_NONE = 0;
		public static final int TOWER_COND_FINISH_TIME_LESS_THAN = 1;
		public static final int TOWER_COND_LEFT_HP_GREATER_THAN = 2;
		public static final int TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstTower = new LuaTable();
			scriptConstTower.set("TOWER_COND_NONE", LuaValue.valueOf(TOWER_COND_NONE));
			scriptConstTower.set("TOWER_COND_FINISH_TIME_LESS_THAN", LuaValue.valueOf(TOWER_COND_FINISH_TIME_LESS_THAN));
			scriptConstTower.set("TOWER_COND_LEFT_HP_GREATER_THAN", LuaValue.valueOf(TOWER_COND_LEFT_HP_GREATER_THAN));
			scriptConstTower.set("TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN", LuaValue.valueOf(TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN));
			env.set("TowerCondType", scriptConstTower);
			env.get("package").get("loaded").set("TowerCondType", scriptConstTower);
			return env;
		}
	}
}
