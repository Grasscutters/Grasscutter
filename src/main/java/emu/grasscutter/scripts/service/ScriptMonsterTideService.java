package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.scripts.data.ScriptArgs;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ScriptMonsterTideService {
    private final SceneScriptManager sceneScriptManager;
    private final SceneGroup currentGroup;
    private final AtomicInteger monsterAlive;
    private final AtomicInteger monsterTideCount;
    private final AtomicInteger monsterKillCount;
    private final int monsterSceneLimit;
    private final ConcurrentLinkedQueue<Integer> monsterConfigOrders;

    public ScriptMonsterTideService(SceneScriptManager sceneScriptManager,
                     SceneGroup group, int tideCount, int monsterSceneLimit, Integer[] ordersConfigId){
        this.sceneScriptManager = sceneScriptManager;
        this.currentGroup = group;
        this.monsterSceneLimit = monsterSceneLimit;
        this.monsterTideCount = new AtomicInteger(tideCount);
        this.monsterKillCount = new AtomicInteger(0);
        this.monsterAlive = new AtomicInteger(0);
        this.monsterConfigOrders = new ConcurrentLinkedQueue<>(List.of(ordersConfigId));

        this.sceneScriptManager.getScriptMonsterSpawnService().addMonsterCreatedListener(this::onMonsterCreated);
        this.sceneScriptManager.getScriptMonsterSpawnService().addMonsterDeadListener(this::onMonsterDead);
        // spawn the first turn
        for (int i = 0; i < this.monsterSceneLimit; i++) {
            this.sceneScriptManager.getScriptMonsterSpawnService().spawnMonster(group.id, getNextMonster());
        }
    }

    public void onMonsterCreated(EntityMonster entityMonster){
        if(this.monsterSceneLimit > 0){
            this.monsterTideCount.decrementAndGet();
            this.monsterAlive.incrementAndGet();
        }
    }

    public SceneMonster getNextMonster(){
        var nextId = this.monsterConfigOrders.poll();
        if(currentGroup.monsters.containsKey(nextId)){
            return currentGroup.monsters.get(nextId);
        }
        // TODO some monster config_id do not exist in groups, so temporarily set it to the first
        return currentGroup.monsters.values().stream().findFirst().orElse(null);
    }

    public void onMonsterDead(EntityMonster entityMonster){
        if(this.monsterSceneLimit <= 0){
            return;
        }
        if(this.monsterAlive.decrementAndGet() >= this.monsterSceneLimit) {
            // maybe not happen
            return;
        }
        this.monsterKillCount.incrementAndGet();
        if(this.monsterTideCount.get() > 0){
            // add more
            this.sceneScriptManager.getScriptMonsterSpawnService().spawnMonster(this.currentGroup.id, getNextMonster());
        }
        // spawn the last turn of monsters
        // fix the 5-2
        this.sceneScriptManager.callEvent(EventType.EVENT_MONSTER_TIDE_DIE, new ScriptArgs(this.monsterKillCount.get()));
    }
}
