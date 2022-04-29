package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Buff {
	public static class BuffStackType extends TwoArgFunction {
		public static final int BUFF_REFRESH = 0;
		public static final int BUFF_EXTEND = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstBuff = new LuaTable();
			scriptConstBuff.set("BUFF_REFRESH", LuaValue.valueOf(BUFF_REFRESH));
			scriptConstBuff.set("BUFF_EXTEND", LuaValue.valueOf(BUFF_EXTEND));
			env.set("BuffStackType", scriptConstBuff);
			env.get("package").get("loaded").set("BuffStackType", scriptConstBuff);
			return env;
		}
	}
}
