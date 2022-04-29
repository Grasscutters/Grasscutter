package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Equip {
	public static class AffixEffect extends TwoArgFunction {
		public static final int AFFIX_NONE = 0;
		public static final int AFFIX_ADD_ATTACK = 1;
		public static final int AFFIX_ADD_ATTACK_PERCENT = 2;
		public static final int AFFIX_ADD_DEFENSE = 3;
		public static final int AFFIX_ADD_DEFENSE_PERCENT = 4;
		public static final int AFFIX_ADD_MAX_HP = 5;
		public static final int AFFIX_ADD_MAX_HP_PERCENT = 6;
		public static final int AFFIX_ADD_MAX_STAMINA = 7;
		public static final int AFFIX_ADD_MAX_STAMINA_PERCENT = 8;
		public static final int AFFIX_ADD_STRIKE_PERCENT = 9;
		public static final int AFFIX_ADD_ANTI_STRIKE_PERCENT = 10;
		public static final int AFFIX_ADD_STRIKE_HURT_PERCENT = 11;
		public static final int AFFIX_ADD_NO_PROP_HURT_PERCENT = 12;
		public static final int AFFIX_SUB_NO_PROP_HURT_PERCENT = 13;
		public static final int AFFIX_ADD_FIRE_HURT_PERCENT = 14;
		public static final int AFFIX_SUB_FIRE_HURT_PERCENT = 15;
		public static final int AFFIX_ADD_WATER_HURT_PERCENT = 16;
		public static final int AFFIX_SUB_WATER_HURT_PERCENT = 17;
		public static final int AFFIX_ADD_GRASS_HURT_PERCENT = 18;
		public static final int AFFIX_SUB_GRASS_HURT_PERCENT = 19;
		public static final int AFFIX_ADD_ELEC_HURT_PERCENT = 20;
		public static final int AFFIX_SUB_ELEC_HURT_PERCENT = 21;
		public static final int AFFIX_ADD_WIND_HURT_PERCENT = 22;
		public static final int AFFIX_SUB_WIND_HURT_PERCENT = 23;
		public static final int AFFIX_SUB_SKILL_CD_PERCENT = 24;
		public static final int AFFIX_HP_RECOVER_PER_SECOND = 25;
		public static final int AFFIX_ADD_SPEED_PERCENT = 26;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstEquip = new LuaTable();
			scriptConstEquip.set("AFFIX_NONE", LuaValue.valueOf(AFFIX_NONE));
			scriptConstEquip.set("AFFIX_ADD_ATTACK", LuaValue.valueOf(AFFIX_ADD_ATTACK));
			scriptConstEquip.set("AFFIX_ADD_ATTACK_PERCENT", LuaValue.valueOf(AFFIX_ADD_ATTACK_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_DEFENSE", LuaValue.valueOf(AFFIX_ADD_DEFENSE));
			scriptConstEquip.set("AFFIX_ADD_DEFENSE_PERCENT", LuaValue.valueOf(AFFIX_ADD_DEFENSE_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_MAX_HP", LuaValue.valueOf(AFFIX_ADD_MAX_HP));
			scriptConstEquip.set("AFFIX_ADD_MAX_HP_PERCENT", LuaValue.valueOf(AFFIX_ADD_MAX_HP_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_MAX_STAMINA", LuaValue.valueOf(AFFIX_ADD_MAX_STAMINA));
			scriptConstEquip.set("AFFIX_ADD_MAX_STAMINA_PERCENT", LuaValue.valueOf(AFFIX_ADD_MAX_STAMINA_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_STRIKE_PERCENT", LuaValue.valueOf(AFFIX_ADD_STRIKE_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_ANTI_STRIKE_PERCENT", LuaValue.valueOf(AFFIX_ADD_ANTI_STRIKE_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_STRIKE_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_STRIKE_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_NO_PROP_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_NO_PROP_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_NO_PROP_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_NO_PROP_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_FIRE_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_FIRE_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_FIRE_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_FIRE_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_WATER_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_WATER_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_WATER_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_WATER_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_GRASS_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_GRASS_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_GRASS_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_GRASS_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_ELEC_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_ELEC_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_ELEC_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_ELEC_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_ADD_WIND_HURT_PERCENT", LuaValue.valueOf(AFFIX_ADD_WIND_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_WIND_HURT_PERCENT", LuaValue.valueOf(AFFIX_SUB_WIND_HURT_PERCENT));
			scriptConstEquip.set("AFFIX_SUB_SKILL_CD_PERCENT", LuaValue.valueOf(AFFIX_SUB_SKILL_CD_PERCENT));
			scriptConstEquip.set("AFFIX_HP_RECOVER_PER_SECOND", LuaValue.valueOf(AFFIX_HP_RECOVER_PER_SECOND));
			scriptConstEquip.set("AFFIX_ADD_SPEED_PERCENT", LuaValue.valueOf(AFFIX_ADD_SPEED_PERCENT));
			env.set("AffixEffect", scriptConstEquip);
			env.get("package").get("loaded").set("AffixEffect", scriptConstEquip);
			return env;
		}
	}
	public static class EquipSlotType extends TwoArgFunction {
		public static final int EQUIP_SLOT_NONE = 0;
		public static final int EQUIP_SLOT_WEAPON = 1;
		public static final int EQUIP_SLOT_BRACER = 2;
		public static final int EQUIP_SLOT_DRESS = 3;
		public static final int EQUIP_SLOT_SHOES = 4;
		public static final int EQUIP_SLOT_RING = 5;
		public static final int EQUIP_SLOT_NECKLACE = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstEquip = new LuaTable();
			scriptConstEquip.set("EQUIP_SLOT_NONE", LuaValue.valueOf(EQUIP_SLOT_NONE));
			scriptConstEquip.set("EQUIP_SLOT_WEAPON", LuaValue.valueOf(EQUIP_SLOT_WEAPON));
			scriptConstEquip.set("EQUIP_SLOT_BRACER", LuaValue.valueOf(EQUIP_SLOT_BRACER));
			scriptConstEquip.set("EQUIP_SLOT_DRESS", LuaValue.valueOf(EQUIP_SLOT_DRESS));
			scriptConstEquip.set("EQUIP_SLOT_SHOES", LuaValue.valueOf(EQUIP_SLOT_SHOES));
			scriptConstEquip.set("EQUIP_SLOT_RING", LuaValue.valueOf(EQUIP_SLOT_RING));
			scriptConstEquip.set("EQUIP_SLOT_NECKLACE", LuaValue.valueOf(EQUIP_SLOT_NECKLACE));
			env.set("EquipSlotType", scriptConstEquip);
			env.get("package").get("loaded").set("EquipSlotType", scriptConstEquip);
			return env;
		}
	}
}
