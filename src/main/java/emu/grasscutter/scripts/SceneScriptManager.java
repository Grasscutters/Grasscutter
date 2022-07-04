package emu.grasscutter.scripts;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.MonsterData;
import emu.grasscutter.data.excels.WorldLevelData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.props.EntityType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.*;
import emu.grasscutter.scripts.service.ScriptMonsterSpawnService;
import emu.grasscutter.scripts.service.ScriptMonsterTideService;
import io.netty.util.concurrent.FastThreadLocalThread;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SceneScriptManager {
	private final Scene scene;
	private final Map<String, Integer> variables;
	private SceneMeta meta;
	private boolean isInit;
	/**
	 * current triggers controlled by RefreshGroup
	 */
	private final Map<Integer, Set<SceneTrigger>> currentTriggers;
    private final Map<Integer, EntityRegion> regions; // <EntityId-Region>
	private final Map<Integer,SceneGroup> sceneGroups;
	private ScriptMonsterTideService scriptMonsterTideService;
	private ScriptMonsterSpawnService scriptMonsterSpawnService;
	/**
	 * blockid - loaded groupSet
	 */
	private final Map<Integer, Set<SceneGroup>> loadedGroupSetPerBlock;
	public static final ExecutorService eventExecutor;
	static {
		eventExecutor = new ThreadPoolExecutor(4, 4,
				60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000),
				FastThreadLocalThread::new, new ThreadPoolExecutor.AbortPolicy());
	}
	public SceneScriptManager(Scene scene) {
		this.scene = scene;
		this.currentTriggers = new ConcurrentHashMap<>();

		this.regions = new ConcurrentHashMap<>();
		this.variables = new ConcurrentHashMap<>();
		this.sceneGroups = new ConcurrentHashMap<>();
		this.scriptMonsterSpawnService = new ScriptMonsterSpawnService(this);
		this.loadedGroupSetPerBlock = new ConcurrentHashMap<>();

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

	public Map<String, Integer> getVariables() {
		return variables;
	}

	public Set<SceneTrigger> getTriggersByEvent(int eventId) {
		return currentTriggers.computeIfAbsent(eventId, e -> new HashSet<>());
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
    public void registerRegionInGroupSuite(SceneGroup group, SceneSuite suite){
        suite.sceneRegions.stream().map(region -> new EntityRegion(this.getScene(), region))
            .forEach(this::registerRegion);
    }
	public synchronized void deregisterRegion(SceneRegion region) {
		var instance = regions.values().stream()
            .filter(r -> r.getConfigId() == region.config_id)
            .findFirst();
        instance.ifPresent(entityRegion -> regions.remove(entityRegion.getId()));
	}

	public Map<Integer, Set<SceneGroup>> getLoadedGroupSetPerBlock() {
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
			group.variables.forEach(var -> this.getVariables().put(var.name, var.value));
		}

		this.sceneGroups.put(group.id, group);
	}

	public void checkRegions() {
		if (this.regions.size() == 0) {
			return;
		}

		for (var region : this.regions.values()) {
            // currently all condition_ENTER_REGION Events check for avatar, so we have no necessary to add other types of entity
			getScene().getEntities().values()
				.stream()
				.filter(e -> e.getEntityType() == EntityType.Avatar.getValue() && region.getMetaRegion().contains(e.getPosition()))
				.forEach(region::addEntity);

			if (region.hasNewEntities()) {
				callEvent(EventType.EVENT_ENTER_REGION, new ScriptArgs(region.getConfigId())
                    .setSourceEntityId(region.getId())
                    .setTargetEntityId(region.getFirstEntityId())
                );

				region.resetNewEntities();
			}
		}
	}

    public List<EntityGadget> getGadgetsInGroupSuite(SceneGroup group, SceneSuite suite){
        return suite.sceneGadgets.stream()
            .map(g -> createGadget(group.id, group.block_id, g))
            .filter(Objects::nonNull)
            .toList();
    }
    public List<EntityMonster> getMonstersInGroupSuite(SceneGroup group, SceneSuite suite){
        return suite.sceneMonsters.stream()
            .map(mob -> createMonster(group.id, group.block_id, mob))
            .filter(Objects::nonNull)
            .toList();
    }
	public void addGroupSuite(SceneGroup group, SceneSuite suite){
        // we added trigger first
        registerTrigger(suite.sceneTriggers);

        var toCreate = new ArrayList<GameEntity>();
        toCreate.addAll(getGadgetsInGroupSuite(group, suite));
        toCreate.addAll(getMonstersInGroupSuite(group, suite));
        addEntities(toCreate);

        registerRegionInGroupSuite(group, suite);
	}
	public void removeGroupSuite(SceneGroup group, SceneSuite suite){
        deregisterTrigger(suite.sceneTriggers);
		removeMonstersInGroup(group, suite);
		removeGadgetsInGroup(group, suite);

        suite.sceneRegions.forEach(this::deregisterRegion);
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

	public void spawnMonstersInGroup(SceneGroup group, SceneSuite suite) {
		if(suite == null || suite.sceneMonsters.size() <= 0){
			return;
		}
		this.addEntities(suite.sceneMonsters.stream()
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
		/**
		 * We use ThreadLocal to trans SceneScriptManager context to ScriptLib, to avoid eval script for every groups' trigger in every scene instances.
		 * But when callEvent is called in a ScriptLib func, it may cause NPE because the inner call cleans the ThreadLocal so that outer call could not get it.
		 * e.g. CallEvent -> set -> ScriptLib.xxx -> CallEvent -> set -> remove -> NPE -> (remove)
		 * So we use thread pool to clean the stack to avoid this new issue.
		 */
		eventExecutor.submit(() -> this.realCallEvent(eventType, params));
	}

	private void realCallEvent(int eventType, ScriptArgs params) {
		try{
			ScriptLoader.getScriptLib().setSceneScriptManager(this);
			for (SceneTrigger trigger : this.getTriggersByEvent(eventType)) {
				try{
					ScriptLoader.getScriptLib().setCurrentGroup(trigger.currentGroup);

					LuaValue ret = callScriptFunc(trigger.condition, trigger.currentGroup, params);
					Grasscutter.getLogger().trace("Call Condition Trigger {}", trigger.condition);

					if (ret.isboolean() && ret.checkboolean()) {
						// the SetGroupVariableValueByGroup in tower need the param to record the first stage time
						callScriptFunc(trigger.action, trigger.currentGroup, params);
						Grasscutter.getLogger().trace("Call Action Trigger {}", trigger.action);
					}
					//TODO some ret may not bool

				}finally {
					ScriptLoader.getScriptLib().removeCurrentGroup();
				}
			}
		}finally {
			// make sure it is removed
			ScriptLoader.getScriptLib().removeSceneScriptManager();
		}
	}

	private LuaValue callScriptFunc(String funcName, SceneGroup group, ScriptArgs params){
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

			ret = safetyCall(funcName, funcLua, args);
		}
		return ret;
	}

	public LuaValue safetyCall(String name, LuaValue func, LuaValue args){
		try{
			return func.call(ScriptLoader.getScriptLibLua(), args);
		}catch (LuaError error){
			ScriptLib.logger.error("[LUA] call trigger failed {},{}",name,args,error);
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
