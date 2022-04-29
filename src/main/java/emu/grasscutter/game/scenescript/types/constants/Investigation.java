package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Investigation {
	public static class InvestigationMonsterMapMarkCreateConditionType extends TwoArgFunction {
		public static final int Invalid = 0;
		public static final int PlayerLevelGE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstInvestigation = new LuaTable();
			scriptConstInvestigation.set("Invalid", LuaValue.valueOf(Invalid));
			scriptConstInvestigation.set("PlayerLevelGE", LuaValue.valueOf(PlayerLevelGE));
			env.set("InvestigationMonsterMapMarkCreateConditionType", scriptConstInvestigation);
			env.get("package").get("loaded").set("InvestigationMonsterMapMarkCreateConditionType", scriptConstInvestigation);
			return env;
		}
	}
	public static class InvestigationMonsterMapMarkCreateType extends TwoArgFunction {
		public static final int AfterUnlock = 0;
		public static final int NerverCreate = 1;
		public static final int ExtraConditions = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstInvestigation = new LuaTable();
			scriptConstInvestigation.set("AfterUnlock", LuaValue.valueOf(AfterUnlock));
			scriptConstInvestigation.set("NerverCreate", LuaValue.valueOf(NerverCreate));
			scriptConstInvestigation.set("ExtraConditions", LuaValue.valueOf(ExtraConditions));
			env.set("InvestigationMonsterMapMarkCreateType", scriptConstInvestigation);
			env.get("package").get("loaded").set("InvestigationMonsterMapMarkCreateType", scriptConstInvestigation);
			return env;
		}
	}
}
