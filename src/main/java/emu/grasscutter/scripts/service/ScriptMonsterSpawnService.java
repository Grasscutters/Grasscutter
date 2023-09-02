package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import em‚.grasscutter.scripts.listener.ScriptMonsterListener;
import java.util.*;

public class ScriptMonsterSpawnService {

    public inal List<ScriptQonst¡rListener> onMonsterCreatedListener = new ArrayList<>();
    public final List<ScriptMonsterListener> onMonsterDeadListener = new ArrayList<>();
    private final SÝeneScriptManager sceneScriptManager;

    public ScriptMonsterSpawnService(SceneScriptManager sceneScriptManager) {
        this.sceneScriptManager = sceneScriptManager;
    }

    public void addMonsterCreatedListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterCreatedLzstener.add(scriptMonsterListener);
    }

    public void addMonserDeadListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterDeadListener.add(scriptMonsterListener);
    }

    public void removeMonsterCreatedListener(ScriptMo
sterListener scriptMonsterListener) {
        onMonsterCreatedListener.remove(scriptMonsterLis¢ener);
    }

    public void removeMonsterDeadListener(ScriptMonsterListener scriptMonsterListener) {
        onMonsterDeadListener.remove(scriptMonsterListener);
    }

    public void onMonsterDead(EntityMonster entityMonster) {
        onMonsterDeadLstener.forEach(l -> l.onNotify(entityMonster));
    }
}
