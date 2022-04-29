package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Cook {
	public static class CookBonusType extends TwoArgFunction {
		public static final int COOK_BONUS_NONE = 0;
		public static final int COOK_BONUS_REPLACE = 1;
		public static final int COOK_BONUS_PROFICIENCY = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCook = new LuaTable();
			scriptConstCook.set("COOK_BONUS_NONE", LuaValue.valueOf(COOK_BONUS_NONE));
			scriptConstCook.set("COOK_BONUS_REPLACE", LuaValue.valueOf(COOK_BONUS_REPLACE));
			scriptConstCook.set("COOK_BONUS_PROFICIENCY", LuaValue.valueOf(COOK_BONUS_PROFICIENCY));
			env.set("CookBonusType", scriptConstCook);
			env.get("package").get("loaded").set("CookBonusType", scriptConstCook);
			return env;
		}
	}
	public static class CookFoodType extends TwoArgFunction {
		public static final int COOK_FOOD_NONE = 0;
		public static final int COOK_FOOD_HEAL = 1;
		public static final int COOK_FOOD_ATTACK = 2;
		public static final int COOK_FOOD_FUNCTION = 3;
		public static final int COOK_FOOD_DEFENSE = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCook = new LuaTable();
			scriptConstCook.set("COOK_FOOD_NONE", LuaValue.valueOf(COOK_FOOD_NONE));
			scriptConstCook.set("COOK_FOOD_HEAL", LuaValue.valueOf(COOK_FOOD_HEAL));
			scriptConstCook.set("COOK_FOOD_ATTACK", LuaValue.valueOf(COOK_FOOD_ATTACK));
			scriptConstCook.set("COOK_FOOD_FUNCTION", LuaValue.valueOf(COOK_FOOD_FUNCTION));
			scriptConstCook.set("COOK_FOOD_DEFENSE", LuaValue.valueOf(COOK_FOOD_DEFENSE));
			env.set("CookFoodType", scriptConstCook);
			env.get("package").get("loaded").set("CookFoodType", scriptConstCook);
			return env;
		}
	}
	public static class CookMethodType extends TwoArgFunction {
		public static final int COOK_METHOD_NONE = 0;
		public static final int COOK_METHOD_STEAM = 1;
		public static final int COOK_METHOD_BOIL = 2;
		public static final int COOK_METHOD_FRY = 3;
		public static final int COOK_METHOD_BAKE = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCook = new LuaTable();
			scriptConstCook.set("COOK_METHOD_NONE", LuaValue.valueOf(COOK_METHOD_NONE));
			scriptConstCook.set("COOK_METHOD_STEAM", LuaValue.valueOf(COOK_METHOD_STEAM));
			scriptConstCook.set("COOK_METHOD_BOIL", LuaValue.valueOf(COOK_METHOD_BOIL));
			scriptConstCook.set("COOK_METHOD_FRY", LuaValue.valueOf(COOK_METHOD_FRY));
			scriptConstCook.set("COOK_METHOD_BAKE", LuaValue.valueOf(COOK_METHOD_BAKE));
			env.set("CookMethodType", scriptConstCook);
			env.get("package").get("loaded").set("CookMethodType", scriptConstCook);
			return env;
		}
	}
}
