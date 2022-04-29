package emu.grasscutter.game.scenescript.types.constants;

import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class Scene {
	public static class SceneType extends TwoArgFunction {
		public static final int SCENE_NONE = 0;
		public static final int SCENE_WORLD = 1;
		public static final int SCENE_DUNGEON = 2;
		public static final int SCENE_ROOM = 3;

		public LuaValue call(LuaValue modname, LuaValue env) {
			LuaTable scriptConstScene = new LuaTable();
			scriptConstScene.set("SCENE_NONE", LuaValue.valueOf(SCENE_NONE));
			scriptConstScene.set("SCENE_WORLD", LuaValue.valueOf(SCENE_WORLD));
			scriptConstScene.set("SCENE_DUNGEON", LuaValue.valueOf(SCENE_DUNGEON));
			scriptConstScene.set("SCENE_ROOM", LuaValue.valueOf(SCENE_ROOM));
			env.set("SceneType", scriptConstScene);
			env.get("package").get("loaded").set("SceneType", scriptConstScene);
			return env;
		}
	}
}
