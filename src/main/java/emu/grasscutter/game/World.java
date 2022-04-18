package emu.grasscutter.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import emu.grasscutter.game.entity.GenshinEntity;
import emu.grasscutter.game.props.ClimateType;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.game.props.LifeState;
import emu.grasscutter.game.GenshinPlayer.SceneLoadState;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.proto.AttackResultOuterClass.AttackResult;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.net.proto.VisionTypeOuterClass.VisionType;
import emu.grasscutter.server.packet.send.PacketDelTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketEntityFightPropUpdateNotify;
import emu.grasscutter.server.packet.send.PacketLifeStateChangeNotify;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.server.packet.send.PacketSceneEntityDisappearNotify;
import emu.grasscutter.server.packet.send.PacketScenePlayerInfoNotify;
import emu.grasscutter.server.packet.send.PacketSyncScenePlayTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketSyncTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerInfoNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerRTTNotify;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class World implements Iterable<GenshinPlayer> {
	private final GenshinPlayer owner;
	private final List<GenshinPlayer> players;
	
	private int levelEntityId;
	private int nextEntityId = 0;
	private int nextPeerId = 0;
	private final Int2ObjectMap<GenshinEntity> entities;
	
	private int worldLevel;
	private int sceneId;
	private int time;
	private ClimateType climate;
	private boolean isMultiplayer;
	private boolean isDungeon;
	
	public World(GenshinPlayer player) {
		this(player, false);
	}
	
	public World(GenshinPlayer player, boolean isMultiplayer) {
		this.owner = player;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.entities = new Int2ObjectOpenHashMap<>();
		this.levelEntityId = getNextEntityId(EntityIdType.MPLEVEL);
		this.sceneId = player.getSceneId();
		this.time = 8 * 60;
		this.climate = ClimateType.CLIMATE_SUNNY;
		this.worldLevel = player.getWorldLevel();
		this.isMultiplayer = isMultiplayer;
	}
	
	public GenshinPlayer getHost() {
		return owner;
	}

	public int getLevelEntityId() {
		return levelEntityId;
	}

	public int getHostPeerId() {
		if (this.getHost() == null) {
			return 0;
		}
		return this.getHost().getPeerId();
	}
	
	public int getNextPeerId() {
		return ++this.nextPeerId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getTime() {
		return time;
	}

	public void changeTime(int time) {
		this.time = time % 1440;
	}

	public int getWorldLevel() {
		return worldLevel;
	}

	public void setWorldLevel(int worldLevel) {
		this.worldLevel = worldLevel;
	}

	public ClimateType getClimate() {
		return climate;
	}

	public void setClimate(ClimateType climate) {
		this.climate = climate;
	}

	public List<GenshinPlayer> getPlayers() {
		return players;
	}
	
	public int getPlayerCount() {
		return getPlayers().size();
	}
	
	public Int2ObjectMap<GenshinEntity> getEntities() {
		return this.entities;
	}
	
	public boolean isInWorld(GenshinEntity entity) {
		return this.entities.containsKey(entity.getId());
	}
	
	public boolean isMultiplayer() {
		return isMultiplayer;
	}
	
	public boolean isDungeon() {
		return isDungeon;
	}
	
	public int getNextEntityId(EntityIdType idType) {
		return (idType.getId() << 24) + ++this.nextEntityId;
	}
	
	public GenshinEntity getEntityById(int id) {
		return this.entities.get(id);
	}
	
	public synchronized void addPlayer(GenshinPlayer player) {
		// Check if player already in
		if (getPlayers().contains(player)) {
			return;
		}
		
		// Remove player from prev world
		if (player.getWorld() != null) {
			player.getWorld().removePlayer(player);
		}
		
		// Register
		player.setWorld(this);
		getPlayers().add(player);
		
		player.setPeerId(this.getNextPeerId());
		player.getTeamManager().setEntityId(getNextEntityId(EntityIdType.TEAM));
		
		// Setup team avatars
		this.setupPlayerAvatars(player);
		
		// Info packet for other players
		if (this.getPlayers().size() > 1) {
			this.updatePlayerInfos(player);
		}
	}

	public synchronized void removePlayer(GenshinPlayer player) {
		// Remove team entities
		player.sendPacket(
				new PacketDelTeamEntityNotify(
						player.getSceneId(), 
						getPlayers().stream().map(p -> p.getTeamManager().getEntityId()).collect(Collectors.toList())
				)
		);
		
		// Deregister
		getPlayers().remove(player);
		player.setWorld(null);
		
		this.removePlayerAvatars(player);
		
		// Info packet for other players
		if (this.getPlayers().size() > 0) {
			this.updatePlayerInfos(player);
		}
		
		// Remove player gadgets
		for (EntityGadget gadget : player.getTeamManager().getGadgets()) {
			this.removeEntity(gadget);
		}
		
		// Disband world if host leaves
		if (getHost() == player) {
			List<GenshinPlayer> kicked = new ArrayList<>(this.getPlayers());
			for (GenshinPlayer victim : kicked) {
				World world = new World(victim);
				world.addPlayer(victim);
				
				victim.sendPacket(new PacketPlayerEnterSceneNotify(victim, EnterType.EnterSelf, EnterReason.TeamKick, victim.getWorld().getSceneId(), victim.getPos()));
			}
		}
	}
	
	private void updatePlayerInfos(GenshinPlayer paramPlayer) {
		for (GenshinPlayer player : getPlayers()) {
			// Dont send packets if player is loading in and filter out joining player
			if (!player.hasSentAvatarDataNotify() || player.getSceneLoadState().getValue() < SceneLoadState.INIT.getValue() || player == paramPlayer) {
				continue;
			}
			
			// Update team of all players since max players has been changed - Probably not the best way to do it
			if (this.isMultiplayer()) {
				player.getTeamManager().getMpTeam().copyFrom(player.getTeamManager().getMpTeam(), player.getTeamManager().getMaxTeamSize());
				player.getTeamManager().updateTeamEntities(null);
			}

			// World player info packets
			player.getSession().send(new PacketWorldPlayerInfoNotify(this));
			player.getSession().send(new PacketScenePlayerInfoNotify(this));
			player.getSession().send(new PacketWorldPlayerRTTNotify(this));
			
			// Team packets
			player.getSession().send(new PacketSyncTeamEntityNotify(player));
			player.getSession().send(new PacketSyncScenePlayTeamEntityNotify(player));
		}
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
	
	private void setupPlayerAvatars(GenshinPlayer player) {
		// Copy main team to mp team
		if (this.isMultiplayer()) {
			player.getTeamManager().getMpTeam().copyFrom(player.getTeamManager().getCurrentSinglePlayerTeamInfo(), player.getTeamManager().getMaxTeamSize());
		}
		
		// Clear entities from old team
		player.getTeamManager().getActiveTeam().clear();

		// Add new entities for player
		TeamInfo teamInfo = player.getTeamManager().getCurrentTeamInfo();
		for (int avatarId : teamInfo.getAvatars()) {
			EntityAvatar entity = new EntityAvatar(this, player.getAvatars().getAvatarById(avatarId));
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
		if (isInWorld(player.getTeamManager().getCurrentAvatarEntity())) {
			return;
		}
		
		if (player.getTeamManager().getCurrentAvatarEntity().getFightProperty(FightProperty.FIGHT_PROP_CUR_HP) <= 0f) {
			player.getTeamManager().getCurrentAvatarEntity().setFightProperty(FightProperty.FIGHT_PROP_CUR_HP, 1f);
		}
		
		this.addEntity(player.getTeamManager().getCurrentAvatarEntity());
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
			if (((EntityAvatar) target).getPlayer().hasGodmode()) {
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
	
	public void close() {
		
	}

	@Override
	public Iterator<GenshinPlayer> iterator() {
		return getPlayers().iterator();
	}
}
