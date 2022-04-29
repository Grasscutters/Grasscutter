package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Avatar {
	public static class AvatarIdentityType extends TwoArgFunction {
		public static final int AVATAR_IDENTITY_MASTER = 0;
		public static final int AVATAR_IDENTITY_NORMAL = 1;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatar = new LuaTable();
			scriptConstAvatar.set("AVATAR_IDENTITY_MASTER", LuaValue.valueOf(AVATAR_IDENTITY_MASTER));
			scriptConstAvatar.set("AVATAR_IDENTITY_NORMAL", LuaValue.valueOf(AVATAR_IDENTITY_NORMAL));
			env.set("AvatarIdentityType", scriptConstAvatar);
			env.get("package").get("loaded").set("AvatarIdentityType", scriptConstAvatar);
			return env;
		}
	}
	public static class AvatarUseType extends TwoArgFunction {
		public static final int AVATAR_TEST = 0;
		public static final int AVATAR_SYNC_TEST = 1;
		public static final int AVATAR_FORMAL = 2;
		public static final int AVATAR_ABANDON = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstAvatar = new LuaTable();
			scriptConstAvatar.set("AVATAR_TEST", LuaValue.valueOf(AVATAR_TEST));
			scriptConstAvatar.set("AVATAR_SYNC_TEST", LuaValue.valueOf(AVATAR_SYNC_TEST));
			scriptConstAvatar.set("AVATAR_FORMAL", LuaValue.valueOf(AVATAR_FORMAL));
			scriptConstAvatar.set("AVATAR_ABANDON", LuaValue.valueOf(AVATAR_ABANDON));
			env.set("AvatarUseType", scriptConstAvatar);
			env.get("package").get("loaded").set("AvatarUseType", scriptConstAvatar);
			return env;
		}
	}
}
