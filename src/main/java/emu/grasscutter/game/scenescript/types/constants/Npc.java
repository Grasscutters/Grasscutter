package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Npc {
	public static class BillboardType extends TwoArgFunction {
		public static final int None = 0;
		public static final int Sneak = 1;
		public static final int Icon = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstNpc = new LuaTable();
			scriptConstNpc.set("None", LuaValue.valueOf(None));
			scriptConstNpc.set("Sneak", LuaValue.valueOf(Sneak));
			scriptConstNpc.set("Icon", LuaValue.valueOf(Icon));
			env.set("BillboardType", scriptConstNpc);
			env.get("package").get("loaded").set("BillboardType", scriptConstNpc);
			return env;
		}
	}
}
