package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class AvatarTalent {
	public static class EffectTargetType extends TwoArgFunction {
		public static final int EFFECT_TARGET_NONE = 0;
		public static final int EFFECT_TARGET_SOLO = 1;
		public static final int EFFECT_TARGET_TEAM = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarTalent = new LuaTable();
			scriptConstAvatarTalent.set("EFFECT_TARGET_NONE", LuaValue.valueOf(EFFECT_TARGET_NONE));
			scriptConstAvatarTalent.set("EFFECT_TARGET_SOLO", LuaValue.valueOf(EFFECT_TARGET_SOLO));
			scriptConstAvatarTalent.set("EFFECT_TARGET_TEAM", LuaValue.valueOf(EFFECT_TARGET_TEAM));
			env.set("EffectTargetType", scriptConstAvatarTalent);
			env.get("package").get("loaded").set("EffectTargetType", scriptConstAvatarTalent);
			return env;
		}
	}
	public static class ProudLifeEffectType extends TwoArgFunction {
		public static final int PROUD_EFFECT_NONE = 0;
		public static final int PROUD_EFFECT_COMBINE_RETURN_MATERIAL = 1;
		public static final int PROUD_EFFECT_COMBINE_MULTIPLY_OUTPUT = 2;
		public static final int PROUD_EFFECT_COMBINE_EXTRA_OUTPUT = 3;
		public static final int PROUD_EFFECT_COMBINE_ADD_EXTRA_PROB = 4;
		public static final int PROUD_EFFECT_COOK_EXTRA_PROFICIENCY = 101;
		public static final int PROUD_EFFECT_COOK_WIDEN_JUDGE_AREA = 102;
		public static final int PROUD_EFFECT_COOK_ADD_BONUS_PROB = 103;
		public static final int PROUD_EFFECT_FORGE_REDUCE_TIME = 203;
		public static final int PROUD_EFFECT_FORGE_ADD_EXTRA_PROB = 204;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarTalent = new LuaTable();
			scriptConstAvatarTalent.set("PROUD_EFFECT_NONE", LuaValue.valueOf(PROUD_EFFECT_NONE));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COMBINE_RETURN_MATERIAL", LuaValue.valueOf(PROUD_EFFECT_COMBINE_RETURN_MATERIAL));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COMBINE_MULTIPLY_OUTPUT", LuaValue.valueOf(PROUD_EFFECT_COMBINE_MULTIPLY_OUTPUT));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COMBINE_EXTRA_OUTPUT", LuaValue.valueOf(PROUD_EFFECT_COMBINE_EXTRA_OUTPUT));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COMBINE_ADD_EXTRA_PROB", LuaValue.valueOf(PROUD_EFFECT_COMBINE_ADD_EXTRA_PROB));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COOK_EXTRA_PROFICIENCY", LuaValue.valueOf(PROUD_EFFECT_COOK_EXTRA_PROFICIENCY));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COOK_WIDEN_JUDGE_AREA", LuaValue.valueOf(PROUD_EFFECT_COOK_WIDEN_JUDGE_AREA));
			scriptConstAvatarTalent.set("PROUD_EFFECT_COOK_ADD_BONUS_PROB", LuaValue.valueOf(PROUD_EFFECT_COOK_ADD_BONUS_PROB));
			scriptConstAvatarTalent.set("PROUD_EFFECT_FORGE_REDUCE_TIME", LuaValue.valueOf(PROUD_EFFECT_FORGE_REDUCE_TIME));
			scriptConstAvatarTalent.set("PROUD_EFFECT_FORGE_ADD_EXTRA_PROB", LuaValue.valueOf(PROUD_EFFECT_FORGE_ADD_EXTRA_PROB));
			env.set("ProudLifeEffectType", scriptConstAvatarTalent);
			env.get("package").get("loaded").set("ProudLifeEffectType", scriptConstAvatarTalent);
			return env;
		}
	}
	public static class ProudSkillType extends TwoArgFunction {
		public static final int PROUD_SKILL_NONE = 0;
		public static final int PROUD_SKILL_CORE = 1;
		public static final int PROUD_SKILL_INHERENT = 2;
		public static final int PROUD_SKILL_ACTIVE = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarTalent = new LuaTable();
			scriptConstAvatarTalent.set("PROUD_SKILL_NONE", LuaValue.valueOf(PROUD_SKILL_NONE));
			scriptConstAvatarTalent.set("PROUD_SKILL_CORE", LuaValue.valueOf(PROUD_SKILL_CORE));
			scriptConstAvatarTalent.set("PROUD_SKILL_INHERENT", LuaValue.valueOf(PROUD_SKILL_INHERENT));
			scriptConstAvatarTalent.set("PROUD_SKILL_ACTIVE", LuaValue.valueOf(PROUD_SKILL_ACTIVE));
			env.set("ProudSkillType", scriptConstAvatarTalent);
			env.get("package").get("loaded").set("ProudSkillType", scriptConstAvatarTalent);
			return env;
		}
	}
	public static class TalentFilterCond extends TwoArgFunction {
		public static final int TALENT_FILTER_NONE = 0;
		public static final int TALENT_FILTER_FIRE_AVATAR = 1;
		public static final int TALENT_FILTER_ELEC_AVATAR = 2;
		public static final int TALENT_FILTER_WATER_AVATAR = 3;
		public static final int TALENT_FILTER_GRASS_AVATAR = 4;
		public static final int TALENT_FILTER_WIND_AVATAR = 5;
		public static final int TALENT_FILTER_ICE_AVATAR = 6;
		public static final int TALENT_FILTER_ROCK_AVATAR = 7;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarTalent = new LuaTable();
			scriptConstAvatarTalent.set("TALENT_FILTER_NONE", LuaValue.valueOf(TALENT_FILTER_NONE));
			scriptConstAvatarTalent.set("TALENT_FILTER_FIRE_AVATAR", LuaValue.valueOf(TALENT_FILTER_FIRE_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_ELEC_AVATAR", LuaValue.valueOf(TALENT_FILTER_ELEC_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_WATER_AVATAR", LuaValue.valueOf(TALENT_FILTER_WATER_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_GRASS_AVATAR", LuaValue.valueOf(TALENT_FILTER_GRASS_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_WIND_AVATAR", LuaValue.valueOf(TALENT_FILTER_WIND_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_ICE_AVATAR", LuaValue.valueOf(TALENT_FILTER_ICE_AVATAR));
			scriptConstAvatarTalent.set("TALENT_FILTER_ROCK_AVATAR", LuaValue.valueOf(TALENT_FILTER_ROCK_AVATAR));
			env.set("TalentFilterCond", scriptConstAvatarTalent);
			env.get("package").get("loaded").set("TalentFilterCond", scriptConstAvatarTalent);
			return env;
		}
	}
	public static class TalentPointType extends TwoArgFunction {
		public static final int TALENT_POINT_NONE = 0;
		public static final int TALENT_POINT_SMALL = 1;
		public static final int TALENT_POINT_BIG = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatarTalent = new LuaTable();
			scriptConstAvatarTalent.set("TALENT_POINT_NONE", LuaValue.valueOf(TALENT_POINT_NONE));
			scriptConstAvatarTalent.set("TALENT_POINT_SMALL", LuaValue.valueOf(TALENT_POINT_SMALL));
			scriptConstAvatarTalent.set("TALENT_POINT_BIG", LuaValue.valueOf(TALENT_POINT_BIG));
			env.set("TalentPointType", scriptConstAvatarTalent);
			env.get("package").get("loaded").set("TalentPointType", scriptConstAvatarTalent);
			return env;
		}
	}
}
