package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class RefreshPolicy {
	public static class RefreshType extends TwoArgFunction {
		public static final int REFRESH_NONE = 0;
		public static final int REFRESH_INTERVAL = 1;
		public static final int REFRESH_DAILY = 2;
		public static final int REFRESH_WEEKlY = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstRefreshPolicy = new LuaTable();
			scriptConstRefreshPolicy.set("REFRESH_NONE", LuaValue.valueOf(REFRESH_NONE));
			scriptConstRefreshPolicy.set("REFRESH_INTERVAL", LuaValue.valueOf(REFRESH_INTERVAL));
			scriptConstRefreshPolicy.set("REFRESH_DAILY", LuaValue.valueOf(REFRESH_DAILY));
			scriptConstRefreshPolicy.set("REFRESH_WEEKlY", LuaValue.valueOf(REFRESH_WEEKlY));
			env.set("RefreshType", scriptConstRefreshPolicy);
			env.get("package").get("loaded").set("RefreshType", scriptConstRefreshPolicy);
			return env;
		}
	}
}
