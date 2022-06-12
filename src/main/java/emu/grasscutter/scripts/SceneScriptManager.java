package emu.grasscutter.scripts;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.WorldLevelData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.scripts.engine.CoerceJavaToLua;
import emu.grasscutter.scripts.service.ScriptMonsterSpawnService;
import emu.grasscutter.scripts.service.ScriptMonsterTideService;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.sandius.rembulan.runtime.LuaFunction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SceneScriptManager {
	private final Scene scene;
	private final Int2ObjectOpenHashMap<Map<String, Integer>> variables; // groupId - <Name, Var>
	private SceneMeta meta;
	private boolean isInit;
	/**
	 * current triggers controlled by RefreshGroup
	 */
	private final Int2ObjectOpenHashMap<Set<SceneTrigger>> currentTriggers;
	private final Int2ObjectOpenHashMap<EntityRegion> regions;
	private Map<Integer,SceneGroup> sceneGroups;
	private ScriptMonsterTideService scriptMonsterTideService;
	private ScriptMonsterSpawnService scriptMonsterSpawnService;
	/**
	 * blockid - loaded groupSet
	 */
	private Int2ObjectMap<Set<SceneGroup>> loadedGroupSetPerBlock;

	public SceneScriptManager(Scene scene) {
		this.scene = scene;
		this.currentTriggers = new Int2ObjectOpenHashMap<>();

		this.regions = new Int2ObjectOpenHashMap<>();
		this.variables = new Int2ObjectOpenHashMap<>();
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

	public SceneConfig getConfig() {
		if(!isInit){
			return null;
		}
		return meta.config;
	}

	public Map<Integer, SceneBlock> getBlocks() {
		return meta.blocks;
	}

	public Map<String, Integer> getVariables(int groupId) {
		return variables.computeIfAbsent(groupId, v -> new ConcurrentHashMap<>());
	}

	public Set<SceneTrigger> getTriggersByEvent(int eventId) {
		return currentTriggers.computeIfAbsent(eventId, e -> ConcurrentHashMap.newKeySet());
	}
	public void registerTrigger(List<SceneTrigger> triggers) {
		triggers.forEach(this::registerTrigger);
	}
	public void registerTrigger(SceneTrigger trigger) {
		getTriggersByEvent(trigger.event).add(trigger);
	}
	public void deregisterTrigger(List<SceneTrigger> triggers) {
		triggers.forEach(this::deregisterTrigger);
	}
	public void deregisterTrigger(SceneTrigger trigger) {
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
	public EntityRegion getRegionById(int id) {
		return regions.get(id);
	}
	
	public void registerRegion(EntityRegion region) {
		regions.put(region.getId(), region);
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
			var group = block.groups.get(groupId);
			if(group == null){
				continue;
			}

			if(!group.isLoaded()){
				getScene().onLoadGroup(List.of(group));
			}
			return group;
		}
		return null;
	}

	private void init() {
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
		group.load(getScene().getId());

		if (group.variables != null) {
			group.variables.forEach(var -> this.getVariables(group.id).put(var.name, var.value));
		}

		this.sceneGroups.put(group.id, group);

		if(group.regions != null){
			//group.regions.forEach(this::registerRegion);
		}
	}
	
	public void checkRegions() {
		if (this.regions.size() == 0) {
			return;
		}

		var entities = getScene().getEntities().values()
				.stream()
				.filter(e -> e.getEntityType() <= 2)
				.toList();

		for (var region : this.regions.values()) {

			entities.stream().filter(e -> region.getMetaRegion().contains(e.getPosition()))
					.forEach(region::addEntity);

			if (region.hasNewEntities()) {
				callEvent(EventType.EVENT_ENTER_REGION, new ScriptArgs(region.getConfigId()).setSourceEntityId(region.getId()));
				
				region.resetNewEntities();
			}
		}
	}

	public void addGroupSuite(SceneGroup group, SceneSuite suite){
		registerTrigger(suite.sceneTriggers);
		spawnMonstersInGroup(group, suite);
		spawnGadgetsInGroup(group, suite);
	}
	public void removeGroupSuite(SceneGroup group, SceneSuite suite){
		deregisterTrigger(suite.sceneTriggers);
		removeMonstersInGroup(group, suite);
		removeGadgetsInGroup(group, suite);
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
				.map(g -> createGadget(g.group.id, group.block_id, g))
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
	public void callEvent(int eventType, ScriptArgs params){
		for (SceneTrigger trigger : this.getTriggersByEvent(eventType)) {

			var ret = callScriptFunc(trigger.conditionFunc, trigger.currentGroup, params);
			Grasscutter.getLogger().debug("Call Condition Trigger {}", trigger.condition);

			if (ret instanceof Boolean b && b) {
				// the SetGroupVariableValueByGroup in tower need the param to record the first stage time
				callScriptFunc(trigger.actionFunc, trigger.currentGroup, params);
				Grasscutter.getLogger().debug("Call Action Trigger {}", trigger.action);
			}
			// TODO some ret may not bool
		}
	}

	private Object callScriptFunc(LuaFunction funcLua, SceneGroup group, ScriptArgs params){
		Object ret = Boolean.TRUE;

		if (funcLua != null) {
			Object args = null;

			if (params != null) {
				args = CoerceJavaToLua.coerce(params);
			}

			ret = ScriptLoader.getEngine().getLuaContext().execute(funcLua,
					new ScriptLibContext(this, group),
					args);
		}
		return ret;
	}

	public ScriptMonsterTideService getScriptMonsterTideService() {
		return scriptMonsterTideService;
	}

	public ScriptMonsterSpawnService getScriptMonsterSpawnService() {
		return scriptMonsterSpawnService;
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

		EntityGadget entity = new EntityGadget(getScene(), g.gadget_id, g.pos);

		if (entity.getGadgetData() == null){
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
		return new EntityNPC(getScene(), npc, blockId, suiteId);
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
		entity.setPoseId(monster.pose_id);

		this.getScriptMonsterSpawnService()
				.onMonsterCreatedListener.forEach(action -> action.onNotify(entity));
		
		return entity;
	}

	public void addEntity(GameEntity gameEntity){
		getScene().addEntity(gameEntity);
	}
	
	public void meetEntities(List<? extends GameEntity> gameEntity){
		getScene().addEntities(gameEntity, VisionTypeOuterClass.VisionType.VISION_TYPE_MEET);
	}
	
	public void addEntities(List<? extends GameEntity> gameEntity){
		getScene().addEntities(gameEntity);
	}


	public RTree<SceneBlock, Geometry> getBlocksIndex() {
		if (this.meta == null){
			return null;
		}
		return meta.sceneBlockIndex;
	}
	public void removeMonstersInGroup(SceneGroup group, SceneSuite suite) {
		var configSet = suite.sceneMonsters.stream()
				.map(m -> m.config_id)
				.collect(Collectors.toSet());
		var toRemove = getScene().getEntities().values().stream()
				.filter(e -> e instanceof EntityMonster)
				.filter(e -> e.getGroupId() == group.id)
				.filter(e -> configSet.contains(e.getConfigId()))
				.toList();

		getScene().removeEntities(toRemove, VisionTypeOuterClass.VisionType.VISION_TYPE_MISS);
	}
	public void removeGadgetsInGroup(SceneGroup group, SceneSuite suite) {
		var configSet = suite.sceneGadgets.stream()
				.map(m -> m.config_id)
				.collect(Collectors.toSet());
		var toRemove = getScene().getEntities().values().stream()
				.filter(e -> e instanceof EntityGadget)
				.filter(e -> e.getGroupId() == group.id)
				.filter(e -> configSet.contains(e.getConfigId()))
				.toList();

		getScene().removeEntities(toRemove, VisionTypeOuterClass.VisionType.VISION_TYPE_MISS);
	}


}
