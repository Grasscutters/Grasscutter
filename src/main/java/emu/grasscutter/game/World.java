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
import emu.grasscutter.data.GenshinData;
import emu.grasscutter.data.def.SceneData;
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
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class World implements Iterable<GenshinPlayer> {
	private final GenshinPlayer owner;
	private final List<GenshinPlayer> players;
	private final Int2ObjectMap<GenshinScene> scenes;
	
	private int levelEntityId;
	private int nextEntityId = 0;
	private int nextPeerId = 0;
	private int worldLevel;

	private boolean isMultiplayer;
	
	public World(GenshinPlayer player) {
		this(player, false);
	}
	
	public World(GenshinPlayer player, boolean isMultiplayer) {
		this.owner = player;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.scenes = new Int2ObjectOpenHashMap<>();
		
		this.levelEntityId = getNextEntityId(EntityIdType.MPLEVEL);
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

	public int getWorldLevel() {
		return worldLevel;
	}

	public void setWorldLevel(int worldLevel) {
		this.worldLevel = worldLevel;
	}

	public List<GenshinPlayer> getPlayers() {
		return players;
	}
	
	public Int2ObjectMap<GenshinScene> getScenes() {
		return this.scenes;
	}
	
	public GenshinScene getSceneById(int sceneId) {
		// Get scene normally
		GenshinScene scene = getScenes().get(sceneId);
		if (scene != null) {
			return scene;
		}
		
		// Create scene from scene data if it doesnt exist
		SceneData sceneData = GenshinData.getSceneDataMap().get(sceneId);
		if (sceneData != null) {
			scene = new GenshinScene(this, sceneData);
			this.registerScene(scene);
			return scene;
		}
		
		return null;
	}
	
	public int getPlayerCount() {
		return getPlayers().size();
	}
	
	public boolean isMultiplayer() {
		return isMultiplayer;
	}
	
	public int getNextEntityId(EntityIdType idType) {
		return (idType.getId() << 24) + ++this.nextEntityId;
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

		// Set player variables
		player.setPeerId(this.getNextPeerId());
		player.getTeamManager().setEntityId(getNextEntityId(EntityIdType.TEAM));
		
		// Copy main team to mp team
		if (this.isMultiplayer()) {
			player.getTeamManager().getMpTeam().copyFrom(player.getTeamManager().getCurrentSinglePlayerTeamInfo(), player.getTeamManager().getMaxTeamSize());
			player.getTeamManager().setCurrentCharacterIndex(0);
		}
		
		// Add to scene
		GenshinScene scene = this.getSceneById(player.getSceneId());
		scene.addPlayer(player);

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
		
		// Remove from scene
		GenshinScene scene = this.getSceneById(player.getSceneId());
		scene.removePlayer(player);

		// Info packet for other players
		if (this.getPlayers().size() > 0) {
			this.updatePlayerInfos(player);
		}

		// Disband world if host leaves
		if (getHost() == player) {
			List<GenshinPlayer> kicked = new ArrayList<>(this.getPlayers());
			for (GenshinPlayer victim : kicked) {
				World world = new World(victim);
				world.addPlayer(victim);
				
				victim.sendPacket(new PacketPlayerEnterSceneNotify(victim, EnterType.EnterSelf, EnterReason.TeamKick, victim.getSceneId(), victim.getPos()));
			}
		}
	}
	
	public void registerScene(GenshinScene scene) {
		this.getScenes().put(scene.getId(), scene);
	}
	
	public void deregisterScene(GenshinScene scene) {
		this.getScenes().remove(scene.getId());
	}
	
	public boolean transferPlayerToScene(GenshinPlayer player, int sceneId, Position pos) {
		if (GenshinData.getSceneDataMap().get(sceneId) == null) {
			return false;
		}
		
		Integer oldSceneId = null;

		if (player.getScene() != null) {
			oldSceneId = player.getScene().getId();
			player.getScene().removePlayer(player);
		}
		
		GenshinScene scene = this.getSceneById(sceneId);
		scene.addPlayer(player);
		player.getPos().set(pos);
		
		// Teleport packet
		if (oldSceneId.equals(sceneId)) {
			player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.EnterGoto, EnterReason.TransPoint, sceneId, pos));
		} else {
			player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.EnterJump, EnterReason.TransPoint, sceneId, pos));
		}
		return true;
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
	
	public void broadcastPacket(GenshinPacket packet) {
    	// Send to all players - might have to check if player has been sent data packets
    	for (GenshinPlayer player : this.getPlayers()) {
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
