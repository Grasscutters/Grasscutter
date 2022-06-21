package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.listener.ScriptMonsterListener;

import java.util.ArrayList;
import java.util.List;

public class ScriptMonsterSpawnService {

    private final SceneScriptManager sceneScriptManager;
    public final List<ScriptMonsterListener> onMonsterCreatedListener = new ArrayList<>();

    public final List<ScriptMonsterListener> onMonsterDeadListener = new ArrayList<>();

    public ScriptMonsterSpawnService(SceneScriptManager sceneScriptManager) {
        this.sceneScriptManager = sceneScriptManager;
    }

    public void addMonsterCreatedListener(ScriptMonsterListener scriptMonsterListener) {
        this.onMonsterCreatedListener.add(scriptMonsterListener);
    }

    public void addMonsterDeadListener(ScriptMonsterListener scriptMonsterListener) {
        this.onMonsterDeadListener.add(scriptMonsterListener);
    }

    public void removeMonsterCreatedListener(ScriptMonsterListener scriptMonsterListener) {
        this.onMonsterCreatedListener.remove(scriptMonsterListener);
    }

    public void removeMonsterDeadListener(ScriptMonsterListener scriptMonsterListener) {
        this.onMonsterDeadListener.remove(scriptMonsterListener);
    }

    public void onMonsterDead(EntityMonster entityMonster) {
        this.onMonsterDeadListener.forEach(l -> l.onNotify(entityMonster));
    }

}
