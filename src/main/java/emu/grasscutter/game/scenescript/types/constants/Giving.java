package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Giving {
	public static class BagTab extends TwoArgFunction {
		public static final int TAB_NONE = 0;
		public static final int TAB_WEAPON = 1;
		public static final int TAB_EQUIP = 2;
		public static final int TAB_AVATAR = 3;
		public static final int TAB_FOOD = 4;
		public static final int TAB_MATERIAL = 5;
		public static final int TAB_QUEST = 6;
		public static final int TAB_CONSUME = 7;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGiving = new LuaTable();
			scriptConstGiving.set("TAB_NONE", LuaValue.valueOf(TAB_NONE));
			scriptConstGiving.set("TAB_WEAPON", LuaValue.valueOf(TAB_WEAPON));
			scriptConstGiving.set("TAB_EQUIP", LuaValue.valueOf(TAB_EQUIP));
			scriptConstGiving.set("TAB_AVATAR", LuaValue.valueOf(TAB_AVATAR));
			scriptConstGiving.set("TAB_FOOD", LuaValue.valueOf(TAB_FOOD));
			scriptConstGiving.set("TAB_MATERIAL", LuaValue.valueOf(TAB_MATERIAL));
			scriptConstGiving.set("TAB_QUEST", LuaValue.valueOf(TAB_QUEST));
			scriptConstGiving.set("TAB_CONSUME", LuaValue.valueOf(TAB_CONSUME));
			env.set("BagTab", scriptConstGiving);
			env.get("package").get("loaded").set("BagTab", scriptConstGiving);
			return env;
		}
	}
	public static class GivingMethod extends TwoArgFunction {
		public static final int GIVING_METHOD_NONE = 0;
		public static final int GIVING_METHOD_EXACT = 1;
		public static final int GIVING_METHOD_GROUP = 2;
		public static final int GIVING_METHOD_VAGUE_GROUP = 3;
		public static final int GIVING_METHOD_ANY_NO_FINISH = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGiving = new LuaTable();
			scriptConstGiving.set("GIVING_METHOD_NONE", LuaValue.valueOf(GIVING_METHOD_NONE));
			scriptConstGiving.set("GIVING_METHOD_EXACT", LuaValue.valueOf(GIVING_METHOD_EXACT));
			scriptConstGiving.set("GIVING_METHOD_GROUP", LuaValue.valueOf(GIVING_METHOD_GROUP));
			scriptConstGiving.set("GIVING_METHOD_VAGUE_GROUP", LuaValue.valueOf(GIVING_METHOD_VAGUE_GROUP));
			scriptConstGiving.set("GIVING_METHOD_ANY_NO_FINISH", LuaValue.valueOf(GIVING_METHOD_ANY_NO_FINISH));
			env.set("GivingMethod", scriptConstGiving);
			env.get("package").get("loaded").set("GivingMethod", scriptConstGiving);
			return env;
		}
	}
}
