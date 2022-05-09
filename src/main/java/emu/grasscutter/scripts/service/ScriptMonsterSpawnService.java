package emu.grasscutter.scripts.service;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.WorldLevelData;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.scripts.data.ScriptArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScriptMonsterSpawnService {

    private final SceneScriptManager sceneScriptManager;
    private final List<Consumer<EntityMonster>> onMonsterCreatedListener = new ArrayList<>();

    private final List<Consumer<EntityMonster>> onMonsterDeadListener = new ArrayList<>();

    public ScriptMonsterSpawnService(SceneScriptManager sceneScriptManager){
        this.sceneScriptManager = sceneScriptManager;
    }

    public void addMonsterCreatedListener(Consumer<EntityMonster> consumer){
        onMonsterCreatedListener.add(consumer);
    }
    public void addMonsterDeadListener(Consumer<EntityMonster> consumer){
        onMonsterDeadListener.add(consumer);
    }

    public void onMonsterDead(EntityMonster entityMonster){
        onMonsterDeadListener.forEach(l -> l.accept(entityMonster));
    }
    public void spawnMonster(int groupId, SceneMonster monster) {
        if(monster == null){
            return;
        }

        MonsterData data = GameData.getMonsterDataMap().get(monster.monster_id);

        if (data == null) {
            return;
        }

        // Calculate level
        int level = monster.level;

        if (sceneScriptManager.getScene().getDungeonData() != null) {
            level = sceneScriptManager.getScene().getDungeonData().getShowLevel();
        } else if (sceneScriptManager.getScene().getWorld().getWorldLevel() > 0) {
            WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(sceneScriptManager.getScene().getWorld().getWorldLevel());

            if (worldLevelData != null) {
                level = worldLevelData.getMonsterLevel();
            }
        }

        // Spawn mob
        EntityMonster entity = new EntityMonster(sceneScriptManager.getScene(), data, monster.pos, level);
        entity.getRotation().set(monster.rot);
        entity.setGroupId(groupId);
        entity.setConfigId(monster.config_id);

        onMonsterCreatedListener.forEach(action -> action.accept(entity));

        sceneScriptManager.getScene().addEntity(entity);

        sceneScriptManager.callEvent(EventType.EVENT_ANY_MONSTER_LIVE, new ScriptArgs(entity.getConfigId()));
    }
}
