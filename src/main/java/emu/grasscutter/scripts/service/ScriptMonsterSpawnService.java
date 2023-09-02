package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.listener.ScriptMonsterListener;
import java.util.*;

public class ScriptMonsterSpawnService {

    public final List<ScriptMonsterListener> onMonsterCreatedListener = new ArrayList<>();
    public final List<ScriptMonsterListener> onMonsterDeadListener = new ArrayList<>();
    private final SceneScriptManager sceneScriptManager;

    public ScriptMonsterSpawnService(SceneScriptManager sceneScriptManager) {
        this.sceneScriptManager = sceneScriptManager;
    }

    public void addMonsterCreatedListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterCreatedListener.add(scriptMonsterListener);
    }

    public void addMonsterDeadListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterDeadListener.add(scriptMonsterListener);
    }

    public void removeMonsterCreatedListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterCreatedListener.remove(scriptMonsterListener);
    }

    public void removeMonsterDeadListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterDeadListener.remove(scriptMonsterListener);
    }

    public void onMonsterDead(EntityMonster entityMonster) {
        onMonsterDeadListener.forEach(l -> l.onNotify(entityMonster));
    }
}
