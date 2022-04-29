package emu.grasscutter.game.scenescript.types.constants;

public class AvatarTalent {
    public static class EffectTargetType {
        public static final int EFFECT_TARGET_NONE = 0;
        public static final int EFFECT_TARGET_SOLO = 1;
        public static final int EFFECT_TARGET_TEAM = 2;
    }

    public static class ProudLifeEffectType {
        public static final int PROUD_EFFECT_COMBINE_EXTRA_OUTPUT = 3;
        public static final int PROUD_EFFECT_COMBINE_MULTIPLY_OUTPUT = 2;
        public static final int PROUD_EFFECT_COOK_WIDEN_JUDGE_AREA = 102;
        public static final int PROUD_EFFECT_FORGE_ADD_EXTRA_PROB = 204;
        public static final int PROUD_EFFECT_NONE = 0;
        public static final int PROUD_EFFECT_COMBINE_ADD_EXTRA_PROB = 4;
        public static final int PROUD_EFFECT_COMBINE_RETURN_MATERIAL = 1;
        public static final int PROUD_EFFECT_COOK_ADD_BONUS_PROB = 103;
        public static final int PROUD_EFFECT_COOK_EXTRA_PROFICIENCY = 101;
        public static final int PROUD_EFFECT_FORGE_REDUCE_TIME = 203;
    }

    public static class ProudSkillType {
        public static final int PROUD_SKILL_NONE = 0;
        public static final int PROUD_SKILL_ACTIVE = 3;
        public static final int PROUD_SKILL_CORE = 1;
        public static final int PROUD_SKILL_INHERENT = 2;
    }

    public static class TalentFilterCond {
        public static final int TALENT_FILTER_GRASS_AVATAR = 4;
        public static final int TALENT_FILTER_ICE_AVATAR = 6;
        public static final int TALENT_FILTER_NONE = 0;
        public static final int TALENT_FILTER_ROCK_AVATAR = 7;
        public static final int TALENT_FILTER_WATER_AVATAR = 3;
        public static final int TALENT_FILTER_WIND_AVATAR = 5;
        public static final int TALENT_FILTER_ELEC_AVATAR = 2;
        public static final int TALENT_FILTER_FIRE_AVATAR = 1;
    }

    public static class TalentPointType {
        public static final int TALENT_POINT_NONE = 0;
        public static final int TALENT_POINT_SMALL = 1;
        public static final int TALENT_POINT_BIG = 2;
    }
}
