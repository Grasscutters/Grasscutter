package emu.grasscutter.game.scenescript.types.constants;

public class Material {
    public static class DocumentType {
        public static final int Book = 0;
        public static final int Video = 1;
    }

    public static class FoodQualityType {
        public static final int FOOD_QUALITY_ORDINARY = 2;
        public static final int FOOD_QUALITY_STRANGE = 1;
        public static final int FOOD_QUALITY_DELICIOUS = 3;
        public static final int FOOD_QUALITY_NONE = 0;
    }

    public static class ItemUseOp {
        public static final int ITEM_USE_ACCEPT_QUEST = 1;
        public static final int ITEM_USE_ADD_AVATAR_BIG_TALENT_POINT = 18;
        public static final int ITEM_USE_ADD_BIG_TALENT_POINT = 6;
        public static final int ITEM_USE_ADD_ELEM_ENERGY = 11;
        public static final int ITEM_USE_ADD_EXP = 4;
        public static final int ITEM_USE_ADD_ITEM = 25;
        public static final int ITEM_USE_ADD_TEMPORARY_STAMINA = 8;
        public static final int ITEM_USE_ADD_SERVER_BUFF = 15;
        public static final int ITEM_USE_MAKE_GADGET = 24;
        public static final int ITEM_USE_ADD_AVATAR_SMALL_TALENT_POINT = 19;
        public static final int ITEM_USE_ADD_DUNGEON_COND_TIME = 13;
        public static final int ITEM_USE_ADD_MAIN_AVATAR_BIG_TALENT_POINT = 22;
        public static final int ITEM_USE_DEL_SERVER_BUFF = 16;
        public static final int ITEM_USE_OPEN_RANDOM_CHEST = 20;
        public static final int ITEM_USE_RELIVE_AVATAR = 5;
        public static final int ITEM_USE_ADD_ALL_ENERGY = 12;
        public static final int ITEM_USE_ADD_CUR_HP = 10;
        public static final int ITEM_USE_ADD_CUR_STAMINA = 9;
        public static final int ITEM_USE_ADD_MAIN_AVATAR_SMALL_TALENT_POINT = 23;
        public static final int ITEM_USE_ADD_PERSIST_STAMINA = 7;
        public static final int ITEM_USE_ADD_WEAPON_EXP = 14;
        public static final int ITEM_USE_GAIN_AVATAR = 3;
        public static final int ITEM_USE_NONE = 0;
        public static final int ITEM_USE_TRIGGER_ABILITY = 2;
        public static final int ITEM_USE_UNLOCK_COOK_RECIPE = 17;
    }

    public static class ItemUseTarget {
        public static final int ITEM_USE_TARGET_NONE = 0;
        public static final int ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR = 4;
        public static final int ITEM_USE_TARGET_SPECIFY_AVATAR = 3;
        public static final int ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR = 5;
        public static final int ITEM_USE_TARGET_CUR_AVATAR = 1;
        public static final int ITEM_USE_TARGET_CUR_TEAM = 2;
    }

    public static class MaterialExpireType {
        public static final int CountDown = 1;
        public static final int DateTime = 2;
    }
}
