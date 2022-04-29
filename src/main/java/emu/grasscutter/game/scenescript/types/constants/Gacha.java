package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Gacha {
	public static class GachaItemParentType extends TwoArgFunction {
		public static final int GACHA_ITEM_PARENT_INVALID = 0;
		public static final int GACHA_ITEM_PARENT_S5 = 1;
		public static final int GACHA_ITEM_PARENT_S4 = 2;
		public static final int GACHA_ITEM_PARENT_S3 = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGacha = new LuaTable();
			scriptConstGacha.set("GACHA_ITEM_PARENT_INVALID", LuaValue.valueOf(GACHA_ITEM_PARENT_INVALID));
			scriptConstGacha.set("GACHA_ITEM_PARENT_S5", LuaValue.valueOf(GACHA_ITEM_PARENT_S5));
			scriptConstGacha.set("GACHA_ITEM_PARENT_S4", LuaValue.valueOf(GACHA_ITEM_PARENT_S4));
			scriptConstGacha.set("GACHA_ITEM_PARENT_S3", LuaValue.valueOf(GACHA_ITEM_PARENT_S3));
			env.set("GachaItemParentType", scriptConstGacha);
			env.get("package").get("loaded").set("GachaItemParentType", scriptConstGacha);
			return env;
		}
	}
	public static class GachaItemType extends TwoArgFunction {
		public static final int GACHA_ITEM_INVALID = 0;
		public static final int GACHA_ITEM_AVATAR_S5 = 11;
		public static final int GACHA_ITEM_AVATAR_S4 = 12;
		public static final int GACHA_ITEM_AVATAR_S3 = 13;
		public static final int GACHA_ITEM_WEAPON_S5 = 21;
		public static final int GACHA_ITEM_WEAPON_S4 = 22;
		public static final int GACHA_ITEM_WEAPON_S3 = 23;
		public static final int GACHA_ITEM_COMMON_MATERIAL = 31;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGacha = new LuaTable();
			scriptConstGacha.set("GACHA_ITEM_INVALID", LuaValue.valueOf(GACHA_ITEM_INVALID));
			scriptConstGacha.set("GACHA_ITEM_AVATAR_S5", LuaValue.valueOf(GACHA_ITEM_AVATAR_S5));
			scriptConstGacha.set("GACHA_ITEM_AVATAR_S4", LuaValue.valueOf(GACHA_ITEM_AVATAR_S4));
			scriptConstGacha.set("GACHA_ITEM_AVATAR_S3", LuaValue.valueOf(GACHA_ITEM_AVATAR_S3));
			scriptConstGacha.set("GACHA_ITEM_WEAPON_S5", LuaValue.valueOf(GACHA_ITEM_WEAPON_S5));
			scriptConstGacha.set("GACHA_ITEM_WEAPON_S4", LuaValue.valueOf(GACHA_ITEM_WEAPON_S4));
			scriptConstGacha.set("GACHA_ITEM_WEAPON_S3", LuaValue.valueOf(GACHA_ITEM_WEAPON_S3));
			scriptConstGacha.set("GACHA_ITEM_COMMON_MATERIAL", LuaValue.valueOf(GACHA_ITEM_COMMON_MATERIAL));
			env.set("GachaItemType", scriptConstGacha);
			env.get("package").get("loaded").set("GachaItemType", scriptConstGacha);
			return env;
		}
	}
	public static class GachaType extends TwoArgFunction {
		public static final int GACHA_TYPE_NEWBIE = 100;
		public static final int GACHA_TYPE_STANDARD = 200;
		public static final int GACHA_TYPE_STANDARD_AVATAR = 201;
		public static final int GACHA_TYPE_STANDARD_WEAPON = 202;
		public static final int GACHA_TYPE_ACTIVITY = 300;
		public static final int GACHA_TYPE_ACTIVITY_AVATAR = 301;
		public static final int GACHA_TYPE_ACTIVITY_WEAPON = 302;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGacha = new LuaTable();
			scriptConstGacha.set("GACHA_TYPE_NEWBIE", LuaValue.valueOf(GACHA_TYPE_NEWBIE));
			scriptConstGacha.set("GACHA_TYPE_STANDARD", LuaValue.valueOf(GACHA_TYPE_STANDARD));
			scriptConstGacha.set("GACHA_TYPE_STANDARD_AVATAR", LuaValue.valueOf(GACHA_TYPE_STANDARD_AVATAR));
			scriptConstGacha.set("GACHA_TYPE_STANDARD_WEAPON", LuaValue.valueOf(GACHA_TYPE_STANDARD_WEAPON));
			scriptConstGacha.set("GACHA_TYPE_ACTIVITY", LuaValue.valueOf(GACHA_TYPE_ACTIVITY));
			scriptConstGacha.set("GACHA_TYPE_ACTIVITY_AVATAR", LuaValue.valueOf(GACHA_TYPE_ACTIVITY_AVATAR));
			scriptConstGacha.set("GACHA_TYPE_ACTIVITY_WEAPON", LuaValue.valueOf(GACHA_TYPE_ACTIVITY_WEAPON));
			env.set("GachaType", scriptConstGacha);
			env.get("package").get("loaded").set("GachaType", scriptConstGacha);
			return env;
		}
	}
	public static class GachaGuaranteeResetType extends TwoArgFunction {
		public static final int GACHA_GUARANTEE_RESET_NONE = 0;
		public static final int GACHA_GUARANTEE_RESET_ACTIVITY_CHANGE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGacha = new LuaTable();
			scriptConstGacha.set("GACHA_GUARANTEE_RESET_NONE", LuaValue.valueOf(GACHA_GUARANTEE_RESET_NONE));
			scriptConstGacha.set("GACHA_GUARANTEE_RESET_ACTIVITY_CHANGE", LuaValue.valueOf(GACHA_GUARANTEE_RESET_ACTIVITY_CHANGE));
			env.set("GachaGuaranteeResetType", scriptConstGacha);
			env.get("package").get("loaded").set("GachaGuaranteeResetType", scriptConstGacha);
			return env;
		}
	}
	public static class GachaGuaranteeType extends TwoArgFunction {
		public static final int GACHA_GUARANTEE_NONE = 0;
		public static final int GACHA_GUARANTEE_SPECIFIED_COUNT = 1;
		public static final int GACHA_GUARANTEE_LOOP_COUNT = 2;
		public static final int GACHA_GUARANTEE_N_COUNT = 3;
		public static final int GACHA_GUARANTEE_LOOP_COUNT_WITH_CHILDS = 4;
		public static final int GACHA_GUARANTEE_N_COUNT_WITH_CHILDS = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstGacha = new LuaTable();
			scriptConstGacha.set("GACHA_GUARANTEE_NONE", LuaValue.valueOf(GACHA_GUARANTEE_NONE));
			scriptConstGacha.set("GACHA_GUARANTEE_SPECIFIED_COUNT", LuaValue.valueOf(GACHA_GUARANTEE_SPECIFIED_COUNT));
			scriptConstGacha.set("GACHA_GUARANTEE_LOOP_COUNT", LuaValue.valueOf(GACHA_GUARANTEE_LOOP_COUNT));
			scriptConstGacha.set("GACHA_GUARANTEE_N_COUNT", LuaValue.valueOf(GACHA_GUARANTEE_N_COUNT));
			scriptConstGacha.set("GACHA_GUARANTEE_LOOP_COUNT_WITH_CHILDS", LuaValue.valueOf(GACHA_GUARANTEE_LOOP_COUNT_WITH_CHILDS));
			scriptConstGacha.set("GACHA_GUARANTEE_N_COUNT_WITH_CHILDS", LuaValue.valueOf(GACHA_GUARANTEE_N_COUNT_WITH_CHILDS));
			env.set("GachaGuaranteeType", scriptConstGacha);
			env.get("package").get("loaded").set("GachaGuaranteeType", scriptConstGacha);
			return env;
		}
	}
}
