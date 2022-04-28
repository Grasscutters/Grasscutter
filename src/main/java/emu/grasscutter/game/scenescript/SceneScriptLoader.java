package emu.grasscutter.game.scenescript;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.scenescript.types.SceneBase;
import org.luaj.vm2.LuaTable;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static emu.grasscutter.Grasscutter.getLogger;

public class SceneScriptLoader {
    private static final ScriptEngineManager manager = new ScriptEngineManager();
    private static final ScriptEngine engine = manager.getEngineByName("luaj");

    public void LoadSceneMain(String scriptPath) {
        //load script from file
        try {
            engine.eval(new java.io.FileReader(scriptPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SceneBase sceneBase = new SceneBase(engine);
        Grasscutter.getLogger().info("Loaded scene script: " + sceneBase.toString());
    }

}
