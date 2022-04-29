package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class WorldArea {
	public static class ExploreEventType extends TwoArgFunction {
		public static final int EXPLORE_EVENT_NONE = 0;
		public static final int EXPLORE_EVENT_UNLOCK_POINT = 1;
		public static final int EXPLORE_EVENT_OPEN_CHEST = 2;
		public static final int EXPLORE_EVENT_CLEAR_GROUP_MONSTER = 3;
		public static final int EXPLORE_EVENT_ITEM_ADD = 4;
		public static final int EXPLORE_EVENT_ENTER_FORCE = 5;
		public static final int EXPLORE_EVENT_OPEN_CHEST_BY_GADGET = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWorldArea = new LuaTable();
			scriptConstWorldArea.set("EXPLORE_EVENT_NONE", LuaValue.valueOf(EXPLORE_EVENT_NONE));
			scriptConstWorldArea.set("EXPLORE_EVENT_UNLOCK_POINT", LuaValue.valueOf(EXPLORE_EVENT_UNLOCK_POINT));
			scriptConstWorldArea.set("EXPLORE_EVENT_OPEN_CHEST", LuaValue.valueOf(EXPLORE_EVENT_OPEN_CHEST));
			scriptConstWorldArea.set("EXPLORE_EVENT_CLEAR_GROUP_MONSTER", LuaValue.valueOf(EXPLORE_EVENT_CLEAR_GROUP_MONSTER));
			scriptConstWorldArea.set("EXPLORE_EVENT_ITEM_ADD", LuaValue.valueOf(EXPLORE_EVENT_ITEM_ADD));
			scriptConstWorldArea.set("EXPLORE_EVENT_ENTER_FORCE", LuaValue.valueOf(EXPLORE_EVENT_ENTER_FORCE));
			scriptConstWorldArea.set("EXPLORE_EVENT_OPEN_CHEST_BY_GADGET", LuaValue.valueOf(EXPLORE_EVENT_OPEN_CHEST_BY_GADGET));
			env.set("ExploreEventType", scriptConstWorldArea);
			env.get("package").get("loaded").set("ExploreEventType", scriptConstWorldArea);
			return env;
		}
	}
	public static class WorldAreaLevelupActionType extends TwoArgFunction {
		public static final int WORLD_AREA_ACTION_NONE = 0;
		public static final int WORLD_AREA_ACTION_REWARD = 1;
		public static final int WORLD_AREA_ACTION_IMPROVE_STAMINA = 2;
		public static final int WORLD_AREA_ACTION_UNLOCK_FORCE = 3;
		public static final int WORLD_AREA_ACTION_UNLOCK_DUNGEON_ENTRANCE = 4;
		public static final int WORLD_AREA_ACTION_ACTIVATE_ITEM = 5;
		public static final int WORLD_AREA_ACTION_UNLOCK_DYNAMIC_HARD = 6;
		public static final int WORLD_AREA_ACTION_UNLOCK_AIR_PORTAL = 7;
		public static final int WORLD_AREA_ACTION_NOTIFY_GROUP = 8;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWorldArea = new LuaTable();
			scriptConstWorldArea.set("WORLD_AREA_ACTION_NONE", LuaValue.valueOf(WORLD_AREA_ACTION_NONE));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_REWARD", LuaValue.valueOf(WORLD_AREA_ACTION_REWARD));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_IMPROVE_STAMINA", LuaValue.valueOf(WORLD_AREA_ACTION_IMPROVE_STAMINA));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_UNLOCK_FORCE", LuaValue.valueOf(WORLD_AREA_ACTION_UNLOCK_FORCE));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_UNLOCK_DUNGEON_ENTRANCE", LuaValue.valueOf(WORLD_AREA_ACTION_UNLOCK_DUNGEON_ENTRANCE));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_ACTIVATE_ITEM", LuaValue.valueOf(WORLD_AREA_ACTION_ACTIVATE_ITEM));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_UNLOCK_DYNAMIC_HARD", LuaValue.valueOf(WORLD_AREA_ACTION_UNLOCK_DYNAMIC_HARD));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_UNLOCK_AIR_PORTAL", LuaValue.valueOf(WORLD_AREA_ACTION_UNLOCK_AIR_PORTAL));
			scriptConstWorldArea.set("WORLD_AREA_ACTION_NOTIFY_GROUP", LuaValue.valueOf(WORLD_AREA_ACTION_NOTIFY_GROUP));
			env.set("WorldAreaLevelupActionType", scriptConstWorldArea);
			env.get("package").get("loaded").set("WorldAreaLevelupActionType", scriptConstWorldArea);
			return env;
		}
	}
	public static class WorldAreaType extends TwoArgFunction {
		public static final int LEVEL_NONE = 0;
		public static final int LEVEL_1 = 1;
		public static final int LEVEL_2 = 2;
		public static final int LEVEL_3 = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWorldArea = new LuaTable();
			scriptConstWorldArea.set("LEVEL_NONE", LuaValue.valueOf(LEVEL_NONE));
			scriptConstWorldArea.set("LEVEL_1", LuaValue.valueOf(LEVEL_1));
			scriptConstWorldArea.set("LEVEL_2", LuaValue.valueOf(LEVEL_2));
			scriptConstWorldArea.set("LEVEL_3", LuaValue.valueOf(LEVEL_3));
			env.set("WorldAreaType", scriptConstWorldArea);
			env.get("package").get("loaded").set("WorldAreaType", scriptConstWorldArea);
			return env;
		}
	}
	public static class AreaTerrainType extends TwoArgFunction {
		public static final int AREA_TERRAIN_NONE = 0;
		public static final int AREA_TERRAIN_CITY = 1;
		public static final int AREA_TERRAIN_OUTDOOR = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWorldArea = new LuaTable();
			scriptConstWorldArea.set("AREA_TERRAIN_NONE", LuaValue.valueOf(AREA_TERRAIN_NONE));
			scriptConstWorldArea.set("AREA_TERRAIN_CITY", LuaValue.valueOf(AREA_TERRAIN_CITY));
			scriptConstWorldArea.set("AREA_TERRAIN_OUTDOOR", LuaValue.valueOf(AREA_TERRAIN_OUTDOOR));
			env.set("AreaTerrainType", scriptConstWorldArea);
			env.get("package").get("loaded").set("AreaTerrainType", scriptConstWorldArea);
			return env;
		}
	}
}
