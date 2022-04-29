package emu.grasscutter.game.scenescript.types.constants;

public class Watcher {
    public static class WatcherTriggerType {
        public static final int TRIGGER_ELEMENT_BALL = 3;
        public static final int TRIGGER_ELEMENT_VIEW = 2;
        public static final int TRIGGER_NEW_AFFIX = 8;
        public static final int TRIGGER_NEW_MONSTER = 6;
        public static final int TRIGGER_NONE = 0;
        public static final int TRIGGER_OBTAIN_AVATAR = 4;
        public static final int TRIGGER_COMBAT_CONFIG_COMMON = 1;
        public static final int TRIGGER_DUNGEON_ENTRY_TO_BE_EXPLORED = 10;
        public static final int TRIGGER_ENTER_AIRFLOW = 5;
        public static final int TRIGGER_PLAYER_LEVEL = 9;
        public static final int TRIGGER_WORLD_LEVEL_UP = 7;
        public static final int TRIGGER_CHANGE_INPUT_DEVICE_TYPE = 11;
    }

    public static class AvatarFilterType {
        public static final int AVATAR_FILTER_NONE = 0;
        public static final int AVATAR_FILTER_WEAPON_TYPE = 2;
        public static final int AVATAR_FILTER_AVATAR_ID = 1;
        public static final int AVATAR_FILTER_ELEMENT_TYPE = 3;
    }

    public static class PushTipsType {
        public static final int PUSH_TIPS_TUTORIAL = 1;
        public static final int PUSH_TIPS_MONSTER = 2;
        public static final int PUSH_TIPS_NONE = 0;
    }

    public static class WatcherPredicateType {
        public static final int PREDICATE_CURRENT_AVATAR = 2;
        public static final int PREDICATE_NONE = 0;
        public static final int PREDICATE_QUEST_FINISH = 1;
    }
}
