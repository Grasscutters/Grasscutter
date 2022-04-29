package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Reminder {
	public static class ReminderShowType extends TwoArgFunction {
		public static final int NONE = 0;
		public static final int TALK = 1;
		public static final int BUBBLE = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstReminder = new LuaTable();
			scriptConstReminder.set("NONE", LuaValue.valueOf(NONE));
			scriptConstReminder.set("TALK", LuaValue.valueOf(TALK));
			scriptConstReminder.set("BUBBLE", LuaValue.valueOf(BUBBLE));
			env.set("ReminderShowType", scriptConstReminder);
			env.get("package").get("loaded").set("ReminderShowType", scriptConstReminder);
			return env;
		}
	}
	public static class ReminderStyleType extends TwoArgFunction {
		public static final int Normal = 0;
		public static final int Banner = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstReminder = new LuaTable();
			scriptConstReminder.set("Normal", LuaValue.valueOf(Normal));
			scriptConstReminder.set("Banner", LuaValue.valueOf(Banner));
			env.set("ReminderStyleType", scriptConstReminder);
			env.get("package").get("loaded").set("ReminderStyleType", scriptConstReminder);
			return env;
		}
	}
}
