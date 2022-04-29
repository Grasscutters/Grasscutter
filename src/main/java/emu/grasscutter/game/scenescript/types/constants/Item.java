package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Item {
	public static class ItemUseOp extends TwoArgFunction {
		public static final int ITEM_USE_NONE = 0;
		public static final int ITEM_USE_RECOVER_HP = 1;
		public static final int ITEM_USE_ACCEPT_QUEST = 2;
		public static final int ITEM_USE_TRIGGER_ABILITY = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstItem = new LuaTable();
			scriptConstItem.set("ITEM_USE_NONE", LuaValue.valueOf(ITEM_USE_NONE));
			scriptConstItem.set("ITEM_USE_RECOVER_HP", LuaValue.valueOf(ITEM_USE_RECOVER_HP));
			scriptConstItem.set("ITEM_USE_ACCEPT_QUEST", LuaValue.valueOf(ITEM_USE_ACCEPT_QUEST));
			scriptConstItem.set("ITEM_USE_TRIGGER_ABILITY", LuaValue.valueOf(ITEM_USE_TRIGGER_ABILITY));
			env.set("ItemUseOp", scriptConstItem);
			env.get("package").get("loaded").set("ItemUseOp", scriptConstItem);
			return env;
		}
	}
}
