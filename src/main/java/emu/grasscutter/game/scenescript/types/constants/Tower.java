package emu.grasscutter.game.scenescript.types.constants;

public class Tower {
    public static class TowerCondType {
        public static final int TOWER_COND_CHALLENGE_LEFT_TIME_MORE_THAN = 3;
        public static final int TOWER_COND_FINISH_TIME_LESS_THAN = 1;
        public static final int TOWER_COND_LEFT_HP_GREATER_THAN = 2;
        public static final int TOWER_COND_NONE = 0;
    }

    public static class TowerBuffLastingType {
        public static final int TOWER_BUFF_LASTING_FLOOR = 1;
        public static final int TOWER_BUFF_LASTING_IMMEDIATE = 2;
        public static final int TOWER_BUFF_LASTING_LEVEL = 3;
        public static final int TOWER_BUFF_LASTING_NONE = 0;
    }
}
