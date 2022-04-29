package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Localization {
	public static class LocalizationAssetType extends TwoArgFunction {
		public static final int LOC_DEFAULT = 0;
		public static final int LOC_IMAGE = 1;
		public static final int LOC_TEXT = 2;
		public static final int LOC_SUBTITLE = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstLocalization = new LuaTable();
			scriptConstLocalization.set("LOC_DEFAULT", LuaValue.valueOf(LOC_DEFAULT));
			scriptConstLocalization.set("LOC_IMAGE", LuaValue.valueOf(LOC_IMAGE));
			scriptConstLocalization.set("LOC_TEXT", LuaValue.valueOf(LOC_TEXT));
			scriptConstLocalization.set("LOC_SUBTITLE", LuaValue.valueOf(LOC_SUBTITLE));
			env.set("LocalizationAssetType", scriptConstLocalization);
			env.get("package").get("loaded").set("LocalizationAssetType", scriptConstLocalization);
			return env;
		}
	}
}
