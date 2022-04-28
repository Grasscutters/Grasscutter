package emu.grasscutter.game.scenescript;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.scenescript.types.SceneBase;
import emu.grasscutter.game.scenescript.types.SceneDummyPoints;
import org.luaj.vm2.lib.jse.JsePlatform;

public class SceneScriptLoader {

    public SceneBase loadSceneBase(String scriptPath) {
        var globals = JsePlatform.standardGlobals();
        globals.loadfile(scriptPath).call();
        var sceneBase = SceneBase.fromLuaTable(globals);
        Grasscutter.getLogger().info("Loaded scene script: " + sceneBase);
        return sceneBase;
    }

    public SceneDummyPoints loadSceneDummyPoints(String scriptPath) {
        var globals = JsePlatform.standardGlobals();
        globals.loadfile(scriptPath).call();
        var sceneDummyPoints = SceneDummyPoints.fromLuaTable(globals);
        Grasscutter.getLogger().info("Loaded scene script: " + sceneDummyPoints);
        return sceneDummyPoints;
    }

}
