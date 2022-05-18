package emu.grasscutter.scripts;

import ch.ethz.globis.phtree.PhTree;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.WorldLevelData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
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
import javax.script.ScriptException;
import java.util.*;

public class SceneScriptManager {
	private final Scene scene;
	private final ScriptLib scriptLib;
	private final LuaValue scriptLibLua;
	private final Map<String, Integer> variables;
	private Bindings bindings;
	private SceneMeta meta;
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
	private Map<Integer,SceneGroup> sceneGroups;
	private ScriptMonsterTideService scriptMonsterTideService;
	private ScriptMonsterSpawnService scriptMonsterSpawnService;
	/**
	 * blockid - loaded groupSet
	 */
	private Int2ObjectMap<Set<SceneGroup>> loadedGroupSetPerBlock;
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
		this.loadedGroupSetPerBlock = new Int2ObjectOpenHashMap<>();

		// TEMPORARY
		if (this.getScene().getId() < 10 && !Grasscutter.getConfig().server.game.enableScriptInBigWorld) {
			return;
		}
		
		// Create
		this.init();
	}
	
	public Scene getScene() {
		return scene;
	}

	public ScriptLib getScriptLib() {
		return scriptLib;
	}
	
	public LuaValue getScriptLibLua() {
		return scriptLibLua;
	}

	public Bindings getBindings() {
		return bindings;
	}

	public SceneConfig getConfig() {
		if(!isInit){
			return null;
		}
		return meta.config;
	}

	public Map<Integer, SceneBlock> getBlocks() {
		return meta.blocks;
	}

	public Map<String, Integer> getVariables() {
		return variables;
	}

	public Set<SceneTrigger> getTriggersByEvent(int eventId) {
		return currentTriggers.computeIfAbsent(eventId, e -> new HashSet<>());
	}
	public void registerTrigger(SceneTrigger trigger) {
		this.triggers.put(trigger.name, trigger);
		getTriggersByEvent(trigger.event).add(trigger);
	}
	
	public void deregisterTrigger(SceneTrigger trigger) {
		this.triggers.remove(trigger.name);
		getTriggersByEvent(trigger.event).remove(trigger);
	}
	public void resetTriggers(int eventId) {
		currentTriggers.put(eventId, new HashSet<>());
	}
	public void refreshGroup(SceneGroup group, int suiteIndex){
		var suite = group.getSuiteByIndex(suiteIndex);
		if(suite == null){
			return;
		}
		if(suite.sceneTriggers.size() > 0){
			for(var trigger : suite.sceneTriggers){
				resetTriggers(trigger.event);
				this.currentTriggers.get(trigger.event).add(trigger);
			}
		}
		spawnMonstersInGroup(group, suite);
		spawnGadgetsInGroup(group, suite);
	}
	public SceneRegion getRegionById(int id) {
		return regions.get(id);
	}
	
	public void registerRegion(SceneRegion region) {
		regions.put(region.config_id, region);
	}
	
	public void deregisterRegion(SceneRegion region) {
		regions.remove(region.config_id);
	}

	public Int2ObjectMap<Set<SceneGroup>> getLoadedGroupSetPerBlock() {
		return loadedGroupSetPerBlock;
	}

	// TODO optimize
	public SceneGroup getGroupById(int groupId) {
		for (SceneBlock block : this.getScene().getLoadedBlocks()) {
			for (SceneGroup group : block.groups) {
				if (group.id == groupId) {
					if(!group.isLoaded()){
						getScene().onLoadGroup(List.of(group));
					}
					return group;
				}
			}
		}
		return null;
	}

	private void init() {
		// Create bindings
		bindings = ScriptLoader.getEngine().createBindings();
		// Set variables
		bindings.put("ScriptLib", getScriptLib());

		var meta = ScriptLoader.getSceneMeta(getScene().getId());
		if (meta == null){
			return;
		}
		this.meta = meta;

		// TEMP
		this.isInit = true;
	}

	public boolean isInit() {
		return isInit;
	}
	
	public void loadBlockFromScript(SceneBlock block) {
		block.load(scene.getId(), meta.context);
	}
	
	public void loadGroupFromScript(SceneGroup group) {
		group.load(getScene().getId(), meta.context);

		try {
			// build the trigger for this scene
			group.getScript().eval(getBindings());
		} catch (ScriptException e) {
			Grasscutter.getLogger().error("Could not build the trigger for this scene", e);
		}

		group.variables.forEach(var -> this.getVariables().put(var.name, var.value));
		this.sceneGroups.put(group.id, group);

		if(group.regions != null){
			group.regions.forEach(this::registerRegion);
		}
	}
	
	public void checkRegions() {
		if (this.regions.size() == 0) {
			return;
		}
		
		for (SceneRegion region : this.regions.values()) {
			getScene().getEntities().values()
				.stream()
				.filter(e -> e.getEntityType() <= 2 && region.contains(e.getPosition()))
				.forEach(region::addEntity);

			if (region.hasNewEntities()) {
				// This is not how it works, source_eid should be region entity id, but we dont have an entity for regions yet
				callEvent(EventType.EVENT_ENTER_REGION, new ScriptArgs(region.config_id).setSourceEntityId(region.config_id));
				
				region.resetNewEntities();
			}
		}
	}
	
	public void spawnGadgetsInGroup(SceneGroup group, int suiteIndex) {
		spawnGadgetsInGroup(group, group.getSuiteByIndex(suiteIndex));
	}
	
	public void spawnGadgetsInGroup(SceneGroup group) {
		spawnGadgetsInGroup(group, null);
	}
	
	public void spawnGadgetsInGroup(SceneGroup group, SceneSuite suite) {
		var gadgets = group.gadgets.values();
		
		if (suite != null) {
			gadgets = suite.sceneGadgets;
		}

		var toCreate = gadgets.stream()
				.map(g -> createGadget(g.groupId, group.block_id, g))
				.filter(Objects::nonNull)
				.toList();
		this.addEntities(toCreate);
	}

	public void spawnMonstersInGroup(SceneGroup group, int suiteIndex) {
		var suite = group.getSuiteByIndex(suiteIndex);
		if(suite == null){
			return;
		}
		spawnMonstersInGroup(group, suite);
	}
	public void spawnMonstersInGroup(SceneGroup group, SceneSuite suite) {
		if(suite == null || suite.sceneMonsters.size() <= 0){
			return;
		}
		this.addEntities(suite.sceneMonsters.stream()
				.map(mob -> createMonster(group.id, group.block_id, mob)).toList());

	}
	
	public void spawnMonstersInGroup(SceneGroup group) {
		this.addEntities(group.monsters.values().stream()
				.map(mob -> createMonster(group.id, group.block_id, mob)).toList());
	}

	public void startMonsterTideInGroup(SceneGroup group, Integer[] ordersConfigId, int tideCount, int sceneLimit) {
		this.scriptMonsterTideService =
				new ScriptMonsterTideService(this, group, tideCount, sceneLimit, ordersConfigId);

	}
	public void unloadCurrentMonsterTide(){
		if(this.getScriptMonsterTideService() == null){
			return;
		}
		this.getScriptMonsterTideService().unload();
	}
	public void spawnMonstersByConfigId(SceneGroup group, int configId, int delayTime) {
		// TODO delay
		getScene().addEntity(createMonster(group.id, group.block_id, group.monsters.get(configId)));
	}
	// Events
	
	public void callEvent(int eventType, ScriptArgs params) {
		for (SceneTrigger trigger : this.getTriggersByEvent(eventType)) {
			scriptLib.setCurrentGroup(trigger.currentGroup);
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
				ret = safetyCall(trigger.condition, condition, args);
			}
			
			if (ret.isboolean() && ret.checkboolean()) {
				if(trigger.action == null || trigger.action.isEmpty()){
					return;
				}
				ScriptLib.logger.trace("Call Action Trigger {}", trigger);
				LuaValue action = (LuaValue) this.getBindings().get(trigger.action);
				// TODO impl the param of SetGroupVariableValueByGroup
				var arg = new ScriptArgs();
				arg.param2 = 100;
				var args = CoerceJavaToLua.coerce(arg);
				safetyCall(trigger.action, action, args);
			}
			//TODO some ret may not bool
			scriptLib.removeCurrentGroup();
		}
	}

	public LuaValue safetyCall(String name, LuaValue func, LuaValue args){
		try{
			return func.call(this.getScriptLibLua(), args);
		}catch (LuaError error){
			ScriptLib.logger.error("[LUA] call trigger failed {},{},{}",name,args,error.getMessage());
			return LuaValue.valueOf(-1);
		}
	}

	public ScriptMonsterTideService getScriptMonsterTideService() {
		return scriptMonsterTideService;
	}

	public ScriptMonsterSpawnService getScriptMonsterSpawnService() {
		return scriptMonsterSpawnService;
	}

	public EntityGadget createGadget(int groupId, int blockId, SceneGadget g) {
		EntityGadget entity = new EntityGadget(getScene(), g.gadget_id, g.pos);

		if (entity.getGadgetData() == null){
			return null;
		}

		entity.setBlockId(blockId);
		entity.setConfigId(g.config_id);
		entity.setGroupId(groupId);
		entity.getRotation().set(g.rot);
		entity.setState(g.state);

		return entity;
	}

	public EntityMonster createMonster(int groupId, int blockId, SceneMonster monster) {
		if(monster == null){
			return null;
		}

		MonsterData data = GameData.getMonsterDataMap().get(monster.monster_id);

		if (data == null) {
			return null;
		}

		// Calculate level
		int level = monster.level;

		if (getScene().getDungeonData() != null) {
			level = getScene().getDungeonData().getShowLevel();
		} else if (getScene().getWorld().getWorldLevel() > 0) {
			WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(getScene().getWorld().getWorldLevel());

			if (worldLevelData != null) {
				level = worldLevelData.getMonsterLevel();
			}
		}

		// Spawn mob
		EntityMonster entity = new EntityMonster(getScene(), data, monster.pos, level);
		entity.getRotation().set(monster.rot);
		entity.setGroupId(groupId);
		entity.setBlockId(blockId);
		entity.setConfigId(monster.config_id);

		this.getScriptMonsterSpawnService()
				.onMonsterCreatedListener.forEach(action -> action.onNotify(entity));

		return entity;
	}

	public void addEntity(GameEntity gameEntity){
		getScene().addEntity(gameEntity);
		callCreateEvent(gameEntity);
	}
	public void meetEntities(List<? extends GameEntity> gameEntity){
		getScene().addEntities(gameEntity, VisionTypeOuterClass.VisionType.VISION_MEET);
		gameEntity.forEach(this::callCreateEvent);
	}
	public void addEntities(List<? extends GameEntity> gameEntity){
		getScene().addEntities(gameEntity);
		gameEntity.forEach(this::callCreateEvent);
	}
	public void callCreateEvent(GameEntity gameEntity){
		if(!isInit){
			return;
		}
		if(gameEntity instanceof EntityMonster entityMonster){
			callEvent(EventType.EVENT_ANY_MONSTER_LIVE, new ScriptArgs(entityMonster.getConfigId()));
		}
		if(gameEntity instanceof EntityGadget entityGadget){
			this.callEvent(EventType.EVENT_GADGET_CREATE, new ScriptArgs(entityGadget.getConfigId()));
		}
	}

	public PhTree<SceneBlock> getBlocksIndex() {
		return meta.sceneBlockIndex;
	}
}
