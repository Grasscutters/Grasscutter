package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Common {
	public static class DungeonPlayType extends TwoArgFunction {
		public static final int DUNGEON_PLAY_TYPE_NONE = 0;
		public static final int DUNGEON_PLAY_TYPE_FOGGY_MAZE = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("DUNGEON_PLAY_TYPE_NONE", LuaValue.valueOf(DUNGEON_PLAY_TYPE_NONE));
			scriptConstCommon.set("DUNGEON_PLAY_TYPE_FOGGY_MAZE", LuaValue.valueOf(DUNGEON_PLAY_TYPE_FOGGY_MAZE));
			env.set("DungeonPlayType", scriptConstCommon);
			env.get("package").get("loaded").set("DungeonPlayType", scriptConstCommon);
			return env;
		}
	}
	public static class RoundType extends TwoArgFunction {
		public static final int ROUND_TYPE_FLOOR = 0;
		public static final int ROUND_TYPE_ROUND = 1;
		public static final int ROUND_TYPE_CEIL = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("ROUND_TYPE_FLOOR", LuaValue.valueOf(ROUND_TYPE_FLOOR));
			scriptConstCommon.set("ROUND_TYPE_ROUND", LuaValue.valueOf(ROUND_TYPE_ROUND));
			scriptConstCommon.set("ROUND_TYPE_CEIL", LuaValue.valueOf(ROUND_TYPE_CEIL));
			env.set("RoundType", scriptConstCommon);
			env.get("package").get("loaded").set("RoundType", scriptConstCommon);
			return env;
		}
	}
	public static class SkillDrag extends TwoArgFunction {
		public static final int DRAG_NONE = 0;
		public static final int DRAG_ROTATE_CAMERA = 1;
		public static final int DRAG_ROTATE_CHARACTER = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("DRAG_NONE", LuaValue.valueOf(DRAG_NONE));
			scriptConstCommon.set("DRAG_ROTATE_CAMERA", LuaValue.valueOf(DRAG_ROTATE_CAMERA));
			scriptConstCommon.set("DRAG_ROTATE_CHARACTER", LuaValue.valueOf(DRAG_ROTATE_CHARACTER));
			env.set("SkillDrag", scriptConstCommon);
			env.get("package").get("loaded").set("SkillDrag", scriptConstCommon);
			return env;
		}
	}
	public static class EquipType extends TwoArgFunction {
		public static final int EQUIP_NONE = 0;
		public static final int EQUIP_BRACER = 1;
		public static final int EQUIP_NECKLACE = 2;
		public static final int EQUIP_SHOES = 3;
		public static final int EQUIP_RING = 4;
		public static final int EQUIP_DRESS = 5;
		public static final int EQUIP_WEAPON = 6;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("EQUIP_NONE", LuaValue.valueOf(EQUIP_NONE));
			scriptConstCommon.set("EQUIP_BRACER", LuaValue.valueOf(EQUIP_BRACER));
			scriptConstCommon.set("EQUIP_NECKLACE", LuaValue.valueOf(EQUIP_NECKLACE));
			scriptConstCommon.set("EQUIP_SHOES", LuaValue.valueOf(EQUIP_SHOES));
			scriptConstCommon.set("EQUIP_RING", LuaValue.valueOf(EQUIP_RING));
			scriptConstCommon.set("EQUIP_DRESS", LuaValue.valueOf(EQUIP_DRESS));
			scriptConstCommon.set("EQUIP_WEAPON", LuaValue.valueOf(EQUIP_WEAPON));
			env.set("EquipType", scriptConstCommon);
			env.get("package").get("loaded").set("EquipType", scriptConstCommon);
			return env;
		}
	}
	public static class QualityType extends TwoArgFunction {
		public static final int QUALITY_NONE = 0;
		public static final int QUALITY_WHITE = 1;
		public static final int QUALITY_GREEN = 2;
		public static final int QUALITY_BLUE = 3;
		public static final int QUALITY_PURPLE = 4;
		public static final int QUALITY_ORANGE = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("QUALITY_NONE", LuaValue.valueOf(QUALITY_NONE));
			scriptConstCommon.set("QUALITY_WHITE", LuaValue.valueOf(QUALITY_WHITE));
			scriptConstCommon.set("QUALITY_GREEN", LuaValue.valueOf(QUALITY_GREEN));
			scriptConstCommon.set("QUALITY_BLUE", LuaValue.valueOf(QUALITY_BLUE));
			scriptConstCommon.set("QUALITY_PURPLE", LuaValue.valueOf(QUALITY_PURPLE));
			scriptConstCommon.set("QUALITY_ORANGE", LuaValue.valueOf(QUALITY_ORANGE));
			env.set("QualityType", scriptConstCommon);
			env.get("package").get("loaded").set("QualityType", scriptConstCommon);
			return env;
		}
	}
	public static class StateType extends TwoArgFunction {
		public static final int BUFF_NONE = 0;
		public static final int BUFF_CONTROL = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("BUFF_NONE", LuaValue.valueOf(BUFF_NONE));
			scriptConstCommon.set("BUFF_CONTROL", LuaValue.valueOf(BUFF_CONTROL));
			env.set("StateType", scriptConstCommon);
			env.get("package").get("loaded").set("StateType", scriptConstCommon);
			return env;
		}
	}
	public static class WeaponType extends TwoArgFunction {
		public static final int WEAPON_NONE = 0;
		public static final int WEAPON_SWORD_ONE_HAND = 1;
		public static final int WEAPON_CROSSBOW = 2;
		public static final int WEAPON_STAFF = 3;
		public static final int WEAPON_DOUBLE_DAGGER = 4;
		public static final int WEAPON_KATANA = 5;
		public static final int WEAPON_SHURIKEN = 6;
		public static final int WEAPON_STICK = 7;
		public static final int WEAPON_SPEAR = 8;
		public static final int WEAPON_SHIELD_SMALL = 9;
		public static final int WEAPON_CATALYST = 10;
		public static final int WEAPON_CLAYMORE = 11;
		public static final int WEAPON_BOW = 12;
		public static final int WEAPON_POLE = 13;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("WEAPON_NONE", LuaValue.valueOf(WEAPON_NONE));
			scriptConstCommon.set("WEAPON_SWORD_ONE_HAND", LuaValue.valueOf(WEAPON_SWORD_ONE_HAND));
			scriptConstCommon.set("WEAPON_CROSSBOW", LuaValue.valueOf(WEAPON_CROSSBOW));
			scriptConstCommon.set("WEAPON_STAFF", LuaValue.valueOf(WEAPON_STAFF));
			scriptConstCommon.set("WEAPON_DOUBLE_DAGGER", LuaValue.valueOf(WEAPON_DOUBLE_DAGGER));
			scriptConstCommon.set("WEAPON_KATANA", LuaValue.valueOf(WEAPON_KATANA));
			scriptConstCommon.set("WEAPON_SHURIKEN", LuaValue.valueOf(WEAPON_SHURIKEN));
			scriptConstCommon.set("WEAPON_STICK", LuaValue.valueOf(WEAPON_STICK));
			scriptConstCommon.set("WEAPON_SPEAR", LuaValue.valueOf(WEAPON_SPEAR));
			scriptConstCommon.set("WEAPON_SHIELD_SMALL", LuaValue.valueOf(WEAPON_SHIELD_SMALL));
			scriptConstCommon.set("WEAPON_CATALYST", LuaValue.valueOf(WEAPON_CATALYST));
			scriptConstCommon.set("WEAPON_CLAYMORE", LuaValue.valueOf(WEAPON_CLAYMORE));
			scriptConstCommon.set("WEAPON_BOW", LuaValue.valueOf(WEAPON_BOW));
			scriptConstCommon.set("WEAPON_POLE", LuaValue.valueOf(WEAPON_POLE));
			env.set("WeaponType", scriptConstCommon);
			env.get("package").get("loaded").set("WeaponType", scriptConstCommon);
			return env;
		}
	}
	public static class GrowCurveType extends TwoArgFunction {
		public static final int GROW_CURVE_NONE = 0;
		public static final int GROW_CURVE_HP = 1;
		public static final int GROW_CURVE_ATTACK = 2;
		public static final int GROW_CURVE_STAMINA = 3;
		public static final int GROW_CURVE_STRIKE = 4;
		public static final int GROW_CURVE_ANTI_STRIKE = 5;
		public static final int GROW_CURVE_ANTI_STRIKE1 = 6;
		public static final int GROW_CURVE_ANTI_STRIKE2 = 7;
		public static final int GROW_CURVE_ANTI_STRIKE3 = 8;
		public static final int GROW_CURVE_STRIKE_HURT = 9;
		public static final int GROW_CURVE_ELEMENT = 10;
		public static final int GROW_CURVE_KILL_EXP = 11;
		public static final int GROW_CURVE_DEFENSE = 12;
		public static final int GROW_CURVE_ATTACK_BOMB = 13;
		public static final int GROW_CURVE_HP_LITTLEMONSTER = 14;
		public static final int GROW_CURVE_ELEMENT_MASTERY = 15;
		public static final int GROW_CURVE_PROGRESSION = 16;
		public static final int GROW_CURVE_DEFENDING = 17;
		public static final int GROW_CURVE_HP_S5 = 21;
		public static final int GROW_CURVE_HP_S4 = 22;
		public static final int GROW_CURVE_ATTACK_S5 = 31;
		public static final int GROW_CURVE_ATTACK_S4 = 32;
		public static final int GROW_CURVE_ATTACK_S3 = 33;
		public static final int GROW_CURVE_STRIKE_S5 = 34;
		public static final int GROW_CURVE_DEFENSE_S5 = 41;
		public static final int GROW_CURVE_DEFENSE_S4 = 42;
		public static final int GROW_CURVE_ATTACK_101 = 1101;
		public static final int GROW_CURVE_ATTACK_102 = 1102;
		public static final int GROW_CURVE_ATTACK_103 = 1103;
		public static final int GROW_CURVE_ATTACK_104 = 1104;
		public static final int GROW_CURVE_ATTACK_105 = 1105;
		public static final int GROW_CURVE_ATTACK_201 = 1201;
		public static final int GROW_CURVE_ATTACK_202 = 1202;
		public static final int GROW_CURVE_ATTACK_203 = 1203;
		public static final int GROW_CURVE_ATTACK_204 = 1204;
		public static final int GROW_CURVE_ATTACK_205 = 1205;
		public static final int GROW_CURVE_ATTACK_301 = 1301;
		public static final int GROW_CURVE_ATTACK_302 = 1302;
		public static final int GROW_CURVE_ATTACK_303 = 1303;
		public static final int GROW_CURVE_ATTACK_304 = 1304;
		public static final int GROW_CURVE_ATTACK_305 = 1305;
		public static final int GROW_CURVE_CRITICAL_101 = 2101;
		public static final int GROW_CURVE_CRITICAL_102 = 2102;
		public static final int GROW_CURVE_CRITICAL_103 = 2103;
		public static final int GROW_CURVE_CRITICAL_104 = 2104;
		public static final int GROW_CURVE_CRITICAL_105 = 2105;
		public static final int GROW_CURVE_CRITICAL_201 = 2201;
		public static final int GROW_CURVE_CRITICAL_202 = 2202;
		public static final int GROW_CURVE_CRITICAL_203 = 2203;
		public static final int GROW_CURVE_CRITICAL_204 = 2204;
		public static final int GROW_CURVE_CRITICAL_205 = 2205;
		public static final int GROW_CURVE_CRITICAL_301 = 2301;
		public static final int GROW_CURVE_CRITICAL_302 = 2302;
		public static final int GROW_CURVE_CRITICAL_303 = 2303;
		public static final int GROW_CURVE_CRITICAL_304 = 2304;
		public static final int GROW_CURVE_CRITICAL_305 = 2305;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("GROW_CURVE_NONE", LuaValue.valueOf(GROW_CURVE_NONE));
			scriptConstCommon.set("GROW_CURVE_HP", LuaValue.valueOf(GROW_CURVE_HP));
			scriptConstCommon.set("GROW_CURVE_ATTACK", LuaValue.valueOf(GROW_CURVE_ATTACK));
			scriptConstCommon.set("GROW_CURVE_STAMINA", LuaValue.valueOf(GROW_CURVE_STAMINA));
			scriptConstCommon.set("GROW_CURVE_STRIKE", LuaValue.valueOf(GROW_CURVE_STRIKE));
			scriptConstCommon.set("GROW_CURVE_ANTI_STRIKE", LuaValue.valueOf(GROW_CURVE_ANTI_STRIKE));
			scriptConstCommon.set("GROW_CURVE_ANTI_STRIKE1", LuaValue.valueOf(GROW_CURVE_ANTI_STRIKE1));
			scriptConstCommon.set("GROW_CURVE_ANTI_STRIKE2", LuaValue.valueOf(GROW_CURVE_ANTI_STRIKE2));
			scriptConstCommon.set("GROW_CURVE_ANTI_STRIKE3", LuaValue.valueOf(GROW_CURVE_ANTI_STRIKE3));
			scriptConstCommon.set("GROW_CURVE_STRIKE_HURT", LuaValue.valueOf(GROW_CURVE_STRIKE_HURT));
			scriptConstCommon.set("GROW_CURVE_ELEMENT", LuaValue.valueOf(GROW_CURVE_ELEMENT));
			scriptConstCommon.set("GROW_CURVE_KILL_EXP", LuaValue.valueOf(GROW_CURVE_KILL_EXP));
			scriptConstCommon.set("GROW_CURVE_DEFENSE", LuaValue.valueOf(GROW_CURVE_DEFENSE));
			scriptConstCommon.set("GROW_CURVE_ATTACK_BOMB", LuaValue.valueOf(GROW_CURVE_ATTACK_BOMB));
			scriptConstCommon.set("GROW_CURVE_HP_LITTLEMONSTER", LuaValue.valueOf(GROW_CURVE_HP_LITTLEMONSTER));
			scriptConstCommon.set("GROW_CURVE_ELEMENT_MASTERY", LuaValue.valueOf(GROW_CURVE_ELEMENT_MASTERY));
			scriptConstCommon.set("GROW_CURVE_PROGRESSION", LuaValue.valueOf(GROW_CURVE_PROGRESSION));
			scriptConstCommon.set("GROW_CURVE_DEFENDING", LuaValue.valueOf(GROW_CURVE_DEFENDING));
			scriptConstCommon.set("GROW_CURVE_HP_S5", LuaValue.valueOf(GROW_CURVE_HP_S5));
			scriptConstCommon.set("GROW_CURVE_HP_S4", LuaValue.valueOf(GROW_CURVE_HP_S4));
			scriptConstCommon.set("GROW_CURVE_ATTACK_S5", LuaValue.valueOf(GROW_CURVE_ATTACK_S5));
			scriptConstCommon.set("GROW_CURVE_ATTACK_S4", LuaValue.valueOf(GROW_CURVE_ATTACK_S4));
			scriptConstCommon.set("GROW_CURVE_ATTACK_S3", LuaValue.valueOf(GROW_CURVE_ATTACK_S3));
			scriptConstCommon.set("GROW_CURVE_STRIKE_S5", LuaValue.valueOf(GROW_CURVE_STRIKE_S5));
			scriptConstCommon.set("GROW_CURVE_DEFENSE_S5", LuaValue.valueOf(GROW_CURVE_DEFENSE_S5));
			scriptConstCommon.set("GROW_CURVE_DEFENSE_S4", LuaValue.valueOf(GROW_CURVE_DEFENSE_S4));
			scriptConstCommon.set("GROW_CURVE_ATTACK_101", LuaValue.valueOf(GROW_CURVE_ATTACK_101));
			scriptConstCommon.set("GROW_CURVE_ATTACK_102", LuaValue.valueOf(GROW_CURVE_ATTACK_102));
			scriptConstCommon.set("GROW_CURVE_ATTACK_103", LuaValue.valueOf(GROW_CURVE_ATTACK_103));
			scriptConstCommon.set("GROW_CURVE_ATTACK_104", LuaValue.valueOf(GROW_CURVE_ATTACK_104));
			scriptConstCommon.set("GROW_CURVE_ATTACK_105", LuaValue.valueOf(GROW_CURVE_ATTACK_105));
			scriptConstCommon.set("GROW_CURVE_ATTACK_201", LuaValue.valueOf(GROW_CURVE_ATTACK_201));
			scriptConstCommon.set("GROW_CURVE_ATTACK_202", LuaValue.valueOf(GROW_CURVE_ATTACK_202));
			scriptConstCommon.set("GROW_CURVE_ATTACK_203", LuaValue.valueOf(GROW_CURVE_ATTACK_203));
			scriptConstCommon.set("GROW_CURVE_ATTACK_204", LuaValue.valueOf(GROW_CURVE_ATTACK_204));
			scriptConstCommon.set("GROW_CURVE_ATTACK_205", LuaValue.valueOf(GROW_CURVE_ATTACK_205));
			scriptConstCommon.set("GROW_CURVE_ATTACK_301", LuaValue.valueOf(GROW_CURVE_ATTACK_301));
			scriptConstCommon.set("GROW_CURVE_ATTACK_302", LuaValue.valueOf(GROW_CURVE_ATTACK_302));
			scriptConstCommon.set("GROW_CURVE_ATTACK_303", LuaValue.valueOf(GROW_CURVE_ATTACK_303));
			scriptConstCommon.set("GROW_CURVE_ATTACK_304", LuaValue.valueOf(GROW_CURVE_ATTACK_304));
			scriptConstCommon.set("GROW_CURVE_ATTACK_305", LuaValue.valueOf(GROW_CURVE_ATTACK_305));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_101", LuaValue.valueOf(GROW_CURVE_CRITICAL_101));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_102", LuaValue.valueOf(GROW_CURVE_CRITICAL_102));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_103", LuaValue.valueOf(GROW_CURVE_CRITICAL_103));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_104", LuaValue.valueOf(GROW_CURVE_CRITICAL_104));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_105", LuaValue.valueOf(GROW_CURVE_CRITICAL_105));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_201", LuaValue.valueOf(GROW_CURVE_CRITICAL_201));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_202", LuaValue.valueOf(GROW_CURVE_CRITICAL_202));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_203", LuaValue.valueOf(GROW_CURVE_CRITICAL_203));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_204", LuaValue.valueOf(GROW_CURVE_CRITICAL_204));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_205", LuaValue.valueOf(GROW_CURVE_CRITICAL_205));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_301", LuaValue.valueOf(GROW_CURVE_CRITICAL_301));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_302", LuaValue.valueOf(GROW_CURVE_CRITICAL_302));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_303", LuaValue.valueOf(GROW_CURVE_CRITICAL_303));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_304", LuaValue.valueOf(GROW_CURVE_CRITICAL_304));
			scriptConstCommon.set("GROW_CURVE_CRITICAL_305", LuaValue.valueOf(GROW_CURVE_CRITICAL_305));
			env.set("GrowCurveType", scriptConstCommon);
			env.get("package").get("loaded").set("GrowCurveType", scriptConstCommon);
			return env;
		}
	}
	public static class ItemType extends TwoArgFunction {
		public static final int ITEM_NONE = 0;
		public static final int ITEM_VIRTUAL = 1;
		public static final int ITEM_MATERIAL = 2;
		public static final int ITEM_RELIQUARY = 3;
		public static final int ITEM_WEAPON = 4;
		public static final int ITEM_DISPLAY = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("ITEM_NONE", LuaValue.valueOf(ITEM_NONE));
			scriptConstCommon.set("ITEM_VIRTUAL", LuaValue.valueOf(ITEM_VIRTUAL));
			scriptConstCommon.set("ITEM_MATERIAL", LuaValue.valueOf(ITEM_MATERIAL));
			scriptConstCommon.set("ITEM_RELIQUARY", LuaValue.valueOf(ITEM_RELIQUARY));
			scriptConstCommon.set("ITEM_WEAPON", LuaValue.valueOf(ITEM_WEAPON));
			scriptConstCommon.set("ITEM_DISPLAY", LuaValue.valueOf(ITEM_DISPLAY));
			env.set("ItemType", scriptConstCommon);
			env.get("package").get("loaded").set("ItemType", scriptConstCommon);
			return env;
		}
	}
	public static class LifeState extends TwoArgFunction {
		public static final int LIFE_NONE = 0;
		public static final int LIFE_ALIVE = 1;
		public static final int LIFE_DEAD = 2;
		public static final int LIFE_REVIVE = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("LIFE_NONE", LuaValue.valueOf(LIFE_NONE));
			scriptConstCommon.set("LIFE_ALIVE", LuaValue.valueOf(LIFE_ALIVE));
			scriptConstCommon.set("LIFE_DEAD", LuaValue.valueOf(LIFE_DEAD));
			scriptConstCommon.set("LIFE_REVIVE", LuaValue.valueOf(LIFE_REVIVE));
			env.set("LifeState", scriptConstCommon);
			env.get("package").get("loaded").set("LifeState", scriptConstCommon);
			return env;
		}
	}
	public static class JobType extends TwoArgFunction {
		public static final int JOB_NONE = 0;
		public static final int JOB_SWORDMAN = 1;
		public static final int JOB_ARCHER = 2;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("JOB_NONE", LuaValue.valueOf(JOB_NONE));
			scriptConstCommon.set("JOB_SWORDMAN", LuaValue.valueOf(JOB_SWORDMAN));
			scriptConstCommon.set("JOB_ARCHER", LuaValue.valueOf(JOB_ARCHER));
			env.set("JobType", scriptConstCommon);
			env.get("package").get("loaded").set("JobType", scriptConstCommon);
			return env;
		}
	}
	public static class MaterialType extends TwoArgFunction {
		public static final int MATERIAL_NONE = 0;
		public static final int MATERIAL_FOOD = 1;
		public static final int MATERIAL_QUEST = 2;
		public static final int MATERIAL_EXCHANGE = 4;
		public static final int MATERIAL_CONSUME = 5;
		public static final int MATERIAL_EXP_FRUIT = 6;
		public static final int MATERIAL_AVATAR = 7;
		public static final int MATERIAL_ADSORBATE = 8;
		public static final int MATERIAL_CRICKET = 9;
		public static final int MATERIAL_ELEM_CRYSTAL = 10;
		public static final int MATERIAL_WEAPON_EXP_STONE = 11;
		public static final int MATERIAL_CHEST = 12;
		public static final int MATERIAL_RELIQUARY_MATERIAL = 13;
		public static final int MATERIAL_AVATAR_MATERIAL = 14;
		public static final int MATERIAL_NOTICE_ADD_HP = 15;
		public static final int MATERIAL_SEA_LAMP = 16;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("MATERIAL_NONE", LuaValue.valueOf(MATERIAL_NONE));
			scriptConstCommon.set("MATERIAL_FOOD", LuaValue.valueOf(MATERIAL_FOOD));
			scriptConstCommon.set("MATERIAL_QUEST", LuaValue.valueOf(MATERIAL_QUEST));
			scriptConstCommon.set("MATERIAL_EXCHANGE", LuaValue.valueOf(MATERIAL_EXCHANGE));
			scriptConstCommon.set("MATERIAL_CONSUME", LuaValue.valueOf(MATERIAL_CONSUME));
			scriptConstCommon.set("MATERIAL_EXP_FRUIT", LuaValue.valueOf(MATERIAL_EXP_FRUIT));
			scriptConstCommon.set("MATERIAL_AVATAR", LuaValue.valueOf(MATERIAL_AVATAR));
			scriptConstCommon.set("MATERIAL_ADSORBATE", LuaValue.valueOf(MATERIAL_ADSORBATE));
			scriptConstCommon.set("MATERIAL_CRICKET", LuaValue.valueOf(MATERIAL_CRICKET));
			scriptConstCommon.set("MATERIAL_ELEM_CRYSTAL", LuaValue.valueOf(MATERIAL_ELEM_CRYSTAL));
			scriptConstCommon.set("MATERIAL_WEAPON_EXP_STONE", LuaValue.valueOf(MATERIAL_WEAPON_EXP_STONE));
			scriptConstCommon.set("MATERIAL_CHEST", LuaValue.valueOf(MATERIAL_CHEST));
			scriptConstCommon.set("MATERIAL_RELIQUARY_MATERIAL", LuaValue.valueOf(MATERIAL_RELIQUARY_MATERIAL));
			scriptConstCommon.set("MATERIAL_AVATAR_MATERIAL", LuaValue.valueOf(MATERIAL_AVATAR_MATERIAL));
			scriptConstCommon.set("MATERIAL_NOTICE_ADD_HP", LuaValue.valueOf(MATERIAL_NOTICE_ADD_HP));
			scriptConstCommon.set("MATERIAL_SEA_LAMP", LuaValue.valueOf(MATERIAL_SEA_LAMP));
			env.set("MaterialType", scriptConstCommon);
			env.get("package").get("loaded").set("MaterialType", scriptConstCommon);
			return env;
		}
	}
	public static class MonsterType extends TwoArgFunction {
		public static final int MONSTER_NONE = 0;
		public static final int MONSTER_ORDINARY = 1;
		public static final int MONSTER_BOSS = 2;
		public static final int MONSTER_ENV_ANIMAL = 3;
		public static final int MONSTER_LITTLE_MONSTER = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("MONSTER_NONE", LuaValue.valueOf(MONSTER_NONE));
			scriptConstCommon.set("MONSTER_ORDINARY", LuaValue.valueOf(MONSTER_ORDINARY));
			scriptConstCommon.set("MONSTER_BOSS", LuaValue.valueOf(MONSTER_BOSS));
			scriptConstCommon.set("MONSTER_ENV_ANIMAL", LuaValue.valueOf(MONSTER_ENV_ANIMAL));
			scriptConstCommon.set("MONSTER_LITTLE_MONSTER", LuaValue.valueOf(MONSTER_LITTLE_MONSTER));
			env.set("MonsterType", scriptConstCommon);
			env.get("package").get("loaded").set("MonsterType", scriptConstCommon);
			return env;
		}
	}
	public static class PropType extends TwoArgFunction {
		public static final int PROP_NONE = 0;
		public static final int PROP_EXP = 1001;
		public static final int PROP_BREAK_LEVEL = 1002;
		public static final int PROP_SMALL_TALENT_POINT = 1004;
		public static final int PROP_BIG_TALENT_POINT = 1005;
		public static final int PROP_GEAR_START_VAL = 2001;
		public static final int PROP_GEAR_STOP_VAL = 2002;
		public static final int PROP_LEVEL = 4001;
		public static final int PROP_LAST_CHANGE_AVATAR_TIME = 10001;
		public static final int PROP_MAX_SPRING_VOLUME = 10002;
		public static final int PROP_CUR_SPRING_VOLUME = 10003;
		public static final int PROP_IS_SPRING_AUTO_USE = 10004;
		public static final int PROP_SPRING_AUTO_USE_PERCENT = 10005;
		public static final int PROP_IS_FLYABLE = 10006;
		public static final int PROP_IS_WEATHER_LOCKED = 10007;
		public static final int PROP_IS_GAME_TIME_LOCKED = 10008;
		public static final int PROP_IS_TRANSFERABLE = 10009;
		public static final int PROP_MAX_STAMINA = 10010;
		public static final int PROP_CUR_PERSIST_STAMINA = 10011;
		public static final int PROP_CUR_TEMPORARY_STAMINA = 10012;
		public static final int PROP_PLAYER_LEVEL = 10013;
		public static final int PROP_PLAYER_EXP = 10014;
		public static final int PROP_PLAYER_HCOIN = 10015;
		public static final int PROP_PLAYER_SCOIN = 10016;
		public static final int PROP_PLAYER_MP_SETTING_TYPE = 10017;
		public static final int PROP_IS_MP_MODE_AVAILABLE = 10018;
		public static final int PROP_PLAYER_LEVEL_LOCK_ID = 10019;
		public static final int PROP_PLAYER_RESIN = 10020;
		public static final int PROP_PLAYER_WORLD_RESIN = 10021;
		public static final int PROP_PLAYER_WAIT_SUB_HCOIN = 10022;
		public static final int PROP_PLAYER_WAIT_SUB_SCOIN = 10023;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("PROP_NONE", LuaValue.valueOf(PROP_NONE));
			scriptConstCommon.set("PROP_EXP", LuaValue.valueOf(PROP_EXP));
			scriptConstCommon.set("PROP_BREAK_LEVEL", LuaValue.valueOf(PROP_BREAK_LEVEL));
			scriptConstCommon.set("PROP_SMALL_TALENT_POINT", LuaValue.valueOf(PROP_SMALL_TALENT_POINT));
			scriptConstCommon.set("PROP_BIG_TALENT_POINT", LuaValue.valueOf(PROP_BIG_TALENT_POINT));
			scriptConstCommon.set("PROP_GEAR_START_VAL", LuaValue.valueOf(PROP_GEAR_START_VAL));
			scriptConstCommon.set("PROP_GEAR_STOP_VAL", LuaValue.valueOf(PROP_GEAR_STOP_VAL));
			scriptConstCommon.set("PROP_LEVEL", LuaValue.valueOf(PROP_LEVEL));
			scriptConstCommon.set("PROP_LAST_CHANGE_AVATAR_TIME", LuaValue.valueOf(PROP_LAST_CHANGE_AVATAR_TIME));
			scriptConstCommon.set("PROP_MAX_SPRING_VOLUME", LuaValue.valueOf(PROP_MAX_SPRING_VOLUME));
			scriptConstCommon.set("PROP_CUR_SPRING_VOLUME", LuaValue.valueOf(PROP_CUR_SPRING_VOLUME));
			scriptConstCommon.set("PROP_IS_SPRING_AUTO_USE", LuaValue.valueOf(PROP_IS_SPRING_AUTO_USE));
			scriptConstCommon.set("PROP_SPRING_AUTO_USE_PERCENT", LuaValue.valueOf(PROP_SPRING_AUTO_USE_PERCENT));
			scriptConstCommon.set("PROP_IS_FLYABLE", LuaValue.valueOf(PROP_IS_FLYABLE));
			scriptConstCommon.set("PROP_IS_WEATHER_LOCKED", LuaValue.valueOf(PROP_IS_WEATHER_LOCKED));
			scriptConstCommon.set("PROP_IS_GAME_TIME_LOCKED", LuaValue.valueOf(PROP_IS_GAME_TIME_LOCKED));
			scriptConstCommon.set("PROP_IS_TRANSFERABLE", LuaValue.valueOf(PROP_IS_TRANSFERABLE));
			scriptConstCommon.set("PROP_MAX_STAMINA", LuaValue.valueOf(PROP_MAX_STAMINA));
			scriptConstCommon.set("PROP_CUR_PERSIST_STAMINA", LuaValue.valueOf(PROP_CUR_PERSIST_STAMINA));
			scriptConstCommon.set("PROP_CUR_TEMPORARY_STAMINA", LuaValue.valueOf(PROP_CUR_TEMPORARY_STAMINA));
			scriptConstCommon.set("PROP_PLAYER_LEVEL", LuaValue.valueOf(PROP_PLAYER_LEVEL));
			scriptConstCommon.set("PROP_PLAYER_EXP", LuaValue.valueOf(PROP_PLAYER_EXP));
			scriptConstCommon.set("PROP_PLAYER_HCOIN", LuaValue.valueOf(PROP_PLAYER_HCOIN));
			scriptConstCommon.set("PROP_PLAYER_SCOIN", LuaValue.valueOf(PROP_PLAYER_SCOIN));
			scriptConstCommon.set("PROP_PLAYER_MP_SETTING_TYPE", LuaValue.valueOf(PROP_PLAYER_MP_SETTING_TYPE));
			scriptConstCommon.set("PROP_IS_MP_MODE_AVAILABLE", LuaValue.valueOf(PROP_IS_MP_MODE_AVAILABLE));
			scriptConstCommon.set("PROP_PLAYER_LEVEL_LOCK_ID", LuaValue.valueOf(PROP_PLAYER_LEVEL_LOCK_ID));
			scriptConstCommon.set("PROP_PLAYER_RESIN", LuaValue.valueOf(PROP_PLAYER_RESIN));
			scriptConstCommon.set("PROP_PLAYER_WORLD_RESIN", LuaValue.valueOf(PROP_PLAYER_WORLD_RESIN));
			scriptConstCommon.set("PROP_PLAYER_WAIT_SUB_HCOIN", LuaValue.valueOf(PROP_PLAYER_WAIT_SUB_HCOIN));
			scriptConstCommon.set("PROP_PLAYER_WAIT_SUB_SCOIN", LuaValue.valueOf(PROP_PLAYER_WAIT_SUB_SCOIN));
			env.set("PropType", scriptConstCommon);
			env.get("package").get("loaded").set("PropType", scriptConstCommon);
			return env;
		}
	}
	public static class ArithType extends TwoArgFunction {
		public static final int ARITH_NONE = 0;
		public static final int ARITH_ADD = 1;
		public static final int ARITH_MULTI = 2;
		public static final int ARITH_SUB = 3;
		public static final int ARITH_DIVIDE = 4;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("ARITH_NONE", LuaValue.valueOf(ARITH_NONE));
			scriptConstCommon.set("ARITH_ADD", LuaValue.valueOf(ARITH_ADD));
			scriptConstCommon.set("ARITH_MULTI", LuaValue.valueOf(ARITH_MULTI));
			scriptConstCommon.set("ARITH_SUB", LuaValue.valueOf(ARITH_SUB));
			scriptConstCommon.set("ARITH_DIVIDE", LuaValue.valueOf(ARITH_DIVIDE));
			env.set("ArithType", scriptConstCommon);
			env.get("package").get("loaded").set("ArithType", scriptConstCommon);
			return env;
		}
	}
	public static class BodyType extends TwoArgFunction {
		public static final int BODY_NONE = 0;
		public static final int BODY_BOY = 1;
		public static final int BODY_GIRL = 2;
		public static final int BODY_LADY = 3;
		public static final int BODY_MALE = 4;
		public static final int BODY_LOLI = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("BODY_NONE", LuaValue.valueOf(BODY_NONE));
			scriptConstCommon.set("BODY_BOY", LuaValue.valueOf(BODY_BOY));
			scriptConstCommon.set("BODY_GIRL", LuaValue.valueOf(BODY_GIRL));
			scriptConstCommon.set("BODY_LADY", LuaValue.valueOf(BODY_LADY));
			scriptConstCommon.set("BODY_MALE", LuaValue.valueOf(BODY_MALE));
			scriptConstCommon.set("BODY_LOLI", LuaValue.valueOf(BODY_LOLI));
			env.set("BodyType", scriptConstCommon);
			env.get("package").get("loaded").set("BodyType", scriptConstCommon);
			return env;
		}
	}
	public static class FightPropType extends TwoArgFunction {
		public static final int FIGHT_PROP_NONE = 0;
		public static final int FIGHT_PROP_BASE_HP = 1;
		public static final int FIGHT_PROP_HP = 2;
		public static final int FIGHT_PROP_HP_PERCENT = 3;
		public static final int FIGHT_PROP_BASE_ATTACK = 4;
		public static final int FIGHT_PROP_ATTACK = 5;
		public static final int FIGHT_PROP_ATTACK_PERCENT = 6;
		public static final int FIGHT_PROP_BASE_DEFENSE = 7;
		public static final int FIGHT_PROP_DEFENSE = 8;
		public static final int FIGHT_PROP_DEFENSE_PERCENT = 9;
		public static final int FIGHT_PROP_BASE_SPEED = 10;
		public static final int FIGHT_PROP_SPEED_PERCENT = 11;
		public static final int FIGHT_PROP_HP_MP_PERCENT = 12;
		public static final int FIGHT_PROP_ATTACK_MP_PERCENT = 13;
		public static final int FIGHT_PROP_CRITICAL = 20;
		public static final int FIGHT_PROP_ANTI_CRITICAL = 21;
		public static final int FIGHT_PROP_CRITICAL_HURT = 22;
		public static final int FIGHT_PROP_CHARGE_EFFICIENCY = 23;
		public static final int FIGHT_PROP_ADD_HURT = 24;
		public static final int FIGHT_PROP_SUB_HURT = 25;
		public static final int FIGHT_PROP_HEAL_ADD = 26;
		public static final int FIGHT_PROP_HEALED_ADD = 27;
		public static final int FIGHT_PROP_ELEMENT_MASTERY = 28;
		public static final int FIGHT_PROP_PHYSICAL_SUB_HURT = 29;
		public static final int FIGHT_PROP_PHYSICAL_ADD_HURT = 30;
		public static final int FIGHT_PROP_DEFENCE_IGNORE_RATIO = 31;
		public static final int FIGHT_PROP_DEFENCE_IGNORE_DELTA = 32;
		public static final int FIGHT_PROP_FIRE_ADD_HURT = 40;
		public static final int FIGHT_PROP_ELEC_ADD_HURT = 41;
		public static final int FIGHT_PROP_WATER_ADD_HURT = 42;
		public static final int FIGHT_PROP_GRASS_ADD_HURT = 43;
		public static final int FIGHT_PROP_WIND_ADD_HURT = 44;
		public static final int FIGHT_PROP_ROCK_ADD_HURT = 45;
		public static final int FIGHT_PROP_ICE_ADD_HURT = 46;
		public static final int FIGHT_PROP_HIT_HEAD_ADD_HURT = 47;
		public static final int FIGHT_PROP_FIRE_SUB_HURT = 50;
		public static final int FIGHT_PROP_ELEC_SUB_HURT = 51;
		public static final int FIGHT_PROP_WATER_SUB_HURT = 52;
		public static final int FIGHT_PROP_GRASS_SUB_HURT = 53;
		public static final int FIGHT_PROP_WIND_SUB_HURT = 54;
		public static final int FIGHT_PROP_ROCK_SUB_HURT = 55;
		public static final int FIGHT_PROP_ICE_SUB_HURT = 56;
		public static final int FIGHT_PROP_EFFECT_HIT = 60;
		public static final int FIGHT_PROP_EFFECT_RESIST = 61;
		public static final int FIGHT_PROP_FREEZE_RESIST = 62;
		public static final int FIGHT_PROP_TORPOR_RESIST = 63;
		public static final int FIGHT_PROP_DIZZY_RESIST = 64;
		public static final int FIGHT_PROP_FREEZE_SHORTEN = 65;
		public static final int FIGHT_PROP_TORPOR_SHORTEN = 66;
		public static final int FIGHT_PROP_DIZZY_SHORTEN = 67;
		public static final int FIGHT_PROP_MAX_FIRE_ENERGY = 70;
		public static final int FIGHT_PROP_MAX_ELEC_ENERGY = 71;
		public static final int FIGHT_PROP_MAX_WATER_ENERGY = 72;
		public static final int FIGHT_PROP_MAX_GRASS_ENERGY = 73;
		public static final int FIGHT_PROP_MAX_WIND_ENERGY = 74;
		public static final int FIGHT_PROP_MAX_ICE_ENERGY = 75;
		public static final int FIGHT_PROP_MAX_ROCK_ENERGY = 76;
		public static final int FIGHT_PROP_SKILL_CD_MINUS_RATIO = 80;
		public static final int FIGHT_PROP_SHIELD_COST_MINUS_RATIO = 81;
		public static final int FIGHT_PROP_CUR_FIRE_ENERGY = 1000;
		public static final int FIGHT_PROP_CUR_ELEC_ENERGY = 1001;
		public static final int FIGHT_PROP_CUR_WATER_ENERGY = 1002;
		public static final int FIGHT_PROP_CUR_GRASS_ENERGY = 1003;
		public static final int FIGHT_PROP_CUR_WIND_ENERGY = 1004;
		public static final int FIGHT_PROP_CUR_ICE_ENERGY = 1005;
		public static final int FIGHT_PROP_CUR_ROCK_ENERGY = 1006;
		public static final int FIGHT_PROP_CUR_HP = 1010;
		public static final int FIGHT_PROP_MAX_HP = 2000;
		public static final int FIGHT_PROP_CUR_ATTACK = 2001;
		public static final int FIGHT_PROP_CUR_DEFENSE = 2002;
		public static final int FIGHT_PROP_CUR_SPEED = 2003;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstCommon = new LuaTable();
			scriptConstCommon.set("FIGHT_PROP_NONE", LuaValue.valueOf(FIGHT_PROP_NONE));
			scriptConstCommon.set("FIGHT_PROP_BASE_HP", LuaValue.valueOf(FIGHT_PROP_BASE_HP));
			scriptConstCommon.set("FIGHT_PROP_HP", LuaValue.valueOf(FIGHT_PROP_HP));
			scriptConstCommon.set("FIGHT_PROP_HP_PERCENT", LuaValue.valueOf(FIGHT_PROP_HP_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_BASE_ATTACK", LuaValue.valueOf(FIGHT_PROP_BASE_ATTACK));
			scriptConstCommon.set("FIGHT_PROP_ATTACK", LuaValue.valueOf(FIGHT_PROP_ATTACK));
			scriptConstCommon.set("FIGHT_PROP_ATTACK_PERCENT", LuaValue.valueOf(FIGHT_PROP_ATTACK_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_BASE_DEFENSE", LuaValue.valueOf(FIGHT_PROP_BASE_DEFENSE));
			scriptConstCommon.set("FIGHT_PROP_DEFENSE", LuaValue.valueOf(FIGHT_PROP_DEFENSE));
			scriptConstCommon.set("FIGHT_PROP_DEFENSE_PERCENT", LuaValue.valueOf(FIGHT_PROP_DEFENSE_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_BASE_SPEED", LuaValue.valueOf(FIGHT_PROP_BASE_SPEED));
			scriptConstCommon.set("FIGHT_PROP_SPEED_PERCENT", LuaValue.valueOf(FIGHT_PROP_SPEED_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_HP_MP_PERCENT", LuaValue.valueOf(FIGHT_PROP_HP_MP_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_ATTACK_MP_PERCENT", LuaValue.valueOf(FIGHT_PROP_ATTACK_MP_PERCENT));
			scriptConstCommon.set("FIGHT_PROP_CRITICAL", LuaValue.valueOf(FIGHT_PROP_CRITICAL));
			scriptConstCommon.set("FIGHT_PROP_ANTI_CRITICAL", LuaValue.valueOf(FIGHT_PROP_ANTI_CRITICAL));
			scriptConstCommon.set("FIGHT_PROP_CRITICAL_HURT", LuaValue.valueOf(FIGHT_PROP_CRITICAL_HURT));
			scriptConstCommon.set("FIGHT_PROP_CHARGE_EFFICIENCY", LuaValue.valueOf(FIGHT_PROP_CHARGE_EFFICIENCY));
			scriptConstCommon.set("FIGHT_PROP_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_HEAL_ADD", LuaValue.valueOf(FIGHT_PROP_HEAL_ADD));
			scriptConstCommon.set("FIGHT_PROP_HEALED_ADD", LuaValue.valueOf(FIGHT_PROP_HEALED_ADD));
			scriptConstCommon.set("FIGHT_PROP_ELEMENT_MASTERY", LuaValue.valueOf(FIGHT_PROP_ELEMENT_MASTERY));
			scriptConstCommon.set("FIGHT_PROP_PHYSICAL_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_PHYSICAL_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_PHYSICAL_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_PHYSICAL_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_DEFENCE_IGNORE_RATIO", LuaValue.valueOf(FIGHT_PROP_DEFENCE_IGNORE_RATIO));
			scriptConstCommon.set("FIGHT_PROP_DEFENCE_IGNORE_DELTA", LuaValue.valueOf(FIGHT_PROP_DEFENCE_IGNORE_DELTA));
			scriptConstCommon.set("FIGHT_PROP_FIRE_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_FIRE_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_ELEC_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_ELEC_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_WATER_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_WATER_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_GRASS_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_GRASS_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_WIND_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_WIND_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_ROCK_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_ROCK_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_ICE_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_ICE_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_HIT_HEAD_ADD_HURT", LuaValue.valueOf(FIGHT_PROP_HIT_HEAD_ADD_HURT));
			scriptConstCommon.set("FIGHT_PROP_FIRE_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_FIRE_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_ELEC_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_ELEC_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_WATER_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_WATER_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_GRASS_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_GRASS_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_WIND_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_WIND_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_ROCK_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_ROCK_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_ICE_SUB_HURT", LuaValue.valueOf(FIGHT_PROP_ICE_SUB_HURT));
			scriptConstCommon.set("FIGHT_PROP_EFFECT_HIT", LuaValue.valueOf(FIGHT_PROP_EFFECT_HIT));
			scriptConstCommon.set("FIGHT_PROP_EFFECT_RESIST", LuaValue.valueOf(FIGHT_PROP_EFFECT_RESIST));
			scriptConstCommon.set("FIGHT_PROP_FREEZE_RESIST", LuaValue.valueOf(FIGHT_PROP_FREEZE_RESIST));
			scriptConstCommon.set("FIGHT_PROP_TORPOR_RESIST", LuaValue.valueOf(FIGHT_PROP_TORPOR_RESIST));
			scriptConstCommon.set("FIGHT_PROP_DIZZY_RESIST", LuaValue.valueOf(FIGHT_PROP_DIZZY_RESIST));
			scriptConstCommon.set("FIGHT_PROP_FREEZE_SHORTEN", LuaValue.valueOf(FIGHT_PROP_FREEZE_SHORTEN));
			scriptConstCommon.set("FIGHT_PROP_TORPOR_SHORTEN", LuaValue.valueOf(FIGHT_PROP_TORPOR_SHORTEN));
			scriptConstCommon.set("FIGHT_PROP_DIZZY_SHORTEN", LuaValue.valueOf(FIGHT_PROP_DIZZY_SHORTEN));
			scriptConstCommon.set("FIGHT_PROP_MAX_FIRE_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_FIRE_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_ELEC_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_ELEC_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_WATER_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_WATER_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_GRASS_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_GRASS_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_WIND_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_WIND_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_ICE_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_ICE_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_MAX_ROCK_ENERGY", LuaValue.valueOf(FIGHT_PROP_MAX_ROCK_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_SKILL_CD_MINUS_RATIO", LuaValue.valueOf(FIGHT_PROP_SKILL_CD_MINUS_RATIO));
			scriptConstCommon.set("FIGHT_PROP_SHIELD_COST_MINUS_RATIO", LuaValue.valueOf(FIGHT_PROP_SHIELD_COST_MINUS_RATIO));
			scriptConstCommon.set("FIGHT_PROP_CUR_FIRE_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_FIRE_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_ELEC_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_ELEC_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_WATER_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_WATER_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_GRASS_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_GRASS_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_WIND_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_WIND_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_ICE_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_ICE_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_ROCK_ENERGY", LuaValue.valueOf(FIGHT_PROP_CUR_ROCK_ENERGY));
			scriptConstCommon.set("FIGHT_PROP_CUR_HP", LuaValue.valueOf(FIGHT_PROP_CUR_HP));
			scriptConstCommon.set("FIGHT_PROP_MAX_HP", LuaValue.valueOf(FIGHT_PROP_MAX_HP));
			scriptConstCommon.set("FIGHT_PROP_CUR_ATTACK", LuaValue.valueOf(FIGHT_PROP_CUR_ATTACK));
			scriptConstCommon.set("FIGHT_PROP_CUR_DEFENSE", LuaValue.valueOf(FIGHT_PROP_CUR_DEFENSE));
			scriptConstCommon.set("FIGHT_PROP_CUR_SPEED", LuaValue.valueOf(FIGHT_PROP_CUR_SPEED));
			env.set("FightPropType", scriptConstCommon);
			env.get("package").get("loaded").set("FightPropType", scriptConstCommon);
			return env;
		}
	}
}
