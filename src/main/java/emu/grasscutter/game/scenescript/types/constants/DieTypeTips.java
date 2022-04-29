package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DieTypeTips {
	public static class PlayerDieType extends TwoArgFunction {
		public static final int PLAYER_DIE_NONE = 0;
		public static final int PLAYER_DIE_KILL_BY_MONSTER = 1;
		public static final int PLAYER_DIE_KILL_BY_GEAR = 2;
		public static final int PLAYER_DIE_FALL = 3;
		public static final int PLAYER_DIE_DRAWN = 4;
		public static final int PLAYER_DIE_ABYSS = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDieTypeTips = new LuaTable();
			scriptConstDieTypeTips.set("PLAYER_DIE_NONE", LuaValue.valueOf(PLAYER_DIE_NONE));
			scriptConstDieTypeTips.set("PLAYER_DIE_KILL_BY_MONSTER", LuaValue.valueOf(PLAYER_DIE_KILL_BY_MONSTER));
			scriptConstDieTypeTips.set("PLAYER_DIE_KILL_BY_GEAR", LuaValue.valueOf(PLAYER_DIE_KILL_BY_GEAR));
			scriptConstDieTypeTips.set("PLAYER_DIE_FALL", LuaValue.valueOf(PLAYER_DIE_FALL));
			scriptConstDieTypeTips.set("PLAYER_DIE_DRAWN", LuaValue.valueOf(PLAYER_DIE_DRAWN));
			scriptConstDieTypeTips.set("PLAYER_DIE_ABYSS", LuaValue.valueOf(PLAYER_DIE_ABYSS));
			env.set("PlayerDieType", scriptConstDieTypeTips);
			env.get("package").get("loaded").set("PlayerDieType", scriptConstDieTypeTips);
			return env;
		}
	}
}
