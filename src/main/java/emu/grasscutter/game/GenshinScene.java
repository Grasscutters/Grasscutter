package emu.grasscutter.game;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.Scene;
import emu.grasscutter.data.SceneDataLoader;
import emu.grasscutter.data.Vector2;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.SceneData;
import emu.grasscutter.data.def.WorldLevelData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class GenshinScene {
	private final World world;
	private final SceneData sceneData;
	private final List<GenshinPlayer> players;
	private final Int2ObjectMap<GameEntity> entities;
	private boolean dontDestroyWhenEmpty;

	private int time;
	private ClimateType climate;
	private int weather;

	public GenshinScene(World world, SceneData sceneData) {
		this.world = world;
		this.sceneData = sceneData;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.entities = new Int2ObjectOpenHashMap<>();

		this.time = 8 * 60;
		this.climate = ClimateType.CLIMATE_SUNNY;

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

	public List<GenshinPlayer> getPlayers() {
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

	public boolean dontDestroyWhenEmpty() {
		return dontDestroyWhenEmpty;
	}

	public void setDontDestroyWhenEmpty(boolean dontDestroyWhenEmpty) {
		this.dontDestroyWhenEmpty = dontDestroyWhenEmpty;
	}

	public boolean isInScene(GameEntity entity) {
		return this.entities.containsKey(entity.getId());
	}

	public void addPlayer(GenshinPlayer player) {
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

	public void removePlayer(GenshinPlayer player) {
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
		if (this.getEntities().size() <= 0) {
			this.getWorld().deregisterScene(this);
		}
	}

	private void setupPlayerAvatars(GenshinPlayer player) {
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

	private void removePlayerAvatars(GenshinPlayer player) {
		Iterator<EntityAvatar> it = player.getTeamManager().getActiveTeam().iterator();
		while (it.hasNext()) {
			this.removeEntity(it.next(), VisionType.VISION_REMOVE);
			it.remove();
		}
	}

	public void spawnPlayer(GenshinPlayer player) {
		if (this.isInScene(player.getTeamManager().getCurrentAvatarEntity())) {
			return;
		}

		if (player.getTeamManager().getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
			player.getTeamManager().getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
		}

		this.addEntity(player.getTeamManager().getCurrentAvatarEntity());
	}

	private void addEntityDirectly(GameEntity entity) {
		getEntities().put(entity.getId(), entity);
	}

	public synchronized void addEntity(GameEntity entity) {
		this.addEntityDirectly(entity);
		this.broadcastPacket(new PacketSceneEntityAppearNotify(entity));
	}

	public synchronized void addEntities(Collection<GameEntity> entities) {
		for (GameEntity entity : entities) {
			this.addEntityDirectly(entity);
		}

		this.broadcastPacket(new PacketSceneEntityAppearNotify(entities, VisionType.VISION_BORN));
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

	public void showOtherEntities(GenshinPlayer player) {
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


	private final ConcurrentLinkedDeque<Integer> loadedBlock = new ConcurrentLinkedDeque<>();

	private final ConcurrentHashMap<Integer, ArrayList<GameEntity>> preloadedEntityData = new ConcurrentHashMap<>();

	private static final Float DESPAWN_DISTANCE = 100.0f;
	private static final Float SPAWN_DISTANCE = DESPAWN_DISTANCE * 0.8f;
//	private static final int RESPAWN_TIME = 10; // In seconds



	public void handleAttack(AttackResult result) {
		//GenshinEntity attacker = getEntityById(result.getAttackerId());
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

		// Lose hp
		target.addFightProperty(FightProperty.FIGHT_PROP_CUR_HP, -result.getDamage());

		// Check if dead
		boolean isDead = false;
		if (target.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
			target.setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 0f);
			isDead = true;
		}

		// Packets
		this.broadcastPacket(new PacketEntityFightPropUpdateNotify(target, FightProperty.FIGHT_PROP_CUR_HP));

		// Check if dead
		if (isDead) {
			this.killEntity(target, result.getAttackerId());
		}
	}

	public void killEntity(GameEntity target, int attackerId) {
		// Packet
		this.broadcastPacket(new PacketLifeStateChangeNotify(attackerId, target, LifeState.LIFE_DEAD));
		this.removeEntity(target);

		// Death event
		target.onDeath(attackerId);
	}

	// TODO Do not use yet
	public synchronized void onTick() {
		for (GenshinPlayer player : this.getPlayers()) {
			this.checkSpawns(player);
		}
	}

	// TODO - Test
	public void checkSpawns(GenshinPlayer player) {
		Scene scene = SceneDataLoader.scenes.get(getId());

		// World level
		WorldLevelData worldLevelData = GenshinData.getWorldLevelDataMap().get(player.getWorldLevel());
		int worldLevelOverride = 0;

		if (worldLevelData != null) {
			worldLevelOverride = worldLevelData.getMonsterLevel();
		}

		if (scene != null) {
			int finalWorldLevelOverride = worldLevelOverride;
			// Load All Block Entities Data

			var visionBlocks = scene.getPlayerVisionBlock(new Vector2(player.getPos().getX(), player.getPos().getZ()), DESPAWN_DISTANCE);

			loadedBlock.stream()
				.filter((blockId) -> !visionBlocks.containsKey(blockId)).toList()
				.forEach((blockId) -> {
					preloadedEntityData.get(blockId).forEach(this::removeEntityDirectly);
					preloadedEntityData.remove(blockId);
					loadedBlock.remove(blockId);
				});

			visionBlocks.entrySet().stream()
				.filter((entry) -> !loadedBlock.contains(entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
				.forEach((blockId, block) -> {
					if (!loadedBlock.contains(blockId)) {
						this.preloadedEntityData.putIfAbsent(blockId, new ArrayList<>());
						block.getGroups().forEach((groupId, group) -> {
							group.getGadgets().forEach((gadget -> {
								EntityGadget entity = new EntityGadget(this, gadget);
								this.preloadedEntityData.get(blockId).add(entity);
							}));
							group.getMonsters().forEach((monster -> {
								MonsterData entityData = GenshinData.getMonsterDataMap().get(monster.getMonsterId());
								EntityMonster entity = new EntityMonster(this, entityData, monster.getPos().toPos(), finalWorldLevelOverride > 0 ? finalWorldLevelOverride : monster.getLevel());
								entity.getRotation().set(monster.getRot().toPos());
								entity.getGroupId().set(groupId);
								entity.getPoseId().set(monster.getPoseId());
								entity.getConfigId().set(monster.getConfigId());
								entity.getAffixList().set(monster.getAffix());
								entity.getTitleId().set(monster.getTitleId());
								entity.getSpecialNameId().set(monster.getSpecialNameId());
								entity.getMarkFlag().set(monster.getMarkFlag());
								entity.isElite().set(monster.isElite());
								this.preloadedEntityData.get(blockId).add(entity);
							}));
//					group.getNpcs().forEach((npc -> {
//						EntityClientGadget entity = new EntityNPC(this, player, )
//						this.addEntityDirectly(entity);
//						entities.add(entity);
//					}));
						});
						loadedBlock.push(blockId);
					}
				});
			List<GameEntity> disappear = new LinkedList<>();
			this.preloadedEntityData.values().forEach((list) -> list.stream().filter((gameEntity -> this.entities.containsKey(gameEntity.getId()))).forEach((entity) -> {
				if (entity.getPosition().distance(player.getPos()) >= DESPAWN_DISTANCE) {
					this.removeEntityDirectly(entity);
					disappear.add(entity);
				}
			}));
			List<GameEntity> appear = new LinkedList<>();
			this.preloadedEntityData.values().forEach((list) -> list.stream().filter((gameEntity -> !this.entities.containsKey(gameEntity.getId()))).forEach((entity) -> {
				if (entity.getPosition().distance(player.getPos()) <= SPAWN_DISTANCE) {
					this.addEntityDirectly(entity);
					appear.add(entity);
				}
			}));
			if (appear.size() > 0) this.broadcastPacket(new PacketSceneEntityAppearNotify(appear, VisionType.VISION_BORN));
			if (disappear.size() > 0) this.broadcastPacket(new PacketSceneEntityDisappearNotify(disappear, VisionType.VISION_REMOVE));
		} else {
			Grasscutter.getLogger().error("Player scene is wrong, could not get any scene groups from player " + player.getNickname());
		}

//		SpatialIndex<SpawnGroupEntry> list = GenshinDepot.getSpawnListById(this.getId());
//		Set<SpawnDataEntry> visible = new HashSet<>();
//
//		int RANGE = 100;
//		Collection<SpawnGroupEntry> entries = list.query(
//			new double[] {player.getPos().getX() - RANGE, player.getPos().getZ() - RANGE},
//			new double[] {player.getPos().getX() + RANGE, player.getPos().getZ() + RANGE}
//		);
//
//		for (SpawnGroupEntry entry : entries) {
//			for (SpawnDataEntry spawnData : entry.getSpawns()) {
//				visible.add(spawnData);
//			}
//		}

//		// Todo
//		List<GenshinEntity> toAdd = new LinkedList<>();
//		List<GenshinEntity> toRemove = new LinkedList<>();
//
//		for (SpawnDataEntry entry : visible) {
//			if (!this.getSpawnedEntities().contains(entry) && !this.getDeadSpawnedEntities().contains(entry)) {
//				// Spawn entity
//				MonsterData data = GenshinData.getMonsterDataMap().get(entry.getMonsterId());
//
//				if (data == null) {
//					continue;
//				}
//
//				EntityMonster entity = new EntityMonster(this, data, entry.getPos(), worldLevelOverride > 0 ? worldLevelOverride : 1);
//				entity.getRotation().set(entry.getRot());
//				entity.setGroupId(entry.getGroup().getGroupId());
//				entity.setPoseId(entry.getPoseId());
//				entity.setConfigId(entry.getConfigId());
//				entity.setSpawnEntry(entry);
//
//				toAdd.add(entity);
//				this.addEntityDirectly(entity);
//
//				// Add to spawned list
//				this.getSpawnedEntities().add(entry);
//			}
//		}
//
//		for (GenshinEntity entity : this.getEntities().values()) {
//			if (entity.getSpawnEntry() != null && !visible.contains(entity.getSpawnEntry())) {
//				toRemove.add(entity);
//				this.removeEntityDirectly(entity);
//			}
//		}
//
//		if (toAdd.size() > 0) {
//			this.broadcastPacket(new PacketSceneEntityAppearNotify(toAdd, VisionType.VisionBorn));
//		}
//		if (toRemove.size() > 0) {
//			this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VisionRemove));
//		}
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

	public void broadcastPacket(GenshinPacket packet) {
		// Send to all players - might have to check if player has been sent data packets
		for (GenshinPlayer player : this.getPlayers()) {
			player.getSession().send(packet);
		}
	}

	public void broadcastPacketToOthers(GenshinPlayer excludedPlayer, GenshinPacket packet) {
		// Optimization
		if (this.getPlayerCount() == 1 && this.getPlayers().get(0) == excludedPlayer) {
			return;
		}
		// Send to all players - might have to check if player has been sent data packets
		for (GenshinPlayer player : this.getPlayers()) {
			if (player == excludedPlayer) {
				continue;
			}
			// Send
			player.getSession().send(packet);
		}
	}
}
