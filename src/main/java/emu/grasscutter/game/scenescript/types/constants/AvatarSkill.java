package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class AvatarSkill {
	public static class MonitorType extends TwoArgFunction {
		public static final int MONITOR_NEVER = 0;
		public static final int MONITOR_OFF_STAGE = 1;
		public static final int MONITOR_ON_STAGE = 2;
		public static final int MONITOR_ALWAYS = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarSkill = new LuaTable();
			scriptConstAvatarSkill.set("MONITOR_NEVER", LuaValue.valueOf(MONITOR_NEVER));
			scriptConstAvatarSkill.set("MONITOR_OFF_STAGE", LuaValue.valueOf(MONITOR_OFF_STAGE));
			scriptConstAvatarSkill.set("MONITOR_ON_STAGE", LuaValue.valueOf(MONITOR_ON_STAGE));
			scriptConstAvatarSkill.set("MONITOR_ALWAYS", LuaValue.valueOf(MONITOR_ALWAYS));
			env.set("MonitorType", scriptConstAvatarSkill);
			env.get("package").get("loaded").set("MonitorType", scriptConstAvatarSkill);
			return env;
		}
	}
}
