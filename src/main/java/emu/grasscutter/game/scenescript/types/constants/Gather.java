package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Gather {
	public static class PointLocation extends TwoArgFunction {
		public static final int POINT_GROUND = 0;
		public static final int POINT_AIR = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGather = new LuaTable();
			scriptConstGather.set("POINT_GROUND", LuaValue.valueOf(POINT_GROUND));
			scriptConstGather.set("POINT_AIR", LuaValue.valueOf(POINT_AIR));
			env.set("PointLocation", scriptConstGather);
			env.get("package").get("loaded").set("PointLocation", scriptConstGather);
			return env;
		}
	}
}
