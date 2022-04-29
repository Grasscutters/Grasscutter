package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class ItemLimit {
	public static class ItemLimitRefreshType extends TwoArgFunction {
		public static final int ITEM_LIMIT_REFRESH_NONE = 0;
		public static final int ITEM_LIMIT_REFRESH_DAILY = 1;
		public static final int ITEM_LIMIT_REFRESH_MONTHLY = 2;
		public static final int ITEM_LIMIT_REFRESH_PERSISTENT = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstItemLimit = new LuaTable();
			scriptConstItemLimit.set("ITEM_LIMIT_REFRESH_NONE", LuaValue.valueOf(ITEM_LIMIT_REFRESH_NONE));
			scriptConstItemLimit.set("ITEM_LIMIT_REFRESH_DAILY", LuaValue.valueOf(ITEM_LIMIT_REFRESH_DAILY));
			scriptConstItemLimit.set("ITEM_LIMIT_REFRESH_MONTHLY", LuaValue.valueOf(ITEM_LIMIT_REFRESH_MONTHLY));
			scriptConstItemLimit.set("ITEM_LIMIT_REFRESH_PERSISTENT", LuaValue.valueOf(ITEM_LIMIT_REFRESH_PERSISTENT));
			env.set("ItemLimitRefreshType", scriptConstItemLimit);
			env.get("package").get("loaded").set("ItemLimitRefreshType", scriptConstItemLimit);
			return env;
		}
	}
	public static class ItemLimitType extends TwoArgFunction {
		public static final int ITEM_LIMIT_NONE = 0;
		public static final int ITEM_LIMIT_INTERNAL = 1;
		public static final int ITEM_LIMIT_GM = 2;
		public static final int ITEM_LIMIT_QUEST = 10001;
		public static final int ITEM_LIMIT_CITY_UPGRADE = 10002;
		public static final int ITEM_LIMIT_UNLOCK_TRANS_POINT = 10003;
		public static final int ITEM_LIMIT_UNLOCK_DUNGEON = 10004;
		public static final int ITEM_LIMIT_INVESTIGATION = 10005;
		public static final int ITEM_LIMIT_QUEST_DUNGEON = 10006;
		public static final int ITEM_LIMIT_PLAYER_LEVEL_UPGRADE = 10007;
		public static final int ITEM_LIMIT_PUSH_TIPS_REWARD = 10008;
		public static final int ITEM_LIMIT_AVATAR_FETTER_REWARD = 10009;
		public static final int ITEM_LIMIT_WORLD_AREA_EXPLORE_EVENT_REWARD = 10010;
		public static final int ITEM_LIMIT_ACTIVITY_SEA_LAMP = 10011;
		public static final int ITEM_LIMIT_ONEOFF_BIG_WORLD_DROP = 11001;
		public static final int ITEM_LIMIT_GAMEPLAY_NICHE = 11003;
		public static final int ITEM_LIMIT_ONEOFF_DUNGEON_DROP = 11004;
		public static final int ITEM_LIMIT_ONEOFF_PLUNDER_DROP = 11005;
		public static final int ITEM_LIMIT_ONEOFF_SUBFIELD_DROP = 11006;
		public static final int ITEM_LIMIT_BIG_WORLD_CHEST = 11007;
		public static final int ITEM_LIMIT_GACHA_TOKEN_DROP = 11008;
		public static final int ITEM_LIMIT_DAILY_BIG_WORLD_DROP = 20001;
		public static final int ITEM_LIMIT_DAILY_DUNGEON_DROP = 20002;
		public static final int ITEM_LIMIT_DAILY_PLUNDER_DROP = 20003;
		public static final int ITEM_LIMIT_DAILY_SUBFIELD_DROP = 20004;
		public static final int ITEM_LIMIT_DAILY_SEA_LAMP_DROP = 20005;
		public static final int ITEM_LIMIT_NORMAL_DUNGEON = 20006;
		public static final int ITEM_LIMIT_DAILY_TASK = 20007;
		public static final int ITEM_LIMIT_DAILY_TASK_SCORE = 20008;
		public static final int ITEM_LIMIT_RAND_TASK_DROP = 20009;
		public static final int ITEM_LIMIT_EXPEDITION = 20010;
		public static final int ITEM_LIMIT_SMALL_MONSTER_DIE = 20011;
		public static final int ITEM_LIMIT_ELITE_MONSTER_DIE = 20012;
		public static final int ITEM_LIMIT_BOSS_MONSTER_DIE = 20013;
		public static final int ITEM_LIMIT_BIG_BOSS_MONSTER_DIE = 20014;
		public static final int ITEM_LIMIT_SMALL_ENV_ANIMAL_DIE = 20015;
		public static final int ITEM_LIMIT_MONSTER_EXCEL_DROP = 20016;
		public static final int ITEM_LIMIT_TOWER_DAILY = 21001;
		public static final int ITEM_LIMIT_RAND_TASK_QUEST_REWARD = 21002;
		public static final int ITEM_LIMIT_ENV_ANIMAL = 22001;
		public static final int ITEM_LIMIT_GATHER = 22002;
		public static final int ITEM_LIMIT_OPERATION_DAILY_ACTIVITY = 22003;
		public static final int ITEM_LIMIT_QUEST_ADD_ITEM = 22004;
		public static final int ITEM_LIMIT_TOWER_MONTHLY = 30001;
		public static final int ITEM_LIMIT_GACHA = 100001;
		public static final int ITEM_LIMIT_SHOP = 100002;
		public static final int ITEM_LIMIT_COOK = 100003;
		public static final int ITEM_LIMIT_COMPOUND = 100004;
		public static final int ITEM_LIMIT_COMBINE = 100005;
		public static final int ITEM_LIMIT_FORGE = 100006;
		public static final int ITEM_LIMIT_RANDOM_CHEST = 100007;
		public static final int ITEM_LIMIT_USE_ITEM = 100008;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstItemLimit = new LuaTable();
			scriptConstItemLimit.set("ITEM_LIMIT_NONE", LuaValue.valueOf(ITEM_LIMIT_NONE));
			scriptConstItemLimit.set("ITEM_LIMIT_INTERNAL", LuaValue.valueOf(ITEM_LIMIT_INTERNAL));
			scriptConstItemLimit.set("ITEM_LIMIT_GM", LuaValue.valueOf(ITEM_LIMIT_GM));
			scriptConstItemLimit.set("ITEM_LIMIT_QUEST", LuaValue.valueOf(ITEM_LIMIT_QUEST));
			scriptConstItemLimit.set("ITEM_LIMIT_CITY_UPGRADE", LuaValue.valueOf(ITEM_LIMIT_CITY_UPGRADE));
			scriptConstItemLimit.set("ITEM_LIMIT_UNLOCK_TRANS_POINT", LuaValue.valueOf(ITEM_LIMIT_UNLOCK_TRANS_POINT));
			scriptConstItemLimit.set("ITEM_LIMIT_UNLOCK_DUNGEON", LuaValue.valueOf(ITEM_LIMIT_UNLOCK_DUNGEON));
			scriptConstItemLimit.set("ITEM_LIMIT_INVESTIGATION", LuaValue.valueOf(ITEM_LIMIT_INVESTIGATION));
			scriptConstItemLimit.set("ITEM_LIMIT_QUEST_DUNGEON", LuaValue.valueOf(ITEM_LIMIT_QUEST_DUNGEON));
			scriptConstItemLimit.set("ITEM_LIMIT_PLAYER_LEVEL_UPGRADE", LuaValue.valueOf(ITEM_LIMIT_PLAYER_LEVEL_UPGRADE));
			scriptConstItemLimit.set("ITEM_LIMIT_PUSH_TIPS_REWARD", LuaValue.valueOf(ITEM_LIMIT_PUSH_TIPS_REWARD));
			scriptConstItemLimit.set("ITEM_LIMIT_AVATAR_FETTER_REWARD", LuaValue.valueOf(ITEM_LIMIT_AVATAR_FETTER_REWARD));
			scriptConstItemLimit.set("ITEM_LIMIT_WORLD_AREA_EXPLORE_EVENT_REWARD", LuaValue.valueOf(ITEM_LIMIT_WORLD_AREA_EXPLORE_EVENT_REWARD));
			scriptConstItemLimit.set("ITEM_LIMIT_ACTIVITY_SEA_LAMP", LuaValue.valueOf(ITEM_LIMIT_ACTIVITY_SEA_LAMP));
			scriptConstItemLimit.set("ITEM_LIMIT_ONEOFF_BIG_WORLD_DROP", LuaValue.valueOf(ITEM_LIMIT_ONEOFF_BIG_WORLD_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_GAMEPLAY_NICHE", LuaValue.valueOf(ITEM_LIMIT_GAMEPLAY_NICHE));
			scriptConstItemLimit.set("ITEM_LIMIT_ONEOFF_DUNGEON_DROP", LuaValue.valueOf(ITEM_LIMIT_ONEOFF_DUNGEON_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_ONEOFF_PLUNDER_DROP", LuaValue.valueOf(ITEM_LIMIT_ONEOFF_PLUNDER_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_ONEOFF_SUBFIELD_DROP", LuaValue.valueOf(ITEM_LIMIT_ONEOFF_SUBFIELD_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_BIG_WORLD_CHEST", LuaValue.valueOf(ITEM_LIMIT_BIG_WORLD_CHEST));
			scriptConstItemLimit.set("ITEM_LIMIT_GACHA_TOKEN_DROP", LuaValue.valueOf(ITEM_LIMIT_GACHA_TOKEN_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_BIG_WORLD_DROP", LuaValue.valueOf(ITEM_LIMIT_DAILY_BIG_WORLD_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_DUNGEON_DROP", LuaValue.valueOf(ITEM_LIMIT_DAILY_DUNGEON_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_PLUNDER_DROP", LuaValue.valueOf(ITEM_LIMIT_DAILY_PLUNDER_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_SUBFIELD_DROP", LuaValue.valueOf(ITEM_LIMIT_DAILY_SUBFIELD_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_SEA_LAMP_DROP", LuaValue.valueOf(ITEM_LIMIT_DAILY_SEA_LAMP_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_NORMAL_DUNGEON", LuaValue.valueOf(ITEM_LIMIT_NORMAL_DUNGEON));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_TASK", LuaValue.valueOf(ITEM_LIMIT_DAILY_TASK));
			scriptConstItemLimit.set("ITEM_LIMIT_DAILY_TASK_SCORE", LuaValue.valueOf(ITEM_LIMIT_DAILY_TASK_SCORE));
			scriptConstItemLimit.set("ITEM_LIMIT_RAND_TASK_DROP", LuaValue.valueOf(ITEM_LIMIT_RAND_TASK_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_EXPEDITION", LuaValue.valueOf(ITEM_LIMIT_EXPEDITION));
			scriptConstItemLimit.set("ITEM_LIMIT_SMALL_MONSTER_DIE", LuaValue.valueOf(ITEM_LIMIT_SMALL_MONSTER_DIE));
			scriptConstItemLimit.set("ITEM_LIMIT_ELITE_MONSTER_DIE", LuaValue.valueOf(ITEM_LIMIT_ELITE_MONSTER_DIE));
			scriptConstItemLimit.set("ITEM_LIMIT_BOSS_MONSTER_DIE", LuaValue.valueOf(ITEM_LIMIT_BOSS_MONSTER_DIE));
			scriptConstItemLimit.set("ITEM_LIMIT_BIG_BOSS_MONSTER_DIE", LuaValue.valueOf(ITEM_LIMIT_BIG_BOSS_MONSTER_DIE));
			scriptConstItemLimit.set("ITEM_LIMIT_SMALL_ENV_ANIMAL_DIE", LuaValue.valueOf(ITEM_LIMIT_SMALL_ENV_ANIMAL_DIE));
			scriptConstItemLimit.set("ITEM_LIMIT_MONSTER_EXCEL_DROP", LuaValue.valueOf(ITEM_LIMIT_MONSTER_EXCEL_DROP));
			scriptConstItemLimit.set("ITEM_LIMIT_TOWER_DAILY", LuaValue.valueOf(ITEM_LIMIT_TOWER_DAILY));
			scriptConstItemLimit.set("ITEM_LIMIT_RAND_TASK_QUEST_REWARD", LuaValue.valueOf(ITEM_LIMIT_RAND_TASK_QUEST_REWARD));
			scriptConstItemLimit.set("ITEM_LIMIT_ENV_ANIMAL", LuaValue.valueOf(ITEM_LIMIT_ENV_ANIMAL));
			scriptConstItemLimit.set("ITEM_LIMIT_GATHER", LuaValue.valueOf(ITEM_LIMIT_GATHER));
			scriptConstItemLimit.set("ITEM_LIMIT_OPERATION_DAILY_ACTIVITY", LuaValue.valueOf(ITEM_LIMIT_OPERATION_DAILY_ACTIVITY));
			scriptConstItemLimit.set("ITEM_LIMIT_QUEST_ADD_ITEM", LuaValue.valueOf(ITEM_LIMIT_QUEST_ADD_ITEM));
			scriptConstItemLimit.set("ITEM_LIMIT_TOWER_MONTHLY", LuaValue.valueOf(ITEM_LIMIT_TOWER_MONTHLY));
			scriptConstItemLimit.set("ITEM_LIMIT_GACHA", LuaValue.valueOf(ITEM_LIMIT_GACHA));
			scriptConstItemLimit.set("ITEM_LIMIT_SHOP", LuaValue.valueOf(ITEM_LIMIT_SHOP));
			scriptConstItemLimit.set("ITEM_LIMIT_COOK", LuaValue.valueOf(ITEM_LIMIT_COOK));
			scriptConstItemLimit.set("ITEM_LIMIT_COMPOUND", LuaValue.valueOf(ITEM_LIMIT_COMPOUND));
			scriptConstItemLimit.set("ITEM_LIMIT_COMBINE", LuaValue.valueOf(ITEM_LIMIT_COMBINE));
			scriptConstItemLimit.set("ITEM_LIMIT_FORGE", LuaValue.valueOf(ITEM_LIMIT_FORGE));
			scriptConstItemLimit.set("ITEM_LIMIT_RANDOM_CHEST", LuaValue.valueOf(ITEM_LIMIT_RANDOM_CHEST));
			scriptConstItemLimit.set("ITEM_LIMIT_USE_ITEM", LuaValue.valueOf(ITEM_LIMIT_USE_ITEM));
			env.set("ItemLimitType", scriptConstItemLimit);
			env.get("package").get("loaded").set("ItemLimitType", scriptConstItemLimit);
			return env;
		}
	}
	public static class OutputControlType extends TwoArgFunction {
		public static final int OUTPUT_CONTROL_NONE = 0;
		public static final int OUTPUT_CONTROL_DROP = 1;
		public static final int OUTPUT_CONTROL_REWARD = 2;
		public static final int OUTPUT_CONTROL_OTHER = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstItemLimit = new LuaTable();
			scriptConstItemLimit.set("OUTPUT_CONTROL_NONE", LuaValue.valueOf(OUTPUT_CONTROL_NONE));
			scriptConstItemLimit.set("OUTPUT_CONTROL_DROP", LuaValue.valueOf(OUTPUT_CONTROL_DROP));
			scriptConstItemLimit.set("OUTPUT_CONTROL_REWARD", LuaValue.valueOf(OUTPUT_CONTROL_REWARD));
			scriptConstItemLimit.set("OUTPUT_CONTROL_OTHER", LuaValue.valueOf(OUTPUT_CONTROL_OTHER));
			env.set("OutputControlType", scriptConstItemLimit);
			env.get("package").get("loaded").set("OutputControlType", scriptConstItemLimit);
			return env;
		}
	}
}
