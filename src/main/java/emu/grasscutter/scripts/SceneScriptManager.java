package emu.grasscutter.scripts;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.WorldLevelData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.EntityNPC;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.scripts.service.ScriptMonsterSpawnService;
import emu.grasscutter.scripts.service.ScriptMonsterTideService;
import io.netty.util.concurrent.FastThreadLocalThread;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SceneScriptManager {
    private final Scene scene;
    private final Map<String, Integer> variables;
    private SceneMeta meta;
    private boolean isInit;
    /**
     * current triggers controlled by RefreshGroup
     */
    private final Int2ObjectOpenHashMap<Set<SceneTrigger>> currentTriggers;
    private final Int2ObjectOpenHashMap<SceneRegion> regions;
    private final Map<Integer, SceneGroup> sceneGroups;
    private ScriptMonsterTideService scriptMonsterTideService;
    private final ScriptMonsterSpawnService scriptMonsterSpawnService;
    /**
     * blockid - loaded groupSet
     */
    private final Int2ObjectMap<Set<SceneGroup>> loadedGroupSetPerBlock;
    public static final ExecutorService eventExecutor;

    static {
        eventExecutor = new ThreadPoolExecutor(4, 4,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000),
            FastThreadLocalThread::new, new ThreadPoolExecutor.AbortPolicy());
    }

    public SceneScriptManager(Scene scene) {
        this.scene = scene;
        this.currentTriggers = new Int2ObjectOpenHashMap<>();

        this.regions = new Int2ObjectOpenHashMap<>();
        this.variables = new HashMap<>();
        this.sceneGroups = new HashMap<>();
        this.scriptMonsterSpawnService = new ScriptMonsterSpawnService(this);
        this.loadedGroupSetPerBlock = new Int2ObjectOpenHashMap<>();

        // TEMPORARY
        if (this.getScene().getId() < 10 && !Grasscutter.getConfig().server.game.enableScriptInBigWorld) {
            return;
        }

        // Create
        this.init();
    }

    public Scene getScene() {
        return this.scene;
    }

    public SceneConfig getConfig() {
        if (!this.isInit) {
            return null;
        }
        return this.meta.config;
    }

    public Map<Integer, SceneBlock> getBlocks() {
        return this.meta.blocks;
    }

    public Map<String, Integer> getVariables() {
        return this.variables;
    }

    public Set<SceneTrigger> getTriggersByEvent(int eventId) {
        return this.currentTriggers.computeIfAbsent(eventId, e -> new HashSet<>());
    }

    public void registerTrigger(List<SceneTrigger> triggers) {
        triggers.forEach(this::registerTrigger);
    }

    public void registerTrigger(SceneTrigger trigger) {
        this.getTriggersByEvent(trigger.event).add(trigger);
    }

    public void deregisterTrigger(List<SceneTrigger> triggers) {
        triggers.forEach(this::deregisterTrigger);
    }

    public void deregisterTrigger(SceneTrigger trigger) {
        this.getTriggersByEvent(trigger.event).remove(trigger);
    }

    public void resetTriggers(int eventId) {
        this.currentTriggers.put(eventId, new HashSet<>());
    }

    public void refreshGroup(SceneGroup group, int suiteIndex) {
        var suite = group.getSuiteByIndex(suiteIndex);
        if (suite == null) {
            return;
        }
        if (suite.sceneTriggers.size() > 0) {
            for (var trigger : suite.sceneTriggers) {
                this.resetTriggers(trigger.event);
                this.currentTriggers.get(trigger.event).add(trigger);
            }
        }
        this.spawnMonstersInGroup(group, suite);
        this.spawnGadgetsInGroup(group, suite);
    }

    public SceneRegion getRegionById(int id) {
        return this.regions.get(id);
    }

    public void registerRegion(SceneRegion region) {
        this.regions.put(region.config_id, region);
    }

    public void deregisterRegion(SceneRegion region) {
        this.regions.remove(region.config_id);
    }

    public Int2ObjectMap<Set<SceneGroup>> getLoadedGroupSetPerBlock() {
        return this.loadedGroupSetPerBlock;
    }

    // TODO optimize
    public SceneGroup getGroupById(int groupId) {
        for (SceneBlock block : this.getScene().getLoadedBlocks()) {
            var group = block.groups.get(groupId);
            if (group == null) {
                continue;
            }

            if (!group.isLoaded()) {
                this.getScene().onLoadGroup(List.of(group));
            }
            return group;
        }
        return null;
    }

    private void init() {
        var meta = ScriptLoader.getSceneMeta(this.getScene().getId());
        if (meta == null) {
            return;
        }
        this.meta = meta;

        // TEMP
        this.isInit = true;
    }

    public boolean isInit() {
        return this.isInit;
    }

    public void loadBlockFromScript(SceneBlock block) {
        block.load(this.scene.getId(), this.meta.context);
    }

    public void loadGroupFromScript(SceneGroup group) {
        group.load(this.getScene().getId());

        if (group.variables != null) {
            group.variables.forEach(var -> this.getVariables().put(var.name, var.value));
        }

        this.sceneGroups.put(group.id, group);

        if (group.regions != null) {
            group.regions.forEach(this::registerRegion);
        }
    }

    public void checkRegions() {
        if (this.regions.size() == 0) {
            return;
        }

        for (SceneRegion region : this.regions.values()) {
            this.getScene().getEntities().values()
                .stream()
                .filter(e -> e.getEntityType() <= 2 && region.contains(e.getPosition()))
                .forEach(region::addEntity);

            if (region.hasNewEntities()) {
                // This is not how it works, source_eid should be region entity id, but we dont have an entity for regions yet
                this.callEvent(EventType.EVENT_ENTER_REGION, new ScriptArgs(region.config_id).setSourceEntityId(region.config_id));

                region.resetNewEntities();
            }
        }
    }

    public void addGroupSuite(SceneGroup group, SceneSuite suite) {
        this.spawnMonstersInGroup(group, suite);
        this.spawnGadgetsInGroup(group, suite);
        this.registerTrigger(suite.sceneTriggers);
    }

    public void removeGroupSuite(SceneGroup group, SceneSuite suite) {
        this.removeMonstersInGroup(group, suite);
        this.removeGadgetsInGroup(group, suite);
        this.deregisterTrigger(suite.sceneTriggers);
    }

    public void spawnGadgetsInGroup(SceneGroup group, int suiteIndex) {
        this.spawnGadgetsInGroup(group, group.getSuiteByIndex(suiteIndex));
    }

    public void spawnGadgetsInGroup(SceneGroup group) {
        this.spawnGadgetsInGroup(group, null);
    }

    public void spawnGadgetsInGroup(SceneGroup group, SceneSuite suite) {
        var gadgets = group.gadgets.values();

        if (suite != null) {
            gadgets = suite.sceneGadgets;
        }

        var toCreate = gadgets.stream()
            .map(g -> this.createGadget(g.group.id, group.block_id, g))
            .filter(Objects::nonNull)
            .toList();
        this.addEntities(toCreate);
    }

    public void spawnMonstersInGroup(SceneGroup group, int suiteIndex) {
        var suite = group.getSuiteByIndex(suiteIndex);
        if (suite == null) {
            return;
        }
        this.spawnMonstersInGroup(group, suite);
    }

    public void spawnMonstersInGroup(SceneGroup group, SceneSuite suite) {
        if (suite == null || suite.sceneMonsters.size() <= 0) {
            return;
        }
        this.addEntities(suite.sceneMonsters.stream()
            .map(mob -> this.createMonster(group.id, group.block_id, mob)).toList());
    }

    public void spawnMonstersInGroup(SceneGroup group) {
        this.addEntities(group.monsters.values().stream()
            .map(mob -> this.createMonster(group.id, group.block_id, mob)).toList());
    }

    public void startMonsterTideInGroup(SceneGroup group, Integer[] ordersConfigId, int tideCount, int sceneLimit) {
        this.scriptMonsterTideService =
            new ScriptMonsterTideService(this, group, tideCount, sceneLimit, ordersConfigId);

    }

    public void unloadCurrentMonsterTide() {
        if (this.getScriptMonsterTideService() == null) {
            return;
        }
        this.getScriptMonsterTideService().unload();
    }

    public void spawnMonstersByConfigId(SceneGroup group, int configId, int delayTime) {
        // TODO delay
        this.getScene().addEntity(this.createMonster(group.id, group.block_id, group.monsters.get(configId)));
    }

    // Events
    public void callEvent(int eventType, ScriptArgs params) {
        /**
         * We use ThreadLocal to trans SceneScriptManager context to ScriptLib, to avoid eval script for every groups' trigger in every scene instances.
         * But when callEvent is called in a ScriptLib func, it may cause NPE because the inner call cleans the ThreadLocal so that outer call could not get it.
         * e.g. CallEvent -> set -> ScriptLib.xxx -> CallEvent -> set -> remove -> NPE -> (remove)
         * So we use thread pool to clean the stack to avoid this new issue.
         */
        eventExecutor.submit(() -> this.realCallEvent(eventType, params));
    }

    private void realCallEvent(int eventType, ScriptArgs params) {
        try {
            ScriptLoader.getScriptLib().setSceneScriptManager(this);
            for (SceneTrigger trigger : this.getTriggersByEvent(eventType)) {
                try {
                    ScriptLoader.getScriptLib().setCurrentGroup(trigger.currentGroup);

                    LuaValue ret = this.callScriptFunc(trigger.condition, trigger.currentGroup, params);
                    Grasscutter.getLogger().trace("Call Condition Trigger {}", trigger.condition);

                    if (ret.isboolean() && ret.checkboolean()) {
                        // the SetGroupVariableValueByGroup in tower need the param to record the first stage time
                        this.callScriptFunc(trigger.action, trigger.currentGroup, params);
                        Grasscutter.getLogger().trace("Call Action Trigger {}", trigger.action);
                    }
                    //TODO some ret may not bool

                } finally {
                    ScriptLoader.getScriptLib().removeCurrentGroup();
                }
            }
        } finally {
            // make sure it is removed
            ScriptLoader.getScriptLib().removeSceneScriptManager();
        }
    }

    private LuaValue callScriptFunc(String funcName, SceneGroup group, ScriptArgs params) {
        LuaValue funcLua = null;
        if (funcName != null && !funcName.isEmpty()) {
            funcLua = (LuaValue) group.getBindings().get(funcName);
        }

        LuaValue ret = LuaValue.TRUE;

        if (funcLua != null) {
            LuaValue args = LuaValue.NIL;

            if (params != null) {
                args = CoerceJavaToLua.coerce(params);
            }

            ret = this.safetyCall(funcName, funcLua, args);
        }
        return ret;
    }

    public LuaValue safetyCall(String name, LuaValue func, LuaValue args) {
        try {
            return func.call(ScriptLoader.getScriptLibLua(), args);
        } catch (LuaError error) {
            ScriptLib.logger.error("[LUA] call trigger failed {},{}", name, args, error);
            return LuaValue.valueOf(-1);
        }
    }

    public ScriptMonsterTideService getScriptMonsterTideService() {
        return this.scriptMonsterTideService;
    }

    public ScriptMonsterSpawnService getScriptMonsterSpawnService() {
        return this.scriptMonsterSpawnService;
    }

    public EntityGadget createGadget(int groupId, int blockId, SceneGadget g) {
        if(g.isOneoff){
            var hasEntity = getScene().getEntities().values().stream()
                    .filter(e -> e instanceof EntityGadget)
                    .filter(e -> e.getGroupId() == g.group.id)
                    .filter(e -> e.getConfigId() == g.config_id)
                    .findFirst();
            if(hasEntity.isPresent()){
                return null;
            }
        }

        EntityGadget entity = new EntityGadget(this.getScene(), g.gadget_id, g.pos);

        if (entity.getGadgetData() == null) {
            return null;
        }

        entity.setBlockId(blockId);
        entity.setConfigId(g.config_id);
        entity.setGroupId(groupId);
        entity.getRotation().set(g.rot);
        entity.setState(g.state);
        entity.setPointType(g.point_type);
        entity.setMetaGadget(g);
        entity.buildContent();

        return entity;
    }

    public EntityNPC createNPC(SceneNPC npc, int blockId, int suiteId) {
        return new EntityNPC(this.getScene(), npc, blockId, suiteId);
    }

    public EntityMonster createMonster(int groupId, int blockId, SceneMonster monster) {
        if (monster == null) {
            return null;
        }

        MonsterData data = GameData.getMonsterDataMap().get(monster.monster_id);

        if (data == null) {
            return null;
        }

        // Calculate level
        int level = monster.level;

        if (this.getScene().getDungeonData() != null) {
            level = this.getScene().getDungeonData().getShowLevel();
        } else if (this.getScene().getWorld().getWorldLevel() > 0) {
            WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(this.getScene().getWorld().getWorldLevel());

            if (worldLevelData != null) {
                level = worldLevelData.getMonsterLevel();
            }
        }

        // Spawn mob
        EntityMonster entity = new EntityMonster(this.getScene(), data, monster.pos, level);
        entity.getRotation().set(monster.rot);
        entity.setGroupId(groupId);
        entity.setBlockId(blockId);
        entity.setConfigId(monster.config_id);
        entity.setPoseId(monster.pose_id);

        this.getScriptMonsterSpawnService()
            .onMonsterCreatedListener.forEach(action -> action.onNotify(entity));

        return entity;
    }

    public void addEntity(GameEntity gameEntity) {
        this.getScene().addEntity(gameEntity);
    }

    public void meetEntities(List<? extends GameEntity> gameEntity) {
        this.getScene().addEntities(gameEntity, VisionTypeOuterClass.VisionType.VISION_TYPE_MEET);
    }

    public void addEntities(List<? extends GameEntity> gameEntity) {
        this.getScene().addEntities(gameEntity);
    }

    public RTree<SceneBlock, Geometry> getBlocksIndex() {
        return this.meta.sceneBlockIndex;
    }

    public void removeMonstersInGroup(SceneGroup group, SceneSuite suite) {
        var configSet = suite.sceneMonsters.stream()
            .map(m -> m.config_id)
            .collect(Collectors.toSet());
        var toRemove = this.getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityMonster)
            .filter(e -> e.getGroupId() == group.id)
            .filter(e -> configSet.contains(e.getConfigId()))
            .toList();

        this.getScene().removeEntities(toRemove, VisionTypeOuterClass.VisionType.VISION_TYPE_MISS);
    }

    public void removeGadgetsInGroup(SceneGroup group, SceneSuite suite) {
        var configSet = suite.sceneGadgets.stream()
            .map(m -> m.config_id)
            .collect(Collectors.toSet());
        var toRemove = this.getScene().getEntities().values().stream()
            .filter(e -> e instanceof EntityGadget)
            .filter(e -> e.getGroupId() == group.id)
            .filter(e -> configSet.contains(e.getConfigId()))
            .toList();

        this.getScene().removeEntities(toRemove, VisionTypeOuterClass.VisionType.VISION_TYPE_MISS);
    }
}
