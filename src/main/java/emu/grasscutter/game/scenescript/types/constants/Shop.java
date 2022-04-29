package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Shop {
	public static class ShopType extends TwoArgFunction {
		public static final int SHOP_TYPE_NONE = 0;
		public static final int SHOP_TYPE_PAIMON = 1001;
		public static final int SHOP_TYPE_CITY = 1002;
		public static final int SHOP_TYPE_BLACKSMITH = 1003;
		public static final int SHOP_TYPE_GROCERY = 1004;
		public static final int SHOP_TYPE_FOOD = 1005;
		public static final int SHOP_TYPE_SEA_LAMP = 1006;
		public static final int SHOP_TYPE_VIRTUAL_SHOP = 1007;
		public static final int SHOP_TYPE_LIYUE_GROCERY = 1008;
		public static final int SHOP_TYPE_LIYUE_SOUVENIR = 1009;
		public static final int SHOP_TYPE_LIYUE_RESTAURANT = 1010;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstShop = new LuaTable();
			scriptConstShop.set("SHOP_TYPE_NONE", LuaValue.valueOf(SHOP_TYPE_NONE));
			scriptConstShop.set("SHOP_TYPE_PAIMON", LuaValue.valueOf(SHOP_TYPE_PAIMON));
			scriptConstShop.set("SHOP_TYPE_CITY", LuaValue.valueOf(SHOP_TYPE_CITY));
			scriptConstShop.set("SHOP_TYPE_BLACKSMITH", LuaValue.valueOf(SHOP_TYPE_BLACKSMITH));
			scriptConstShop.set("SHOP_TYPE_GROCERY", LuaValue.valueOf(SHOP_TYPE_GROCERY));
			scriptConstShop.set("SHOP_TYPE_FOOD", LuaValue.valueOf(SHOP_TYPE_FOOD));
			scriptConstShop.set("SHOP_TYPE_SEA_LAMP", LuaValue.valueOf(SHOP_TYPE_SEA_LAMP));
			scriptConstShop.set("SHOP_TYPE_VIRTUAL_SHOP", LuaValue.valueOf(SHOP_TYPE_VIRTUAL_SHOP));
			scriptConstShop.set("SHOP_TYPE_LIYUE_GROCERY", LuaValue.valueOf(SHOP_TYPE_LIYUE_GROCERY));
			scriptConstShop.set("SHOP_TYPE_LIYUE_SOUVENIR", LuaValue.valueOf(SHOP_TYPE_LIYUE_SOUVENIR));
			scriptConstShop.set("SHOP_TYPE_LIYUE_RESTAURANT", LuaValue.valueOf(SHOP_TYPE_LIYUE_RESTAURANT));
			env.set("ShopType", scriptConstShop);
			env.get("package").get("loaded").set("ShopType", scriptConstShop);
			return env;
		}
	}
}
