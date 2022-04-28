package emu.grasscutter.game.scenescript.types.constants;

public class WorldArea {
	public static class AreaTerrainType {
		public static final int AREA_TERRAIN_CITY = 1;
		public static final int AREA_TERRAIN_NONE = 0;
		public static final int AREA_TERRAIN_OUTDOOR = 2;
	}
	public static class ExploreEventType {
		public static final int EXPLORE_EVENT_OPEN_CHEST_BY_GADGET = 6;
		public static final int EXPLORE_EVENT_UNLOCK_POINT = 1;
		public static final int EXPLORE_EVENT_CLEAR_GROUP_MONSTER = 3;
		public static final int EXPLORE_EVENT_ENTER_FORCE = 5;
		public static final int EXPLORE_EVENT_ITEM_ADD = 4;
		public static final int EXPLORE_EVENT_NONE = 0;
		public static final int EXPLORE_EVENT_OPEN_CHEST = 2;
	}
	public static class WorldAreaLevelupActionType {
		public static final int WORLD_AREA_ACTION_NOTIFY_GROUP = 8;
		public static final int WORLD_AREA_ACTION_UNLOCK_DUNGEON_ENTRANCE = 4;
		public static final int WORLD_AREA_ACTION_IMPROVE_STAMINA = 2;
		public static final int WORLD_AREA_ACTION_NONE = 0;
		public static final int WORLD_AREA_ACTION_REWARD = 1;
		public static final int WORLD_AREA_ACTION_UNLOCK_AIR_PORTAL = 7;
		public static final int WORLD_AREA_ACTION_UNLOCK_DYNAMIC_HARD = 6;
		public static final int WORLD_AREA_ACTION_UNLOCK_FORCE = 3;
		public static final int WORLD_AREA_ACTION_ACTIVATE_ITEM = 5;
	}
	public static class WorldAreaType {
		public static final int LEVEL_2 = 2;
		public static final int LEVEL_3 = 3;
		public static final int LEVEL_NONE = 0;
		public static final int LEVEL_1 = 1;
	}
}
