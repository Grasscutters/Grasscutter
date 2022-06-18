package emu.grasscutter.scripts;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.scripts.service.ScriptMonsterSpawnService;
import emu.grasscutter.scripts.service.ScriptMonsterTideService;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.util.*;
import java.util.stream.Collectors;

import static emu.grasscutter.Configuration.SCRIPT;

public class SceneScriptManager {
    private final Scene scene;
    private final ScriptLib scriptLib;
    private final LuaValue scriptLibLua;
    private final Map<String, Integer> variables;
    private Bindings bindings;
    private SceneConfig config;
    private List<SceneBlock> blocks;
    private boolean isInit;
    /**
     * SceneTrigger Set
     */
    private final Map<String, SceneTrigger> triggers;
    /**
     * current triggers controlled by RefreshGroup
     */
    private final Int2ObjectOpenHashMap<Set<SceneTrigger>> currentTriggers;
    private final Int2ObjectOpenHashMap<SceneRegion> regions;
    private final Map<Integer, SceneGroup> sceneGroups;
    private SceneGroup currentGroup;
    private ScriptMonsterTideService scriptMonsterTideService;
    private final ScriptMonsterSpawnService scriptMonsterSpawnService;

    public SceneScriptManager(Scene scene) {
        this.scene = scene;
        this.scriptLib = new ScriptLib(this);
        this.scriptLibLua = CoerceJavaToLua.coerce(this.scriptLib);
        this.triggers = new HashMap<>();
        this.currentTriggers = new Int2ObjectOpenHashMap<>();

        this.regions = new Int2ObjectOpenHashMap<>();
        this.variables = new HashMap<>();
        this.sceneGroups = new HashMap<>();
        this.scriptMonsterSpawnService = new ScriptMonsterSpawnService(this);

        // TEMPORARY
        if (this.getScene().getId() < 10) {
            return;
        }

        // Create
        this.init();
    }

    public Scene getScene() {
        return this.scene;
    }

    public ScriptLib getScriptLib() {
        return this.scriptLib;
    }

    public LuaValue getScriptLibLua() {
        return this.scriptLibLua;
    }

    public Bindings getBindings() {
        return this.bindings;
    }

    public SceneConfig getConfig() {
        return this.config;
    }

    public SceneGroup getCurrentGroup() {
        return this.currentGroup;
    }

    public List<SceneBlock> getBlocks() {
        return this.blocks;
    }

    public Map<String, Integer> getVariables() {
        return this.variables;
    }

    public Set<SceneTrigger> getTriggersByEvent(int eventId) {
        return this.currentTriggers.computeIfAbsent(eventId, e -> new HashSet<>());
    }

    public void registerTrigger(SceneTrigger trigger) {
        this.triggers.put(trigger.name, trigger);
        this.getTriggersByEvent(trigger.event).add(trigger);
    }

    public void deregisterTrigger(SceneTrigger trigger) {
        this.triggers.remove(trigger.name);
        this.getTriggersByEvent(trigger.event).remove(trigger);
    }

    public void resetTriggers(List<String> triggerNames) {
        for (var name : triggerNames) {
            var instance = this.triggers.get(name);
            this.currentTriggers.get(instance.event).clear();
            this.currentTriggers.get(instance.event).add(instance);
        }
    }

    public void refreshGroup(SceneGroup group, int suiteIndex) {
        var suite = group.getSuiteByIndex(suiteIndex);
        if (suite == null) {
            return;
        }
        if (suite.triggers.size() > 0) {
            this.resetTriggers(suite.triggers);
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

    // TODO optimize
    public SceneGroup getGroupById(int groupId) {
        for (SceneBlock block : this.getScene().getLoadedBlocks()) {
            for (SceneGroup group : block.groups) {
                if (group.id == groupId) {
                    return group;
                }
            }
        }
        return null;
    }

    private void init() {
        // Get compiled script if cached
        CompiledScript cs = ScriptLoader.getScriptByPath(
            SCRIPT("Scene/" + this.getScene().getId() + "/scene" + this.getScene().getId() + "." + ScriptLoader.getScriptType()));

        if (cs == null) {
            Grasscutter.getLogger().warn("No script found for scene " + this.getScene().getId());
            return;
        }

        // Create bindings
        this.bindings = ScriptLoader.getEngine().createBindings();

        // Set variables
        this.bindings.put("ScriptLib", this.getScriptLib());

        // Eval script
        try {
            cs.eval(this.getBindings());

            this.config = ScriptLoader.getSerializer().toObject(SceneConfig.class, this.bindings.get("scene_config"));

            // TODO optimize later
            // Create blocks
            List<Integer> blockIds = ScriptLoader.getSerializer().toList(Integer.class, this.bindings.get("blocks"));
            List<SceneBlock> blocks = ScriptLoader.getSerializer().toList(SceneBlock.class, this.bindings.get("block_rects"));

            for (int i = 0; i < blocks.size(); i++) {
                SceneBlock block = blocks.get(i);
                block.id = blockIds.get(i);

                this.loadBlockFromScript(block);
            }

            this.blocks = blocks;
        } catch (ScriptException e) {
            Grasscutter.getLogger().error("Error running script", e);
            return;
        }

        // TEMP
        this.isInit = true;
    }

    public boolean isInit() {
        return this.isInit;
    }

    private void loadBlockFromScript(SceneBlock block) {
        CompiledScript cs = ScriptLoader.getScriptByPath(
            SCRIPT("Scene/" + this.getScene().getId() + "/scene" + this.getScene().getId() + "_block" + block.id + "." + ScriptLoader.getScriptType()));

        if (cs == null) {
            return;
        }

        // Eval script
        try {
            cs.eval(this.getBindings());

            // Set groups
            block.groups = ScriptLoader.getSerializer().toList(SceneGroup.class, this.bindings.get("groups"));
            block.groups.forEach(g -> g.block_id = block.id);
        } catch (ScriptException e) {
            Grasscutter.getLogger().error("Error loading block " + block.id + " in scene " + this.getScene().getId(), e);
        }
    }

    public void loadGroupFromScript(SceneGroup group) {
        // Set flag here so if there is no script, we dont call this function over and over again.
        group.setLoaded(true);

        CompiledScript cs = ScriptLoader.getScriptByPath(
            SCRIPT("Scene/" + this.getScene().getId() + "/scene" + this.getScene().getId() + "_group" + group.id + "." + ScriptLoader.getScriptType()));

        if (cs == null) {
            return;
        }

        // Eval script
        try {
            cs.eval(this.getBindings());

            // Set
            group.monsters = ScriptLoader.getSerializer().toList(SceneMonster.class, this.bindings.get("monsters")).stream()
                .collect(Collectors.toMap(x -> x.config_id, y -> y));
            group.gadgets = ScriptLoader.getSerializer().toList(SceneGadget.class, this.bindings.get("gadgets"));
            group.triggers = ScriptLoader.getSerializer().toList(SceneTrigger.class, this.bindings.get("triggers"));
            group.suites = ScriptLoader.getSerializer().toList(SceneSuite.class, this.bindings.get("suites"));
            group.regions = ScriptLoader.getSerializer().toList(SceneRegion.class, this.bindings.get("regions"));
            group.init_config = ScriptLoader.getSerializer().toObject(SceneInitConfig.class, this.bindings.get("init_config"));

            // Add variables to suite
            List<SceneVar> variables = ScriptLoader.getSerializer().toList(SceneVar.class, this.bindings.get("variables"));
            variables.forEach(var -> this.getVariables().put(var.name, var.value));

            // Add monsters to suite TODO optimize
            Int2ObjectMap<Object> map = new Int2ObjectOpenHashMap<>();
            group.monsters.entrySet().forEach(m -> map.put(m.getValue().config_id, m));
            group.gadgets.forEach(m -> map.put(m.config_id, m));

            for (SceneSuite suite : group.suites) {
                suite.sceneMonsters = new ArrayList<>(suite.monsters.size());
                suite.monsters.forEach(id -> {
                    Object objEntry = map.get(id.intValue());
                    if (objEntry instanceof Map.Entry<?, ?> monsterEntry) {
                        Object monster = monsterEntry.getValue();
                        if (monster instanceof SceneMonster sceneMonster) {
                            suite.sceneMonsters.add(sceneMonster);
                        }
                    }
                });

                suite.sceneGadgets = new ArrayList<>(suite.gadgets.size());
                for (int id : suite.gadgets) {
                    try {
                        SceneGadget gadget = (SceneGadget) map.get(id);
                        if (gadget != null) {
                            suite.sceneGadgets.add(gadget);
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
            this.sceneGroups.put(group.id, group);
        } catch (ScriptException e) {
            Grasscutter.getLogger().error("Error loading group " + group.id + " in scene " + this.getScene().getId(), e);
        }
    }

    public void onTick() {
        this.checkRegions();
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

    public void spawnGadgetsInGroup(SceneGroup group, int suiteIndex) {
        this.spawnGadgetsInGroup(group, group.getSuiteByIndex(suiteIndex));
    }

    public void spawnGadgetsInGroup(SceneGroup group) {
        this.spawnGadgetsInGroup(group, null);
    }

    public void spawnGadgetsInGroup(SceneGroup group, SceneSuite suite) {
        List<SceneGadget> gadgets = group.gadgets;

        if (suite != null) {
            gadgets = suite.sceneGadgets;
        }

        for (SceneGadget g : gadgets) {
            EntityGadget entity = new EntityGadget(this.getScene(), g.gadget_id, g.pos);

            if (entity.getGadgetData() == null) continue;

            entity.setBlockId(group.block_id);
            entity.setConfigId(g.config_id);
            entity.setGroupId(group.id);
            entity.getRotation().set(g.rot);
            entity.setState(g.state);

            this.getScene().addEntity(entity);
            this.callEvent(EventType.EVENT_GADGET_CREATE, new ScriptArgs(entity.getConfigId()));
        }
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
        this.currentGroup = group;
        suite.sceneMonsters.forEach(mob -> this.scriptMonsterSpawnService.spawnMonster(group.id, mob));
    }

    public void spawnMonstersInGroup(SceneGroup group) {
        this.currentGroup = group;
        group.monsters.values().forEach(mob -> this.scriptMonsterSpawnService.spawnMonster(group.id, mob));
    }

    public void startMonsterTideInGroup(SceneGroup group, Integer[] ordersConfigId, int tideCount, int sceneLimit) {
        this.currentGroup = group;
        this.scriptMonsterTideService =
            new ScriptMonsterTideService(this, group, tideCount, sceneLimit, ordersConfigId);

    }

    public void unloadCurrentMonsterTide() {
        if (this.getScriptMonsterTideService() == null) {
            return;
        }
        this.getScriptMonsterTideService().unload();
    }

    public void spawnMonstersByConfigId(int configId, int delayTime) {
        // TODO delay
        this.scriptMonsterSpawnService.spawnMonster(this.currentGroup.id, this.currentGroup.monsters.get(configId));
    }
    // Events

    public void callEvent(int eventType, ScriptArgs params) {
        for (SceneTrigger trigger : this.getTriggersByEvent(eventType)) {
            LuaValue condition = null;

            if (trigger.condition != null && !trigger.condition.isEmpty()) {
                condition = (LuaValue) this.getBindings().get(trigger.condition);
            }

            LuaValue ret = LuaValue.TRUE;

            if (condition != null) {
                LuaValue args = LuaValue.NIL;

                if (params != null) {
                    args = CoerceJavaToLua.coerce(params);
                }

                ScriptLib.logger.trace("Call Condition Trigger {}", trigger);
                ret = this.safetyCall(trigger.condition, condition, args);
            }

            if (ret.isboolean() && ret.checkboolean()) {
                ScriptLib.logger.trace("Call Action Trigger {}", trigger);
                LuaValue action = (LuaValue) this.getBindings().get(trigger.action);
                // TODO impl the param of SetGroupVariableValueByGroup
                var arg = new ScriptArgs();
                arg.param2 = 100;
                var args = CoerceJavaToLua.coerce(arg);
                this.safetyCall(trigger.action, action, args);
            }
            //TODO some ret may not bool
        }
    }

    public LuaValue safetyCall(String name, LuaValue func, LuaValue args) {
        try {
            return func.call(this.getScriptLibLua(), args);
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

}
