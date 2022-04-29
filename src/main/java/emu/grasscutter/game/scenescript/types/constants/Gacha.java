package emu.grasscutter.game.scenescript.types.constants;

public class Gacha {
    public static class GachaGuaranteeResetType {
        public static final int GACHA_GUARANTEE_RESET_ACTIVITY_CHANGE = 1;
        public static final int GACHA_GUARANTEE_RESET_NONE = 0;
    }

    public static class GachaGuaranteeType {
        public static final int GACHA_GUARANTEE_SPECIFIED_COUNT = 1;
        public static final int GACHA_GUARANTEE_LOOP_COUNT = 2;
        public static final int GACHA_GUARANTEE_LOOP_COUNT_WITH_CHILDS = 4;
        public static final int GACHA_GUARANTEE_NONE = 0;
        public static final int GACHA_GUARANTEE_N_COUNT = 3;
        public static final int GACHA_GUARANTEE_N_COUNT_WITH_CHILDS = 5;
    }

    public static class GachaItemParentType {
        public static final int GACHA_ITEM_PARENT_S4 = 2;
        public static final int GACHA_ITEM_PARENT_S5 = 1;
        public static final int GACHA_ITEM_PARENT_INVALID = 0;
        public static final int GACHA_ITEM_PARENT_S3 = 3;
    }

    public static class GachaItemType {
        public static final int GACHA_ITEM_WEAPON_S5 = 21;
        public static final int GACHA_ITEM_AVATAR_S3 = 13;
        public static final int GACHA_ITEM_AVATAR_S4 = 12;
        public static final int GACHA_ITEM_AVATAR_S5 = 11;
        public static final int GACHA_ITEM_COMMON_MATERIAL = 31;
        public static final int GACHA_ITEM_INVALID = 0;
        public static final int GACHA_ITEM_WEAPON_S3 = 23;
        public static final int GACHA_ITEM_WEAPON_S4 = 22;
    }

    public static class GachaType {
        public static final int GACHA_TYPE_ACTIVITY_WEAPON = 302;
        public static final int GACHA_TYPE_NEWBIE = 100;
        public static final int GACHA_TYPE_STANDARD = 200;
        public static final int GACHA_TYPE_STANDARD_AVATAR = 201;
        public static final int GACHA_TYPE_STANDARD_WEAPON = 202;
        public static final int GACHA_TYPE_ACTIVITY = 300;
        public static final int GACHA_TYPE_ACTIVITY_AVATAR = 301;
    }
}
