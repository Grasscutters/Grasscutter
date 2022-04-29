package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Monster {
	public static class MonsterRarityType extends TwoArgFunction {
		public static final int MONSTER_RARITY_NONE = 0;
		public static final int MONSTER_RARITY_SMALL_MONSTER = 1;
		public static final int MONSTER_RARITY_ELITE_MONSTER = 2;
		public static final int MONSTER_RARITY_BOSS_MONSTER = 3;
		public static final int MONSTER_RARITY_BIG_BOSS_MONSTER = 4;
		public static final int MONSTER_RARITY_SMALL_ENV_ANIMAL = 5;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstMonster = new LuaTable();
			scriptConstMonster.set("MONSTER_RARITY_NONE", LuaValue.valueOf(MONSTER_RARITY_NONE));
			scriptConstMonster.set("MONSTER_RARITY_SMALL_MONSTER", LuaValue.valueOf(MONSTER_RARITY_SMALL_MONSTER));
			scriptConstMonster.set("MONSTER_RARITY_ELITE_MONSTER", LuaValue.valueOf(MONSTER_RARITY_ELITE_MONSTER));
			scriptConstMonster.set("MONSTER_RARITY_BOSS_MONSTER", LuaValue.valueOf(MONSTER_RARITY_BOSS_MONSTER));
			scriptConstMonster.set("MONSTER_RARITY_BIG_BOSS_MONSTER", LuaValue.valueOf(MONSTER_RARITY_BIG_BOSS_MONSTER));
			scriptConstMonster.set("MONSTER_RARITY_SMALL_ENV_ANIMAL", LuaValue.valueOf(MONSTER_RARITY_SMALL_ENV_ANIMAL));
			env.set("MonsterRarityType", scriptConstMonster);
			env.get("package").get("loaded").set("MonsterRarityType", scriptConstMonster);
			return env;
		}
	}
}
