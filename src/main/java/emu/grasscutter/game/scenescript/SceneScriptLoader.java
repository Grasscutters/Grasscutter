package emu.grasscutter.game.scenescript;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.scenescript.types.SceneBase;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class SceneScriptLoader {

    public void loadSceneMain(String scriptPath) {
        Globals globals = JsePlatform.standardGlobals();
        globals.loadfile(scriptPath).call();
        SceneBase sceneBase = new SceneBase(globals);
        Grasscutter.getLogger().info("Loaded scene script: " + sceneBase);
    }

}
