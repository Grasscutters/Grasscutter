package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.scripts.data.SceneMonster;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.scripts.listener.ScriptMonsterListener;

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
    private final OnMonsterCreated onMonsterCreated= new OnMonsterCreated();
    private final OnMonsterDead onMonsterDead= new OnMonsterDead();

    public ScriptMonsterTideService(SceneScriptManager sceneScriptManager,
                     SceneGroup group, int tideCount, int monsterSceneLimit, Integer[] ordersConfigId){
        this.sceneScriptManager = sceneScriptManager;
        this.currentGroup = group;
        this.monsterSceneLimit = monsterSceneLimit;
        this.monsterTideCount = new AtomicInteger(tideCount);
        this.monsterKillCount = new AtomicInteger(0);
        this.monsterAlive = new AtomicInteger(0);
        this.monsterConfigOrders = new ConcurrentLinkedQueue<>(List.of(ordersConfigId));

        this.sceneScriptManager.getScriptMonsterSpawnService().addMonsterCreatedListener(onMonsterCreated);
        this.sceneScriptManager.getScriptMonsterSpawnService().addMonsterDeadListener(onMonsterDead);
        // spawn the first turn
        for (int i = 0; i < this.monsterSceneLimit; i++) {
            sceneScriptManager.addEntity(this.sceneScriptManager.createMonster(group.id, group.block_id, getNextMonster()));
        }
    }

    public class OnMonsterCreated implements ScriptMonsterListener{
        @Override
        public void onNotify(EntityMonster sceneMonster) {
            if(monsterSceneLimit > 0){
                monsterAlive.incrementAndGet();
                monsterTideCount.decrementAndGet();
            }
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

    public class OnMonsterDead implements ScriptMonsterListener {
        @Override
        public void onNotify(EntityMonster sceneMonster) {
            if (monsterSceneLimit <= 0) {
                return;
            }
            if (monsterAlive.decrementAndGet() >= monsterSceneLimit) {
                // maybe not happen
                return;
            }
            monsterKillCount.incrementAndGet();
            if (monsterTideCount.get() > 0) {
                // add more
                sceneScriptManager.addEntity(sceneScriptManager.createMonster(currentGroup.id, currentGroup.block_id, getNextMonster()));
            }
            // spawn the last turn of monsters
            // fix the 5-2
            sceneScriptManager.callEvent(EventType.EVENT_MONSTER_TIDE_DIE, new ScriptArgs(monsterKillCount.get()));
        }

    }

    public void unload(){
        this.sceneScriptManager.getScriptMonsterSpawnService().removeMonsterCreatedListener(onMonsterCreated);
        this.sceneScriptManager.getScriptMonsterSpawnService().removeMonsterDeadListener(onMonsterDead);
    }
}
