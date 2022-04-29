package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Gadget {
	public static class InteractActionType extends TwoArgFunction {
		public static final int INTERACT_ACTION_NONE = 0;
		public static final int INTERACT_ACTION_STATE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGadget = new LuaTable();
			scriptConstGadget.set("INTERACT_ACTION_NONE", LuaValue.valueOf(INTERACT_ACTION_NONE));
			scriptConstGadget.set("INTERACT_ACTION_STATE", LuaValue.valueOf(INTERACT_ACTION_STATE));
			env.set("InteractActionType", scriptConstGadget);
			env.get("package").get("loaded").set("InteractActionType", scriptConstGadget);
			return env;
		}
	}
}
