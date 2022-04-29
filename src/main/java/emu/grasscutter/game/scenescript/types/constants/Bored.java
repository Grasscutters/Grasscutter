package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Bored {
	public static class BoredActionType extends TwoArgFunction {
		public static final int BORED_ACTION_NONE = 0;
		public static final int BORED_ACTION_CREATE_MONSTER = 1;
		public static final int BORED_ACTION_AMBUSH = 2;
		public static final int BORED_ACTION_ENHANCE_GATHER = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstBored = new LuaTable();
			scriptConstBored.set("BORED_ACTION_NONE", LuaValue.valueOf(BORED_ACTION_NONE));
			scriptConstBored.set("BORED_ACTION_CREATE_MONSTER", LuaValue.valueOf(BORED_ACTION_CREATE_MONSTER));
			scriptConstBored.set("BORED_ACTION_AMBUSH", LuaValue.valueOf(BORED_ACTION_AMBUSH));
			scriptConstBored.set("BORED_ACTION_ENHANCE_GATHER", LuaValue.valueOf(BORED_ACTION_ENHANCE_GATHER));
			env.set("BoredActionType", scriptConstBored);
			env.get("package").get("loaded").set("BoredActionType", scriptConstBored);
			return env;
		}
	}
	public static class BoardEventType extends TwoArgFunction {
		public static final int BOARD_EVENT_NONE = 0;
		public static final int BOARD_EVENT_KILL_MONSTER = 1;
		public static final int BOARD_EVENT_GAME = 101;
		public static final int BOARD_EVENT_GATHER = 102;
		public static final int BOARD_EVENT_ACCELERATE = 104;
		public static final int BOARD_EVENT_USE_ITEM = 105;
		public static final int BOARD_EVENT_CHANGE_AVATAR = 106;
		public static final int BOARD_EVENT_SKILL = 107;
		public static final int BOARD_EVENT_REGION = 108;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstBored = new LuaTable();
			scriptConstBored.set("BOARD_EVENT_NONE", LuaValue.valueOf(BOARD_EVENT_NONE));
			scriptConstBored.set("BOARD_EVENT_KILL_MONSTER", LuaValue.valueOf(BOARD_EVENT_KILL_MONSTER));
			scriptConstBored.set("BOARD_EVENT_GAME", LuaValue.valueOf(BOARD_EVENT_GAME));
			scriptConstBored.set("BOARD_EVENT_GATHER", LuaValue.valueOf(BOARD_EVENT_GATHER));
			scriptConstBored.set("BOARD_EVENT_ACCELERATE", LuaValue.valueOf(BOARD_EVENT_ACCELERATE));
			scriptConstBored.set("BOARD_EVENT_USE_ITEM", LuaValue.valueOf(BOARD_EVENT_USE_ITEM));
			scriptConstBored.set("BOARD_EVENT_CHANGE_AVATAR", LuaValue.valueOf(BOARD_EVENT_CHANGE_AVATAR));
			scriptConstBored.set("BOARD_EVENT_SKILL", LuaValue.valueOf(BOARD_EVENT_SKILL));
			scriptConstBored.set("BOARD_EVENT_REGION", LuaValue.valueOf(BOARD_EVENT_REGION));
			env.set("BoardEventType", scriptConstBored);
			env.get("package").get("loaded").set("BoardEventType", scriptConstBored);
			return env;
		}
	}
}
