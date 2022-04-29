package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class TextMap {
	public static class TextParamType extends TwoArgFunction {
		public static final int TEXT_PARAM_NONE = 0;
		public static final int TEXT_PARAM_AVATAR_NAME = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstTextMap = new LuaTable();
			scriptConstTextMap.set("TEXT_PARAM_NONE", LuaValue.valueOf(TEXT_PARAM_NONE));
			scriptConstTextMap.set("TEXT_PARAM_AVATAR_NAME", LuaValue.valueOf(TEXT_PARAM_AVATAR_NAME));
			env.set("TextParamType", scriptConstTextMap);
			env.get("package").get("loaded").set("TextParamType", scriptConstTextMap);
			return env;
		}
	}
	public static class TextShowPlace extends TwoArgFunction {
		public static final int TEXT_SHOW_NONE = 0;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstTextMap = new LuaTable();
			scriptConstTextMap.set("TEXT_SHOW_NONE", LuaValue.valueOf(TEXT_SHOW_NONE));
			env.set("TextShowPlace", scriptConstTextMap);
			env.get("package").get("loaded").set("TextShowPlace", scriptConstTextMap);
			return env;
		}
	}
}
