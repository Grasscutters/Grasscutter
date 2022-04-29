package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DungeonEntryDataConfig {
	public static class DungeonEntrySatisfiedConditionType extends TwoArgFunction {
		public static final int DUNGEON_ENTRY_CONDITION_NONE = 0;
		public static final int DUNGEON_ENTRY_CONDITION_LEVEL = 1;
		public static final int DUNGEON_ENTRY_CONDITION_QUEST = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeonEntryDataConfig = new LuaTable();
			scriptConstDungeonEntryDataConfig.set("DUNGEON_ENTRY_CONDITION_NONE", LuaValue.valueOf(DUNGEON_ENTRY_CONDITION_NONE));
			scriptConstDungeonEntryDataConfig.set("DUNGEON_ENTRY_CONDITION_LEVEL", LuaValue.valueOf(DUNGEON_ENTRY_CONDITION_LEVEL));
			scriptConstDungeonEntryDataConfig.set("DUNGEON_ENTRY_CONDITION_QUEST", LuaValue.valueOf(DUNGEON_ENTRY_CONDITION_QUEST));
			env.set("DungeonEntrySatisfiedConditionType", scriptConstDungeonEntryDataConfig);
			env.get("package").get("loaded").set("DungeonEntrySatisfiedConditionType", scriptConstDungeonEntryDataConfig);
			return env;
		}
	}
	public static class DungunEntryType extends TwoArgFunction {
		public static final int DUNGEN_ENTRY_TYPE_NONE = 0;
		public static final int DUNGEN_ENTRY_TYPE_AVATAR_EXP = 1;
		public static final int DUNGEN_ENTRY_TYPE_WEAPON_PROMOTE = 2;
		public static final int DUNGEN_ENTRY_TYPE_AVATAR_TALENT = 3;
		public static final int DUNGEN_ENTRY_TYPE_RELIQUARY = 4;
		public static final int DUNGEN_ENTRY_TYPE_SCOIN = 5;
		public static final int DUNGEON_ENTRY_TYPE_OBSCURAE = 6;
		public static final int DUNGEON_ENTRY_TYPE_NORMAL = 7;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstDungeonEntryDataConfig = new LuaTable();
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_NONE", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_NONE));
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_AVATAR_EXP", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_AVATAR_EXP));
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_WEAPON_PROMOTE", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_WEAPON_PROMOTE));
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_AVATAR_TALENT", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_AVATAR_TALENT));
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_RELIQUARY", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_RELIQUARY));
			scriptConstDungeonEntryDataConfig.set("DUNGEN_ENTRY_TYPE_SCOIN", LuaValue.valueOf(DUNGEN_ENTRY_TYPE_SCOIN));
			scriptConstDungeonEntryDataConfig.set("DUNGEON_ENTRY_TYPE_OBSCURAE", LuaValue.valueOf(DUNGEON_ENTRY_TYPE_OBSCURAE));
			scriptConstDungeonEntryDataConfig.set("DUNGEON_ENTRY_TYPE_NORMAL", LuaValue.valueOf(DUNGEON_ENTRY_TYPE_NORMAL));
			env.set("DungunEntryType", scriptConstDungeonEntryDataConfig);
			env.get("package").get("loaded").set("DungunEntryType", scriptConstDungeonEntryDataConfig);
			return env;
		}
	}
}
