package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Watcher {
	public static class AvatarFilterType extends TwoArgFunction {
		public static final int AVATAR_FILTER_NONE = 0;
		public static final int AVATAR_FILTER_AVATAR_ID = 1;
		public static final int AVATAR_FILTER_WEAPON_TYPE = 2;
		public static final int AVATAR_FILTER_ELEMENT_TYPE = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWatcher = new LuaTable();
			scriptConstWatcher.set("AVATAR_FILTER_NONE", LuaValue.valueOf(AVATAR_FILTER_NONE));
			scriptConstWatcher.set("AVATAR_FILTER_AVATAR_ID", LuaValue.valueOf(AVATAR_FILTER_AVATAR_ID));
			scriptConstWatcher.set("AVATAR_FILTER_WEAPON_TYPE", LuaValue.valueOf(AVATAR_FILTER_WEAPON_TYPE));
			scriptConstWatcher.set("AVATAR_FILTER_ELEMENT_TYPE", LuaValue.valueOf(AVATAR_FILTER_ELEMENT_TYPE));
			env.set("AvatarFilterType", scriptConstWatcher);
			env.get("package").get("loaded").set("AvatarFilterType", scriptConstWatcher);
			return env;
		}
	}
	public static class PushTipsType extends TwoArgFunction {
		public static final int PUSH_TIPS_NONE = 0;
		public static final int PUSH_TIPS_TUTORIAL = 1;
		public static final int PUSH_TIPS_MONSTER = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWatcher = new LuaTable();
			scriptConstWatcher.set("PUSH_TIPS_NONE", LuaValue.valueOf(PUSH_TIPS_NONE));
			scriptConstWatcher.set("PUSH_TIPS_TUTORIAL", LuaValue.valueOf(PUSH_TIPS_TUTORIAL));
			scriptConstWatcher.set("PUSH_TIPS_MONSTER", LuaValue.valueOf(PUSH_TIPS_MONSTER));
			env.set("PushTipsType", scriptConstWatcher);
			env.get("package").get("loaded").set("PushTipsType", scriptConstWatcher);
			return env;
		}
	}
	public static class WatcherPredicateType extends TwoArgFunction {
		public static final int PREDICATE_NONE = 0;
		public static final int PREDICATE_QUEST_FINISH = 1;
		public static final int PREDICATE_CURRENT_AVATAR = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWatcher = new LuaTable();
			scriptConstWatcher.set("PREDICATE_NONE", LuaValue.valueOf(PREDICATE_NONE));
			scriptConstWatcher.set("PREDICATE_QUEST_FINISH", LuaValue.valueOf(PREDICATE_QUEST_FINISH));
			scriptConstWatcher.set("PREDICATE_CURRENT_AVATAR", LuaValue.valueOf(PREDICATE_CURRENT_AVATAR));
			env.set("WatcherPredicateType", scriptConstWatcher);
			env.get("package").get("loaded").set("WatcherPredicateType", scriptConstWatcher);
			return env;
		}
	}
	public static class WatcherTriggerType extends TwoArgFunction {
		public static final int TRIGGER_NONE = 0;
		public static final int TRIGGER_COMBAT_CONFIG_COMMON = 1;
		public static final int TRIGGER_ELEMENT_VIEW = 2;
		public static final int TRIGGER_ELEMENT_BALL = 3;
		public static final int TRIGGER_OBTAIN_AVATAR = 4;
		public static final int TRIGGER_ENTER_AIRFLOW = 5;
		public static final int TRIGGER_NEW_MONSTER = 6;
		public static final int TRIGGER_WORLD_LEVEL_UP = 7;
		public static final int TRIGGER_NEW_AFFIX = 8;
		public static final int TRIGGER_PLAYER_LEVEL = 9;
		public static final int TRIGGER_DUNGEON_ENTRY_TO_BE_EXPLORED = 10;
		public static final int TRIGGER_CHANGE_INPUT_DEVICE_TYPE = 11;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWatcher = new LuaTable();
			scriptConstWatcher.set("TRIGGER_NONE", LuaValue.valueOf(TRIGGER_NONE));
			scriptConstWatcher.set("TRIGGER_COMBAT_CONFIG_COMMON", LuaValue.valueOf(TRIGGER_COMBAT_CONFIG_COMMON));
			scriptConstWatcher.set("TRIGGER_ELEMENT_VIEW", LuaValue.valueOf(TRIGGER_ELEMENT_VIEW));
			scriptConstWatcher.set("TRIGGER_ELEMENT_BALL", LuaValue.valueOf(TRIGGER_ELEMENT_BALL));
			scriptConstWatcher.set("TRIGGER_OBTAIN_AVATAR", LuaValue.valueOf(TRIGGER_OBTAIN_AVATAR));
			scriptConstWatcher.set("TRIGGER_ENTER_AIRFLOW", LuaValue.valueOf(TRIGGER_ENTER_AIRFLOW));
			scriptConstWatcher.set("TRIGGER_NEW_MONSTER", LuaValue.valueOf(TRIGGER_NEW_MONSTER));
			scriptConstWatcher.set("TRIGGER_WORLD_LEVEL_UP", LuaValue.valueOf(TRIGGER_WORLD_LEVEL_UP));
			scriptConstWatcher.set("TRIGGER_NEW_AFFIX", LuaValue.valueOf(TRIGGER_NEW_AFFIX));
			scriptConstWatcher.set("TRIGGER_PLAYER_LEVEL", LuaValue.valueOf(TRIGGER_PLAYER_LEVEL));
			scriptConstWatcher.set("TRIGGER_DUNGEON_ENTRY_TO_BE_EXPLORED", LuaValue.valueOf(TRIGGER_DUNGEON_ENTRY_TO_BE_EXPLORED));
			scriptConstWatcher.set("TRIGGER_CHANGE_INPUT_DEVICE_TYPE", LuaValue.valueOf(TRIGGER_CHANGE_INPUT_DEVICE_TYPE));
			env.set("WatcherTriggerType", scriptConstWatcher);
			env.get("package").get("loaded").set("WatcherTriggerType", scriptConstWatcher);
			return env;
		}
	}
}
