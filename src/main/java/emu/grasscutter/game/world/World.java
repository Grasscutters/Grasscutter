package emu.grasscutter.game.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.game.props.EnterReason;
import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.ExcelSceneData;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.server.packet.send.PacketDelTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketPlayerEnterSceneNotify;
import emu.grasscutter.server.packet.send.PacketScenePlayerInfoNotify;
import emu.grasscutter.server.packet.send.PacketSyncScenePlayTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketSyncTeamEntityNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerInfoNotify;
import emu.grasscutter.server.packet.send.PacketWorldPlayerRTTNotify;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class World implements Iterable<Player> {
	private final Player owner;
	private final List<Player> players;
	private final Int2ObjectMap<Scene> scenes;
	
	private int levelEntityId;
	private int nextEntityId = 0;
	private int nextPeerId = 0;
	private int worldLevel;

	private boolean isMultiplayer;
	
	public World(Player player) {
		this(player, false);
	}
	
	public World(Player player, boolean isMultiplayer) {
		this.owner = player;
		this.players = Collections.synchronizedList(new ArrayList<>());
		this.scenes = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
		
		this.levelEntityId = getNextEntityId(EntityIdType.MPLEVEL);
		this.worldLevel = player.getWorldLevel();
		this.isMultiplayer = isMultiplayer;

		this.owner.getServer().registerWorld(this);
	}
	
	public Player getHost() {
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

	public List<Player> getPlayers() {
		return players;
	}
	
	public Int2ObjectMap<Scene> getScenes() {
		return this.scenes;
	}
	
	public Scene getSceneById(int sceneId) {
		// Get scene normally
		Scene scene = getScenes().get(sceneId);
		if (scene != null) {
			return scene;
		}
		
		// Create scene from scene data if it doesnt exist
		ExcelSceneData sceneData = GameData.getSceneDataMap().get(sceneId);
		if (sceneData != null) {
			scene = new Scene(this, sceneData);
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
	
	public synchronized void addPlayer(Player player) {
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
		Scene scene = this.getSceneById(player.getSceneId());
		scene.addPlayer(player);

		// Info packet for other players
		if (this.getPlayers().size() > 1) {
			this.updatePlayerInfos(player);
		}
	}

	public synchronized void removePlayer(Player player) {
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
		Scene scene = this.getSceneById(player.getSceneId());
		scene.removePlayer(player);

		// Info packet for other players
		if (this.getPlayers().size() > 0) {
			this.updatePlayerInfos(player);
		}

		// Disband world if host leaves
		if (getHost() == player) {
			List<Player> kicked = new ArrayList<>(this.getPlayers());
			for (Player victim : kicked) {
				World world = new World(victim);
				world.addPlayer(victim);
				
				victim.sendPacket(new PacketPlayerEnterSceneNotify(victim, EnterType.ENTER_SELF, EnterReason.TeamKick, victim.getSceneId(), victim.getPos()));
			}
		}
	}
	
	public void registerScene(Scene scene) {
		this.getScenes().put(scene.getId(), scene);
	}
	
	public void deregisterScene(Scene scene) {
		this.getScenes().remove(scene.getId());
	}
	
	public boolean transferPlayerToScene(Player player, int sceneId, Position pos) {
		if (GameData.getSceneDataMap().get(sceneId) == null) {
			return false;
		}
		
		Scene oldScene = null;

		if (player.getScene() != null) {
			oldScene = player.getScene();

			// Dont deregister scenes if the player is going to tp back into them
			if (oldScene.getId() == sceneId) {
				oldScene.setDontDestroyWhenEmpty(true);
			}

			oldScene.removePlayer(player);
		}
		
		Scene newScene = this.getSceneById(sceneId);
		newScene.addPlayer(player);
		player.getPos().set(pos);
		
		if (oldScene != null) {
			oldScene.setDontDestroyWhenEmpty(false);
		}

		// Teleport packet
		if (oldScene == newScene) {
			player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.ENTER_GOTO, EnterReason.TransPoint, sceneId, pos));
		} else {
			player.sendPacket(new PacketPlayerEnterSceneNotify(player, EnterType.ENTER_JUMP, EnterReason.TransPoint, sceneId, pos));
		}
		return true;
	}
	
	private void updatePlayerInfos(Player paramPlayer) {
		for (Player player : getPlayers()) {
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
	
	public void broadcastPacket(BasePacket packet) {
    	// Send to all players - might have to check if player has been sent data packets
    	for (Player player : this.getPlayers()) {
    		player.getSession().send(packet);
    	}
	}
	
	public void onTick() {
		for (Scene scene : this.getScenes().values()) {
			scene.onTick();
		}
	}

	public void close() {
		
	}

	@Override
	public Iterator<Player> iterator() {
		return getPlayers().iterator();
	}
}
