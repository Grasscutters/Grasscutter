package emu.grasscutter.scripts.service;

import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.scripts.listener.ScriptMonsterListener;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class ScriptMonsterTideService {
    private final SceneScriptManager sceneScriptManager;
    private final SceneGroup currentGroup;
    private final AtomicInteger monsterAlive;
    private final AtomicInteger monsterTideCount;
    private final AtomicInteger monsterKillCount;
    private final int monsterSceneLimit;
    private final ConcurrentLinkedQueue<Integer> monsterConfigOrders;
    private final List<Integer> monsterConfigIds;
    private final OnMonsterCreated onMonsterCreated = new OnMonsterCreated();
    private final OnMonsterDead onMonsterDead = new OnMonsterDead();
    private final String source;

    public ScriptMonsterTideService(
            SceneScriptManager sceneScriptManager,
            String source,
            SceneGroup group,
            int tideCount,
            int monsterSceneLimit,
            Integer[] ordersConfigId) {
        this.sceneScriptManager = sceneScriptManager;
        this.currentGroup = group;
        this.monsterSceneLimit = monsterSceneLimit;
        this.monsterTideCount = new AtomicInteger(tideCount);
        this.monsterKillCount = new AtomicInteger(0);
        this.monsterAlive = new AtomicInteger(0);
        this.monsterConfigOrders = new ConcurrentLinkedQueue<>(List.of(ordersConfigId));
        this.monsterConfigIds = List.of(ordersConfigId);
        this.source = source;

        this.sceneScriptManager
                .getScriptMonsterSpawnService()
                .addMonsterCreatedListener(onMonsterCreated);
        this.sceneScriptManager.getScriptMonsterSpawnService().addMonsterDeadListener(onMonsterDead);
        // spawn the first turn
        for (int i = 0; i < this.monsterSceneLimit; i++) {
            sceneScriptManager.addEntity(
                    this.sceneScriptManager.createMonster(group.id, group.block_id, getNextMonster()));
        }
    }

    public class OnMonsterCreated implements ScriptMonsterListener {
        @Override
        public void onNotify(EntityMonster sceneMonster) {
            if (monsterConfigIds.contains(sceneMonster.getConfigId()) && monsterSceneLimit > 0) {
                monsterAlive.incrementAndGet();
                monsterTideCount.decrementAndGet();
            }
        }
    }

    public SceneMonster getNextMonster() {
        var nextId = this.monsterConfigOrders.poll();
        if (nextId == null) {
            // AutoMonsterTide has been called with fewer monster config IDs than the total tide count.
            // Get last config ID from the list, then.
            return currentGroup.monsters.get(monsterConfigIds.get(monsterConfigIds.size() - 1));
        } else if (currentGroup.monsters.containsKey(nextId)) {
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
                sceneScriptManager.addEntity(
                        sceneScriptManager.createMonster(
                                currentGroup.id, currentGroup.block_id, getNextMonster()));
            }
            // call registered events that may spawn in more monsters
            var scriptArgs =
                    new ScriptArgs(currentGroup.id, EventType.EVENT_MONSTER_TIDE_DIE, monsterKillCount.get());
            scriptArgs.setEventSource(source);
            sceneScriptManager.callEvent(scriptArgs);
        }
    }

    public void unload() {
        this.sceneScriptManager
                .getScriptMonsterSpawnService()
                .removeMonsterCreatedListener(onMonsterCreated);
        this.sceneScriptManager.getScriptMonsterSpawnService().removeMonsterDeadListener(onMonsterDead);
    }
}
