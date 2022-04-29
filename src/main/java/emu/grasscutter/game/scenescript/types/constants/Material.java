package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Material {
	public static class DocumentType extends TwoArgFunction {
		public static final int Book = 0;
		public static final int Video = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMaterial = new LuaTable();
			scriptConstMaterial.set("Book", LuaValue.valueOf(Book));
			scriptConstMaterial.set("Video", LuaValue.valueOf(Video));
			env.set("DocumentType", scriptConstMaterial);
			env.get("package").get("loaded").set("DocumentType", scriptConstMaterial);
			return env;
		}
	}
	public static class FoodQualityType extends TwoArgFunction {
		public static final int FOOD_QUALITY_NONE = 0;
		public static final int FOOD_QUALITY_STRANGE = 1;
		public static final int FOOD_QUALITY_ORDINARY = 2;
		public static final int FOOD_QUALITY_DELICIOUS = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMaterial = new LuaTable();
			scriptConstMaterial.set("FOOD_QUALITY_NONE", LuaValue.valueOf(FOOD_QUALITY_NONE));
			scriptConstMaterial.set("FOOD_QUALITY_STRANGE", LuaValue.valueOf(FOOD_QUALITY_STRANGE));
			scriptConstMaterial.set("FOOD_QUALITY_ORDINARY", LuaValue.valueOf(FOOD_QUALITY_ORDINARY));
			scriptConstMaterial.set("FOOD_QUALITY_DELICIOUS", LuaValue.valueOf(FOOD_QUALITY_DELICIOUS));
			env.set("FoodQualityType", scriptConstMaterial);
			env.get("package").get("loaded").set("FoodQualityType", scriptConstMaterial);
			return env;
		}
	}
	public static class ItemUseOp extends TwoArgFunction {
		public static final int ITEM_USE_NONE = 0;
		public static final int ITEM_USE_ACCEPT_QUEST = 1;
		public static final int ITEM_USE_TRIGGER_ABILITY = 2;
		public static final int ITEM_USE_GAIN_AVATAR = 3;
		public static final int ITEM_USE_ADD_EXP = 4;
		public static final int ITEM_USE_RELIVE_AVATAR = 5;
		public static final int ITEM_USE_ADD_BIG_TALENT_POINT = 6;
		public static final int ITEM_USE_ADD_PERSIST_STAMINA = 7;
		public static final int ITEM_USE_ADD_TEMPORARY_STAMINA = 8;
		public static final int ITEM_USE_ADD_CUR_STAMINA = 9;
		public static final int ITEM_USE_ADD_CUR_HP = 10;
		public static final int ITEM_USE_ADD_ELEM_ENERGY = 11;
		public static final int ITEM_USE_ADD_ALL_ENERGY = 12;
		public static final int ITEM_USE_ADD_DUNGEON_COND_TIME = 13;
		public static final int ITEM_USE_ADD_WEAPON_EXP = 14;
		public static final int ITEM_USE_ADD_SERVER_BUFF = 15;
		public static final int ITEM_USE_DEL_SERVER_BUFF = 16;
		public static final int ITEM_USE_UNLOCK_COOK_RECIPE = 17;
		public static final int ITEM_USE_ADD_AVATAR_BIG_TALENT_POINT = 18;
		public static final int ITEM_USE_ADD_AVATAR_SMALL_TALENT_POINT = 19;
		public static final int ITEM_USE_OPEN_RANDOM_CHEST = 20;
		public static final int ITEM_USE_ADD_MAIN_AVATAR_BIG_TALENT_POINT = 22;
		public static final int ITEM_USE_ADD_MAIN_AVATAR_SMALL_TALENT_POINT = 23;
		public static final int ITEM_USE_MAKE_GADGET = 24;
		public static final int ITEM_USE_ADD_ITEM = 25;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMaterial = new LuaTable();
			scriptConstMaterial.set("ITEM_USE_NONE", LuaValue.valueOf(ITEM_USE_NONE));
			scriptConstMaterial.set("ITEM_USE_ACCEPT_QUEST", LuaValue.valueOf(ITEM_USE_ACCEPT_QUEST));
			scriptConstMaterial.set("ITEM_USE_TRIGGER_ABILITY", LuaValue.valueOf(ITEM_USE_TRIGGER_ABILITY));
			scriptConstMaterial.set("ITEM_USE_GAIN_AVATAR", LuaValue.valueOf(ITEM_USE_GAIN_AVATAR));
			scriptConstMaterial.set("ITEM_USE_ADD_EXP", LuaValue.valueOf(ITEM_USE_ADD_EXP));
			scriptConstMaterial.set("ITEM_USE_RELIVE_AVATAR", LuaValue.valueOf(ITEM_USE_RELIVE_AVATAR));
			scriptConstMaterial.set("ITEM_USE_ADD_BIG_TALENT_POINT", LuaValue.valueOf(ITEM_USE_ADD_BIG_TALENT_POINT));
			scriptConstMaterial.set("ITEM_USE_ADD_PERSIST_STAMINA", LuaValue.valueOf(ITEM_USE_ADD_PERSIST_STAMINA));
			scriptConstMaterial.set("ITEM_USE_ADD_TEMPORARY_STAMINA", LuaValue.valueOf(ITEM_USE_ADD_TEMPORARY_STAMINA));
			scriptConstMaterial.set("ITEM_USE_ADD_CUR_STAMINA", LuaValue.valueOf(ITEM_USE_ADD_CUR_STAMINA));
			scriptConstMaterial.set("ITEM_USE_ADD_CUR_HP", LuaValue.valueOf(ITEM_USE_ADD_CUR_HP));
			scriptConstMaterial.set("ITEM_USE_ADD_ELEM_ENERGY", LuaValue.valueOf(ITEM_USE_ADD_ELEM_ENERGY));
			scriptConstMaterial.set("ITEM_USE_ADD_ALL_ENERGY", LuaValue.valueOf(ITEM_USE_ADD_ALL_ENERGY));
			scriptConstMaterial.set("ITEM_USE_ADD_DUNGEON_COND_TIME", LuaValue.valueOf(ITEM_USE_ADD_DUNGEON_COND_TIME));
			scriptConstMaterial.set("ITEM_USE_ADD_WEAPON_EXP", LuaValue.valueOf(ITEM_USE_ADD_WEAPON_EXP));
			scriptConstMaterial.set("ITEM_USE_ADD_SERVER_BUFF", LuaValue.valueOf(ITEM_USE_ADD_SERVER_BUFF));
			scriptConstMaterial.set("ITEM_USE_DEL_SERVER_BUFF", LuaValue.valueOf(ITEM_USE_DEL_SERVER_BUFF));
			scriptConstMaterial.set("ITEM_USE_UNLOCK_COOK_RECIPE", LuaValue.valueOf(ITEM_USE_UNLOCK_COOK_RECIPE));
			scriptConstMaterial.set("ITEM_USE_ADD_AVATAR_BIG_TALENT_POINT", LuaValue.valueOf(ITEM_USE_ADD_AVATAR_BIG_TALENT_POINT));
			scriptConstMaterial.set("ITEM_USE_ADD_AVATAR_SMALL_TALENT_POINT", LuaValue.valueOf(ITEM_USE_ADD_AVATAR_SMALL_TALENT_POINT));
			scriptConstMaterial.set("ITEM_USE_OPEN_RANDOM_CHEST", LuaValue.valueOf(ITEM_USE_OPEN_RANDOM_CHEST));
			scriptConstMaterial.set("ITEM_USE_ADD_MAIN_AVATAR_BIG_TALENT_POINT", LuaValue.valueOf(ITEM_USE_ADD_MAIN_AVATAR_BIG_TALENT_POINT));
			scriptConstMaterial.set("ITEM_USE_ADD_MAIN_AVATAR_SMALL_TALENT_POINT", LuaValue.valueOf(ITEM_USE_ADD_MAIN_AVATAR_SMALL_TALENT_POINT));
			scriptConstMaterial.set("ITEM_USE_MAKE_GADGET", LuaValue.valueOf(ITEM_USE_MAKE_GADGET));
			scriptConstMaterial.set("ITEM_USE_ADD_ITEM", LuaValue.valueOf(ITEM_USE_ADD_ITEM));
			env.set("ItemUseOp", scriptConstMaterial);
			env.get("package").get("loaded").set("ItemUseOp", scriptConstMaterial);
			return env;
		}
	}
	public static class ItemUseTarget extends TwoArgFunction {
		public static final int ITEM_USE_TARGET_NONE = 0;
		public static final int ITEM_USE_TARGET_CUR_AVATAR = 1;
		public static final int ITEM_USE_TARGET_CUR_TEAM = 2;
		public static final int ITEM_USE_TARGET_SPECIFY_AVATAR = 3;
		public static final int ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR = 4;
		public static final int ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMaterial = new LuaTable();
			scriptConstMaterial.set("ITEM_USE_TARGET_NONE", LuaValue.valueOf(ITEM_USE_TARGET_NONE));
			scriptConstMaterial.set("ITEM_USE_TARGET_CUR_AVATAR", LuaValue.valueOf(ITEM_USE_TARGET_CUR_AVATAR));
			scriptConstMaterial.set("ITEM_USE_TARGET_CUR_TEAM", LuaValue.valueOf(ITEM_USE_TARGET_CUR_TEAM));
			scriptConstMaterial.set("ITEM_USE_TARGET_SPECIFY_AVATAR", LuaValue.valueOf(ITEM_USE_TARGET_SPECIFY_AVATAR));
			scriptConstMaterial.set("ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR", LuaValue.valueOf(ITEM_USE_TARGET_SPECIFY_ALIVE_AVATAR));
			scriptConstMaterial.set("ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR", LuaValue.valueOf(ITEM_USE_TARGET_SPECIFY_DEAD_AVATAR));
			env.set("ItemUseTarget", scriptConstMaterial);
			env.get("package").get("loaded").set("ItemUseTarget", scriptConstMaterial);
			return env;
		}
	}
	public static class MaterialExpireType extends TwoArgFunction {
		public static final int CountDown = 1;
		public static final int DateTime = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMaterial = new LuaTable();
			scriptConstMaterial.set("CountDown", LuaValue.valueOf(CountDown));
			scriptConstMaterial.set("DateTime", LuaValue.valueOf(DateTime));
			env.set("MaterialExpireType", scriptConstMaterial);
			env.get("package").get("loaded").set("MaterialExpireType", scriptConstMaterial);
			return env;
		}
	}
}
