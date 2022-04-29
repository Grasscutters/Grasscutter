package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Reliquary {
	public static class ReliquaryMainAffixName extends TwoArgFunction {
		public static final int Reliquary_Main_Affix_NONE = 0;
		public static final int Reliquary_Main_Affix_TOUGH = 1;
		public static final int Reliquary_Main_Affix_STRONG = 2;
		public static final int Reliquary_Main_Affix_RUTHLESS = 3;
		public static final int Reliquary_Main_Affix_FATAL = 4;
		public static final int Reliquary_Main_Affix_GLORY = 5;
		public static final int Reliquary_Main_Affix_EMINENCE = 6;
		public static final int Reliquary_Main_Affix_EXULTATION = 7;
		public static final int Reliquary_Main_Affix_CRUEL = 8;
		public static final int Reliquary_Main_Affix_HOLINESS = 9;
		public static final int Reliquary_Main_Affix_FIERCE = 10;
		public static final int Reliquary_Main_Affix_WISDOM = 11;
		public static final int Reliquary_Main_Affix_CONCENTRATION = 12;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstReliquary = new LuaTable();
			scriptConstReliquary.set("Reliquary_Main_Affix_NONE", LuaValue.valueOf(Reliquary_Main_Affix_NONE));
			scriptConstReliquary.set("Reliquary_Main_Affix_TOUGH", LuaValue.valueOf(Reliquary_Main_Affix_TOUGH));
			scriptConstReliquary.set("Reliquary_Main_Affix_STRONG", LuaValue.valueOf(Reliquary_Main_Affix_STRONG));
			scriptConstReliquary.set("Reliquary_Main_Affix_RUTHLESS", LuaValue.valueOf(Reliquary_Main_Affix_RUTHLESS));
			scriptConstReliquary.set("Reliquary_Main_Affix_FATAL", LuaValue.valueOf(Reliquary_Main_Affix_FATAL));
			scriptConstReliquary.set("Reliquary_Main_Affix_GLORY", LuaValue.valueOf(Reliquary_Main_Affix_GLORY));
			scriptConstReliquary.set("Reliquary_Main_Affix_EMINENCE", LuaValue.valueOf(Reliquary_Main_Affix_EMINENCE));
			scriptConstReliquary.set("Reliquary_Main_Affix_EXULTATION", LuaValue.valueOf(Reliquary_Main_Affix_EXULTATION));
			scriptConstReliquary.set("Reliquary_Main_Affix_CRUEL", LuaValue.valueOf(Reliquary_Main_Affix_CRUEL));
			scriptConstReliquary.set("Reliquary_Main_Affix_HOLINESS", LuaValue.valueOf(Reliquary_Main_Affix_HOLINESS));
			scriptConstReliquary.set("Reliquary_Main_Affix_FIERCE", LuaValue.valueOf(Reliquary_Main_Affix_FIERCE));
			scriptConstReliquary.set("Reliquary_Main_Affix_WISDOM", LuaValue.valueOf(Reliquary_Main_Affix_WISDOM));
			scriptConstReliquary.set("Reliquary_Main_Affix_CONCENTRATION", LuaValue.valueOf(Reliquary_Main_Affix_CONCENTRATION));
			env.set("ReliquaryMainAffixName", scriptConstReliquary);
			env.get("package").get("loaded").set("ReliquaryMainAffixName", scriptConstReliquary);
			return env;
		}
	}
}
