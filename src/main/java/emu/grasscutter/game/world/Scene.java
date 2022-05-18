package emu.grasscutter.game.world;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.def.*;
import emu.grasscutter.game.dungeons.DungeonChallenge;
import emu.grasscutter.game.dungeons.DungeonSettleListener;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.scripts.SceneIndexManager;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.data.SceneBlock;
import emu.grasscutter.scripts.data.SceneGadget;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.danilopianini.util.SpatialIndex;

import java.util.*;
import java.util.stream.Collectors;

import static emu.grasscutter.utils.Language.translate;

public class Scene {
	private final World world;
	private final SceneData sceneData;
	private final List<Player> players;
	private final Int2ObjectMap<GameEntity> entities;
	
	private final Set<SpawnDataEntry> spawnedEntities;
	private final Set<SpawnDataEntry> deadSpawnedEntities;
	private final Set<SceneBlock> loadedBlocks;
	private boolean dontDestroyWhenEmpty;
	
	private int autoCloseTime;
	private int time;
	private ClimateType climate;
	private int weather;
	
	private SceneScriptManager scriptManager;
	private DungeonChallenge challenge;
	private List<DungeonSettleListener> dungeonSettleListeners;
	private DungeonData dungeonData;
	private int prevScene; // Id of the previous scene
	private int prevScenePoint;
	public Scene(World world, SceneData sceneData) {
		this.world = world;
		this.sceneData = sceneData;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.entities = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

		this.time = 8 * 60;
		this.climate = ClimateType.CLIMATE_SUNNY;
		this.prevScene = 3;
		
		this.spawnedEntities = new HashSet<>();
		this.deadSpawnedEntities = new HashSet<>();
		this.loadedBlocks = new HashSet<>();
		this.scriptManager = new SceneScriptManager(this);
	}
	
	public int getId() {
		return sceneData.getId();
	}

	public World getWorld() {
		return world;
	}
	
	public SceneData getSceneData() {
		return this.sceneData;
	}
	
	public SceneType getSceneType() {
		return getSceneData().getSceneType();
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public int getPlayerCount() {
		return this.getPlayers().size();
	}

	public Int2ObjectMap<GameEntity> getEntities() {
		return entities;
	}
	
	public GameEntity getEntityById(int id) {
		return this.entities.get(id);
	}

	public GameEntity getEntityByConfigId(int configId) {
		return this.entities.values().stream()
				.filter(x -> x.getConfigId() == configId)
				.findFirst()
				.orElse(null);
	}
	/**
	 * @return the autoCloseTime
	 */
	public int getAutoCloseTime() {
		return autoCloseTime;
	}

	/**
	 * @param autoCloseTime the autoCloseTime to set
	 */
	public void setAutoCloseTime(int autoCloseTime) {
		this.autoCloseTime = autoCloseTime;
	}

	public int getTime() {
		return time;
	}

	public void changeTime(int time) {
		this.time = time % 1440;
	}
	
	public ClimateType getClimate() {
		return climate;
	}

	public int getWeather() {
		return weather;
	}

	public void setClimate(ClimateType climate) {
		this.climate = climate;
	}

	public void setWeather(int weather) {
		this.weather = weather;
	}

	public int getPrevScene() {
		return prevScene;
	}

	public void setPrevScene(int prevScene) {
		this.prevScene = prevScene;
	}

	public int getPrevScenePoint() {
		return prevScenePoint;
	}

	public void setPrevScenePoint(int prevPoint) {
		this.prevScenePoint = prevPoint;
	}

	public boolean dontDestroyWhenEmpty() {
		return dontDestroyWhenEmpty;
	}

	public void setDontDestroyWhenEmpty(boolean dontDestroyWhenEmpty) {
		this.dontDestroyWhenEmpty = dontDestroyWhenEmpty;
	}

	public Set<SceneBlock> getLoadedBlocks() {
		return loadedBlocks;
	}

	public Set<SpawnDataEntry> getSpawnedEntities() {
		return spawnedEntities;
	}

	public Set<SpawnDataEntry> getDeadSpawnedEntities() {
		return deadSpawnedEntities;
	}

	public SceneScriptManager getScriptManager() {
		return scriptManager;
	}

	public DungeonData getDungeonData() {
		return dungeonData;
	}

	public void setDungeonData(DungeonData dungeonData) {
		if (dungeonData == null || this.dungeonData != null || this.getSceneType() != SceneType.SCENE_DUNGEON || dungeonData.getSceneId() != this.getId()) {
			return;
		}
		this.dungeonData = dungeonData;
	}

	public DungeonChallenge getChallenge() {
		return challenge;
	}

	public void setChallenge(DungeonChallenge challenge) {
		this.challenge = challenge;
	}

	public void addDungeonSettleObserver(DungeonSettleListener dungeonSettleListener){
		if(dungeonSettleListeners == null){
			dungeonSettleListeners = new ArrayList<>();
		}
		dungeonSettleListeners.add(dungeonSettleListener);
	}

	public List<DungeonSettleListener> getDungeonSettleObservers() {
		return dungeonSettleListeners;
	}

	public boolean isInScene(GameEntity entity) {
		return this.entities.containsKey(entity.getId());
	}
	
	public synchronized void addPlayer(Player player) {
		// Check if player already in
		if (getPlayers().contains(player)) {
			return;
		}
		
		// Remove player from prev scene
		if (player.getScene() != null) {
			player.getScene().removePlayer(player);
		}
			
		// Add
		getPlayers().add(player);
		player.setSceneId(this.getId());
		player.setScene(this);
		
		this.setupPlayerAvatars(player);
	}
	
	public synchronized void removePlayer(Player player) {
		// Remove from challenge if leaving
		if (this.getChallenge() != null && this.getChallenge().inProgress()) {
			player.sendPacket(new PacketDungeonChallengeFinishNotify(this.getChallenge()));
		}
		
		// Remove player from scene
		getPlayers().remove(player);
		player.setScene(null);
		
		// Remove player avatars
		this.removePlayerAvatars(player);
		
		// Remove player gadgets
		for (EntityBaseGadget gadget : player.getTeamManager().getGadgets()) {
			this.removeEntity(gadget);
		}
		
		// Deregister scene if not in use
		if (this.getPlayerCount() <= 0 && !this.dontDestroyWhenEmpty()) {
			this.getWorld().deregisterScene(this);
		}
	}
	
	private void setupPlayerAvatars(Player player) {
		// Clear entities from old team
		player.getTeamManager().getActiveTeam().clear();

		// Add new entities for player
		TeamInfo teamInfo = player.getTeamManager().getCurrentTeamInfo();
		for (int avatarId : teamInfo.getAvatars()) {
			EntityAvatar entity = new EntityAvatar(player.getScene(), player.getAvatars().getAvatarById(avatarId));
			player.getTeamManager().getActiveTeam().add(entity);
		}
		
		// Limit character index in case its out of bounds
		if (player.getTeamManager().getCurrentCharacterIndex() >= player.getTeamManager().getActiveTeam().size() || player.getTeamManager().getCurrentCharacterIndex() < 0) {
			player.getTeamManager().setCurrentCharacterIndex(player.getTeamManager().getCurrentCharacterIndex() - 1);
		}
	}
	
	private void removePlayerAvatars(Player player) {
		Iterator<EntityAvatar> it = player.getTeamManager().getActiveTeam().iterator();
		while (it.hasNext()) {
			this.removeEntity(it.next(), VisionType.VISION_REMOVE);
			it.remove();
		}
	}
	
	public void spawnPlayer(Player player) {
		if (this.isInScene(player.getTeamManager().getCurrentAvatarEntity())) {
			return;
		}
		
		if (player.getTeamManager().getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
			player.getTeamManager().getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
		}
		
		this.addEntity(player.getTeamManager().getCurrentAvatarEntity());
		
		// Notify the client of any extra skill charges
		for (EntityAvatar entity : player.getTeamManager().getActiveTeam()) {
			if (entity.getAvatar().getSkillExtraChargeMap().size() > 0) {
				player.sendPacket(new PacketAvatarSkillInfoNotify(entity.getAvatar()));
			}
		}
	}
	
	private void addEntityDirectly(GameEntity entity) {
		getEntities().put(entity.getId(), entity);
	}
	
	public synchronized void addEntity(GameEntity entity) {
		this.addEntityDirectly(entity);
		this.broadcastPacket(new PacketSceneEntityAppearNotify(entity));
	}

	public synchronized void addEntityToSingleClient(Player player, GameEntity entity) {
		this.addEntityDirectly(entity);
		player.sendPacket(new PacketSceneEntityAppearNotify(entity));

	}
	public void addEntities(Collection<? extends GameEntity> entities){
		addEntities(entities, VisionType.VISION_BORN);
	}
	
	public synchronized void addEntities(Collection<? extends GameEntity> entities, VisionType visionType) {
		if(entities == null || entities.isEmpty()){
			return;
		}
		for (GameEntity entity : entities) {
			this.addEntityDirectly(entity);
		}
		
		this.broadcastPacket(new PacketSceneEntityAppearNotify(entities, visionType));
	}
	
	private GameEntity removeEntityDirectly(GameEntity entity) {
		return getEntities().remove(entity.getId());
	}
	
	public void removeEntity(GameEntity entity) {
		this.removeEntity(entity, VisionType.VISION_DIE);
	}
	
	public synchronized void removeEntity(GameEntity entity, VisionType visionType) {
		GameEntity removed = this.removeEntityDirectly(entity);
		if (removed != null) {
			this.broadcastPacket(new PacketSceneEntityDisappearNotify(removed, visionType));
		}
	}
	
	public synchronized void replaceEntity(EntityAvatar oldEntity, EntityAvatar newEntity) {
		this.removeEntityDirectly(oldEntity);
		this.addEntityDirectly(newEntity);
		this.broadcastPacket(new PacketSceneEntityDisappearNotify(oldEntity, VisionType.VISION_REPLACE));
		this.broadcastPacket(new PacketSceneEntityAppearNotify(newEntity, VisionType.VISION_REPLACE, oldEntity.getId()));
	}

	public void showOtherEntities(Player player) {
		List<GameEntity> entities = new LinkedList<>();
		GameEntity currentEntity = player.getTeamManager().getCurrentAvatarEntity();
		
		for (GameEntity entity : this.getEntities().values()) {
			if (entity == currentEntity) {
				continue;
			}
			entities.add(entity);
		}
		
		player.sendPacket(new PacketSceneEntityAppearNotify(entities, VisionType.VISION_MEET));
	}
	
	public void handleAttack(AttackResult result) {
		//GameEntity attacker = getEntityById(result.getAttackerId());
		GameEntity target = getEntityById(result.getDefenseId());
		
		if (target == null) {
			return;
		}
		
		// Godmode check
		if (target instanceof EntityAvatar) {
			if (((EntityAvatar) target).getPlayer().inGodmode()) {
				return;
			}
		}
		
		// Sanity check
		target.damage(result.getDamage(), result.getAttackerId());
	}
	
	public void killEntity(GameEntity target, int attackerId) {
		// Packet
		this.broadcastPacket(new PacketLifeStateChangeNotify(attackerId, target, LifeState.LIFE_DEAD));

		// Reward drop
		if (target instanceof EntityMonster && this.getSceneType() != SceneType.SCENE_DUNGEON) {
			getWorld().getServer().getDropManager().callDrop((EntityMonster) target);
		}

		this.removeEntity(target);
		
		// Death event
		target.onDeath(attackerId);
	}

	public void onTick() {
		if (this.getScriptManager().isInit()) {
			this.checkBlocks();
		} else {
			// TEMPORARY
			this.checkSpawns();
		}
		// Triggers
		this.scriptManager.checkRegions();
	}
	
	// TODO - Test
	public void checkSpawns() {
		SpatialIndex<SpawnGroupEntry> list = GameDepot.getSpawnListById(this.getId());
		Set<SpawnDataEntry> visible = new HashSet<>();
		
		for (Player player : this.getPlayers()) {
			int RANGE = 100;
			Collection<SpawnGroupEntry> entries = list.query(
				new double[] {player.getPos().getX() - RANGE, player.getPos().getZ() - RANGE}, 
				new double[] {player.getPos().getX() + RANGE, player.getPos().getZ() + RANGE}
			);
			
			for (SpawnGroupEntry entry : entries) {
				for (SpawnDataEntry spawnData : entry.getSpawns()) {
					visible.add(spawnData);
				}
			}
		}
		
		// World level
		WorldLevelData worldLevelData = GameData.getWorldLevelDataMap().get(getWorld().getWorldLevel());
		int worldLevelOverride = 0;
		
		if (worldLevelData != null) {
			worldLevelOverride = worldLevelData.getMonsterLevel();
		}
				
		// Todo
		List<GameEntity> toAdd = new LinkedList<>();
		List<GameEntity> toRemove = new LinkedList<>();
		
		for (SpawnDataEntry entry : visible) {
			if (!this.getSpawnedEntities().contains(entry) && !this.getDeadSpawnedEntities().contains(entry)) {
				// Spawn entity
				MonsterData data = GameData.getMonsterDataMap().get(entry.getMonsterId());
				
				if (data == null) {
					continue;
				}
				
				EntityMonster entity = new EntityMonster(this, data, entry.getPos(), worldLevelOverride > 0 ? worldLevelOverride : entry.getLevel());
				entity.getRotation().set(entry.getRot());
				entity.setGroupId(entry.getGroup().getGroupId());
				entity.setPoseId(entry.getPoseId());
				entity.setConfigId(entry.getConfigId());
				entity.setSpawnEntry(entry);
				
				toAdd.add(entity);
				
				// Add to spawned list
				this.getSpawnedEntities().add(entry);
			}
		}
		
		for (GameEntity entity : this.getEntities().values()) {
			if (entity.getSpawnEntry() != null && !visible.contains(entity.getSpawnEntry())) {
				toRemove.add(entity);
			}
		}
		
		if (toAdd.size() > 0) {
			toAdd.stream().forEach(this::addEntityDirectly);
			this.broadcastPacket(new PacketSceneEntityAppearNotify(toAdd, VisionType.VISION_BORN));
		}
		if (toRemove.size() > 0) {
			toRemove.stream().forEach(this::removeEntityDirectly);
			this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VISION_REMOVE));
		}
	}
	public Set<SceneBlock> getPlayerActiveBlocks(Player player){
		// TODO consider the borders of blocks
		return getScriptManager().getBlocks().values().stream()
				.filter(block -> block.contains(player.getPos()))
				.collect(Collectors.toSet());
	}
	public void checkBlocks() {
		Set<SceneBlock> visible = new HashSet<>();
		for (Player player : this.getPlayers()) {
			var blocks = getPlayerActiveBlocks(player);
			visible.addAll(blocks);
		}

		Iterator<SceneBlock> it = this.getLoadedBlocks().iterator();
		while (it.hasNext()) {
			SceneBlock block = it.next();

			if (!visible.contains(block)) {
				it.remove();

				onUnloadBlock(block);
			}
		}

		for(var block : visible){
			if (!this.getLoadedBlocks().contains(block)) {
				this.onLoadBlock(block, this.getPlayers());
				this.getLoadedBlocks().add(block);
			}else{
				// dynamic load the groups for players in a loaded block
				var toLoad = this.getPlayers().stream()
						.filter(p -> block.contains(p.getPos()))
						.map(p -> playerMeetGroups(p, block))
						.flatMap(Collection::stream)
						.toList();
				onLoadGroup(toLoad);
			}
		}

	}
	public List<SceneGroup> playerMeetGroups(Player player, SceneBlock block){
		int RANGE = 100;

		List<SceneGroup> sceneGroups = SceneIndexManager.queryNeighbors(block.sceneGroupIndex, player.getPos(), RANGE);

		List<SceneGroup> groups = sceneGroups.stream()
				.filter(group -> !scriptManager.getLoadedGroupSetPerBlock().get(block.id).contains(group))
				.peek(group -> scriptManager.getLoadedGroupSetPerBlock().get(block.id).add(group))
				.toList();

		if (groups.size() == 0) {
			return List.of();
		}

		return groups;
	}
	public void onLoadBlock(SceneBlock block, List<Player> players) {
		this.getScriptManager().loadBlockFromScript(block);
		scriptManager.getLoadedGroupSetPerBlock().put(block.id , new HashSet<>());

		// the groups form here is not added in current scene
		var groups = players.stream()
				.filter(player -> block.contains(player.getPos()))
				.map(p -> playerMeetGroups(p, block))
				.flatMap(Collection::stream)
				.toList();

		onLoadGroup(groups);
		Grasscutter.getLogger().info("Scene {} Block {} loaded.", this.getId(), block.id);
	}

	public void onLoadGroup(List<SceneGroup> groups){
		if(groups == null || groups.isEmpty()){
			return;
		}
		for (SceneGroup group : groups) {
			// We load the script files for the groups here
			this.getScriptManager().loadGroupFromScript(group);
		}

		// Spawn gadgets AFTER triggers are added
		// TODO
		var entities = new ArrayList<GameEntity>();
		for (SceneGroup group : groups) {
			if (group.init_config == null) {
				continue;
			}
			
			// Load garbages
			List<SceneGadget> garbageGadgets = group.getGarbageGadgets();
			
			if (garbageGadgets != null) {
				garbageGadgets.forEach(g -> scriptManager.createGadget(group.id, group.block_id, g));
			}

			// Load suites
			int suite = group.init_config.suite;

			if (suite == 0) {
				continue;
			}

			do {
				var suiteData = group.getSuiteByIndex(suite);
				suiteData.sceneTriggers.forEach(getScriptManager()::registerTrigger);

				entities.addAll(suiteData.sceneGadgets.stream()
						.map(g -> scriptManager.createGadget(group.id, group.block_id, g)).toList());
				entities.addAll(suiteData.sceneMonsters.stream()
						.map(mob -> scriptManager.createMonster(group.id, group.block_id, mob)).toList());
				suite++;
			} while (suite < group.init_config.end_suite);
		}

		scriptManager.meetEntities(entities);
		Grasscutter.getLogger().info("Scene {} loaded {} group(s)", this.getId(), groups.size());
	}
	
	public void onUnloadBlock(SceneBlock block) {
		List<GameEntity> toRemove = this.getEntities().values().stream()
				.filter(e -> e.getBlockId() == block.id).toList();

		if (toRemove.size() > 0) {
			toRemove.forEach(this::removeEntityDirectly);
			this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VISION_REMOVE));
		}
		
		for (SceneGroup group : block.groups) {
			if(group.triggers != null){
				group.triggers.values().forEach(getScriptManager()::deregisterTrigger);
			}
			if(group.regions != null){
				group.regions.forEach(getScriptManager()::deregisterRegion);
			}
		}
		scriptManager.getLoadedGroupSetPerBlock().remove(block.id);
		Grasscutter.getLogger().info("Scene {} Block {} is unloaded.", this.getId(), block.id);
	}
	
	// Gadgets
	
	public void onPlayerCreateGadget(EntityClientGadget gadget) {
		// Directly add
		this.addEntityDirectly(gadget);
		
		// Add to owner's gadget list
		gadget.getOwner().getTeamManager().getGadgets().add(gadget);
		
		// Optimization
		if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == gadget.getOwner()) {
			return;
		}
		
		this.broadcastPacketToOthers(gadget.getOwner(), new PacketSceneEntityAppearNotify(gadget));
	}
	
	public void onPlayerDestroyGadget(int entityId) {
		GameEntity entity = getEntities().get(entityId);
		
		if (entity == null || !(entity instanceof EntityClientGadget)) {
			return;
		}
		
		// Get and remove entity
		EntityClientGadget gadget = (EntityClientGadget) entity;
		this.removeEntityDirectly(gadget);
		
		// Remove from owner's gadget list
		gadget.getOwner().getTeamManager().getGadgets().remove(gadget);
		
		// Optimization
		if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == gadget.getOwner()) {
			return;
		}
		
		this.broadcastPacketToOthers(gadget.getOwner(), new PacketSceneEntityDisappearNotify(gadget, VisionType.VISION_DIE));
	}

	// Broadcasting
	
	public void broadcastPacket(BasePacket packet) {
    	// Send to all players - might have to check if player has been sent data packets
    	for (Player player : this.getPlayers()) {
    		player.getSession().send(packet);
    	}
	}
	
	public void broadcastPacketToOthers(Player excludedPlayer, BasePacket packet) {
		// Optimization
		if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == excludedPlayer) {
			return;
		}
    	// Send to all players - might have to check if player has been sent data packets
    	for (Player player : this.getPlayers()) {
    		if (player == excludedPlayer) {
    			continue;
    		}
    		// Send
    		player.getSession().send(packet);
    	}
	}

	public void addItemEntity(int itemId, int amount, GameEntity bornForm){
		ItemData itemData = GameData.getItemDataMap().get(itemId);
		if (itemData == null) {
			return;
		}
		if (itemData.isEquip()) {
			float range = (3f + (.1f * amount));
			for (int i = 0; i < amount; i++) {
				Position pos = bornForm.getPosition().clone().addX((float) (Math.random() * range) - (range / 2)).addZ((float) (Math.random() * range) - (range / 2)).addZ(.9f);
				EntityItem entity = new EntityItem(this, null, itemData, pos, 1);
				addEntity(entity);
			}
		} else {
			EntityItem entity = new EntityItem(this, null, itemData, bornForm.getPosition().clone().addZ(.9f), amount);
			addEntity(entity);
		}
	}
}
