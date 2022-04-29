package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Compound {
	public static class CompoundType extends TwoArgFunction {
		public static final int COMPOUND_NONE = 0;
		public static final int COMPOUND_COOK = 1;
		public static final int COMPOUND_PLACEHOLDER_2 = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCompound = new LuaTable();
			scriptConstCompound.set("COMPOUND_NONE", LuaValue.valueOf(COMPOUND_NONE));
			scriptConstCompound.set("COMPOUND_COOK", LuaValue.valueOf(COMPOUND_COOK));
			scriptConstCompound.set("COMPOUND_PLACEHOLDER_2", LuaValue.valueOf(COMPOUND_PLACEHOLDER_2));
			env.set("CompoundType", scriptConstCompound);
			env.get("package").get("loaded").set("CompoundType", scriptConstCompound);
			return env;
		}
	}
}
