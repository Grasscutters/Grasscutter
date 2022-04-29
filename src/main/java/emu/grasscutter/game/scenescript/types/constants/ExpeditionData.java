package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class ExpeditionData {
	public static class ExpeditionOpenCondType extends TwoArgFunction {
		public static final int EXP_OPEN_COND_LEVEL = 0;
		public static final int EXP_OPEN_COND_POINT = 1;
		public static final int EXP_OPEN_COND_QUEST = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstExpeditionData = new LuaTable();
			scriptConstExpeditionData.set("EXP_OPEN_COND_LEVEL", LuaValue.valueOf(EXP_OPEN_COND_LEVEL));
			scriptConstExpeditionData.set("EXP_OPEN_COND_POINT", LuaValue.valueOf(EXP_OPEN_COND_POINT));
			scriptConstExpeditionData.set("EXP_OPEN_COND_QUEST", LuaValue.valueOf(EXP_OPEN_COND_QUEST));
			env.set("ExpeditionOpenCondType", scriptConstExpeditionData);
			env.get("package").get("loaded").set("ExpeditionOpenCondType", scriptConstExpeditionData);
			return env;
		}
	}
}
