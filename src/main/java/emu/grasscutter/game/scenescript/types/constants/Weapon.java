package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Weapon {
	public static class WeaponMaterialType extends TwoArgFunction {
		public static final int WEAPON_MATERIAL_NONE = 0;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstWeapon = new LuaTable();
			scriptConstWeapon.set("WEAPON_MATERIAL_NONE", LuaValue.valueOf(WEAPON_MATERIAL_NONE));
			env.set("WeaponMaterialType", scriptConstWeapon);
			env.get("package").get("loaded").set("WeaponMaterialType", scriptConstWeapon);
			return env;
		}
	}
}
