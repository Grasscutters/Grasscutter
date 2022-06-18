package emu.grasscutter.scripts.service;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.WorldLevelData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.scripts.listener.ScriptMonsterListener;

import java.util.ArrayList;
import java.util.List;

public class ScriptMonsterSpawnService {

    private final SceneScriptManager sceneScriptManager;
    private final List<ScriptMonsterListener> onMonsterCreatedListener = new ArrayList<>();

    private final List<ScriptMonsterListener> onMonsterDeadListener = new ArrayList<>();

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

    public void spawnMonster(int groupId, SceneMonster monster) {
        if (monster == null) {
            return;
        }

        MonsterData data = GameData.getMonsterDataMap().get(monster.monster_id);

        if (data == null) {
            return;
        }

        // Calculate level
        int level = monster.level;

        if (this.sceneScriptManager.getScene().getDungeonData() != null) {
            level = this.sceneScriptManager.getScene().getDungeonData().getShowLevel();
        } else if (this.sceneScriptManager.getScene().getWorld().getWorldLevel() > 0) {
            WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(this.sceneScriptManager.getScene().getWorld().getWorldLevel());

            if (worldLevelData != null) {
                level = worldLevelData.getMonsterLevel();
            }
        }

        // Spawn mob
        EntityMonster entity = new EntityMonster(this.sceneScriptManager.getScene(), data, monster.pos, level);
        entity.getRotation().set(monster.rot);
        entity.setGroupId(groupId);
        entity.setConfigId(monster.config_id);

        this.onMonsterCreatedListener.forEach(action -> action.onNotify(entity));

        this.sceneScriptManager.getScene().addEntity(entity);

        this.sceneScriptManager.callEvent(EventType.EVENT_ANY_MONSTER_LIVE, new ScriptArgs(entity.getConfigId()));
    }
}
