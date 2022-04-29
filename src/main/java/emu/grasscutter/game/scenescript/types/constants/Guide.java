package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Guide {
	public static class GuideTriggerType extends TwoArgFunction {
		public static final int PLAYER_LEVEL = 0;
		public static final int ANY_AVATAR_LEVEL = 1;
		public static final int GET_NEW_ITEM = 2;
		public static final int OPENSTATE_CHANGE = 3;
		public static final int HAS_AVATAR_NUM = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGuide = new LuaTable();
			scriptConstGuide.set("PLAYER_LEVEL", LuaValue.valueOf(PLAYER_LEVEL));
			scriptConstGuide.set("ANY_AVATAR_LEVEL", LuaValue.valueOf(ANY_AVATAR_LEVEL));
			scriptConstGuide.set("GET_NEW_ITEM", LuaValue.valueOf(GET_NEW_ITEM));
			scriptConstGuide.set("OPENSTATE_CHANGE", LuaValue.valueOf(OPENSTATE_CHANGE));
			scriptConstGuide.set("HAS_AVATAR_NUM", LuaValue.valueOf(HAS_AVATAR_NUM));
			env.set("GuideTriggerType", scriptConstGuide);
			env.get("package").get("loaded").set("GuideTriggerType", scriptConstGuide);
			return env;
		}
	}
}
