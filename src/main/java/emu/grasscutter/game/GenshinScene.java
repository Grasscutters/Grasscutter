package emu.grasscutter.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.danilopianini.util.SpatialIndex;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.GenshinDepot;
import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.def.MonsterData;
import emu.grasscutter.data.def.SceneData;
import emu.grasscutter.data.def.WorldLevelData;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.EntityMonster;
import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.props.SceneType;
import emu.grasscutter.game.world.SpawnDataEntry;
import emu.grasscutter.game.world.SpawnDataEntry.SpawnGroupEntry;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class GenshinScene {
	private final World world;
	private final SceneData sceneData;
	private final List<GenshinPlayer> players;
	private final Int2ObjectMap<GenshinEntity> entities;
	
	private final Set<SpawnDataEntry> spawnedEntities;
	private final Set<SpawnDataEntry> deadSpawnedEntities;
	private boolean dontDestroyWhenEmpty;
	
	private int time;
	private ClimateType climate;
	private int weather;

	public GenshinScene(World world, SceneData sceneData) {
		this.world = world;
		this.sceneData = sceneData;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.entities = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

		this.time = 8 * 60;
		this.climate = ClimateType.CLIMATE_SUNNY;
		
		this.spawnedEntities = new HashSet<>();
		this.deadSpawnedEntities = new HashSet<>();
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

	public Int2ObjectMap<GenshinEntity> getEntities() {
		return entities;
	}
	
	public GenshinEntity getEntityById(int id) {
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

	public Set<SpawnDataEntry> getSpawnedEntities() {
		return spawnedEntities;
	}

	public Set<SpawnDataEntry> getDeadSpawnedEntities() {
		return deadSpawnedEntities;
	}

	public boolean isInScene(GenshinEntity entity) {
		return this.entities.containsKey(entity.getId());
	}
	
	public synchronized void addPlayer(GenshinPlayer player) {
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
	
	public synchronized void removePlayer(GenshinPlayer player) {
		// Remove player from scene
		getPlayers().remove(player);
		player.setScene(null);
		
		// Remove player avatars
		this.removePlayerAvatars(player);
		
		// Remove player gadgets
		for (EntityGadget gadget : player.getTeamManager().getGadgets()) {
			this.removeEntity(gadget);
		}
		
		// Deregister scene if not in use
		if (this.getEntities().size() <= 0 && !this.dontDestroyWhenEmpty()) {
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
			this.removeEntity(it.next(), VisionType.VisionRemove);
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
	
	private void addEntityDirectly(GenshinEntity entity) {
		getEntities().put(entity.getId(), entity);
	}
	
	public synchronized void addEntity(GenshinEntity entity) {
		this.addEntityDirectly(entity);
		this.broadcastPacket(new PacketSceneEntityAppearNotify(entity));
	}
	
	public synchronized void addEntities(Collection<GenshinEntity> entities) {
		for (GenshinEntity entity : entities) {
			this.addEntityDirectly(entity);
		}
		
		this.broadcastPacket(new PacketSceneEntityAppearNotify(entities, VisionType.VisionBorn));
	}
	
	private GenshinEntity removeEntityDirectly(GenshinEntity entity) {
		return getEntities().remove(entity.getId());
	}
	
	public void removeEntity(GenshinEntity entity) {
		this.removeEntity(entity, VisionType.VisionDie);
	}
	
	public synchronized void removeEntity(GenshinEntity entity, VisionType visionType) {
		GenshinEntity removed = this.removeEntityDirectly(entity);
		if (removed != null) {
			this.broadcastPacket(new PacketSceneEntityDisappearNotify(removed, visionType));
		}
	}
	
	public synchronized void replaceEntity(EntityAvatar oldEntity, EntityAvatar newEntity) {
		this.removeEntityDirectly(oldEntity);
		this.addEntityDirectly(newEntity);
		this.broadcastPacket(new PacketSceneEntityDisappearNotify(oldEntity, VisionType.VisionReplace));
		this.broadcastPacket(new PacketSceneEntityAppearNotify(newEntity, VisionType.VisionReplace, oldEntity.getId()));
	}
	
	public void showOtherEntities(GenshinPlayer player) {
		List<GenshinEntity> entities = new LinkedList<>();
		GenshinEntity currentEntity = player.getTeamManager().getCurrentAvatarEntity();
		
		for (GenshinEntity entity : this.getEntities().values()) {
			if (entity == currentEntity) {
				continue;
			}
			entities.add(entity);
		}
		
		player.sendPacket(new PacketSceneEntityAppearNotify(entities, VisionType.VisionMeet));
	}
	
	public void handleAttack(AttackResult result) {
		//GenshinEntity attacker = getEntityById(result.getAttackerId());
		GenshinEntity target = getEntityById(result.getDefenseId());
		
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
	
	public void killEntity(GenshinEntity target, int attackerId) {
		// Packet
		this.broadcastPacket(new PacketLifeStateChangeNotify(attackerId, target, LifeState.LIFE_DEAD));
		this.removeEntity(target);
		
		// Death event
		target.onDeath(attackerId);
	}
	
	public void onTick() {
		this.checkSpawns();
	}
	
	// TODO - Test
	public void checkSpawns() {
		SpatialIndex<SpawnGroupEntry> list = GenshinDepot.getSpawnListById(this.getId());
		Set<SpawnDataEntry> visible = new HashSet<>();
		
		for (GenshinPlayer player : this.getPlayers()) {
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
		WorldLevelData worldLevelData = GenshinData.getWorldLevelDataMap().get(getWorld().getWorldLevel());
		int worldLevelOverride = 0;
		
		if (worldLevelData != null) {
			worldLevelOverride = worldLevelData.getMonsterLevel();
		}
				
		// Todo
		List<GenshinEntity> toAdd = new LinkedList<>();
		List<GenshinEntity> toRemove = new LinkedList<>();
		
		for (SpawnDataEntry entry : visible) {
			if (!this.getSpawnedEntities().contains(entry) && !this.getDeadSpawnedEntities().contains(entry)) {
				// Spawn entity
				MonsterData data = GenshinData.getMonsterDataMap().get(entry.getMonsterId());
				
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
		
		for (GenshinEntity entity : this.getEntities().values()) {
			if (entity.getSpawnEntry() != null && !visible.contains(entity.getSpawnEntry())) {
				toRemove.add(entity);
			}
		}
		
		if (toAdd.size() > 0) {
			toAdd.stream().forEach(this::addEntityDirectly);
			this.broadcastPacket(new PacketSceneEntityAppearNotify(toAdd, VisionType.VisionBorn));
		}
		if (toRemove.size() > 0) {
			toRemove.stream().forEach(this::removeEntityDirectly);
			this.broadcastPacket(new PacketSceneEntityDisappearNotify(toRemove, VisionType.VisionRemove));
		}
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
		GenshinEntity entity = getEntities().get(entityId);
		
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
		
		this.broadcastPacketToOthers(gadget.getOwner(), new PacketSceneEntityDisappearNotify(gadget, VisionType.VisionDie));
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
