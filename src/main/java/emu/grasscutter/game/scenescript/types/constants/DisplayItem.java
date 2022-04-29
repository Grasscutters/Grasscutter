package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DisplayItem {
	public static class DisplayItemType extends TwoArgFunction {
		public static final int RELIQUARY_ITEM = 0;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDisplayItem = new LuaTable();
			scriptConstDisplayItem.set("RELIQUARY_ITEM", LuaValue.valueOf(RELIQUARY_ITEM));
			env.set("DisplayItemType", scriptConstDisplayItem);
			env.get("package").get("loaded").set("DisplayItemType", scriptConstDisplayItem);
			return env;
		}
	}
}
