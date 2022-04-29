package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Tutorial {
	public static class TutorialDetailType extends TwoArgFunction {
		public static final int TUT_DEFAULT = 0;
		public static final int TUT_FULL_SCREEN = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstTutorial = new LuaTable();
			scriptConstTutorial.set("TUT_DEFAULT", LuaValue.valueOf(TUT_DEFAULT));
			scriptConstTutorial.set("TUT_FULL_SCREEN", LuaValue.valueOf(TUT_FULL_SCREEN));
			env.set("TutorialDetailType", scriptConstTutorial);
			env.get("package").get("loaded").set("TutorialDetailType", scriptConstTutorial);
			return env;
		}
	}
}
