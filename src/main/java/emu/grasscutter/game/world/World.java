package emu.grasscutter.game.world;

import static emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType.SCRIPT;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.dungeon.DungeonData;
import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.Player.SceneLoadState;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.game.world.data.TeleportProperties;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo.*;
import emu.grasscutter.net.proto.EnterTypeOuterClass.EnterType;
import emu.grasscutter.scripts.data.SceneConfig;
import emu.grasscutter.server.event.player.PlayerTeleportEvent;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.packet.send.*;
import emu.grasscutter.utils.ConversionUtils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

public class World implements Iterable<Player> {
    @Getter private final GameServer server;
    @Getter private Player host;
    @Getter private final List<Player> players;
    @Getter private final Int2ObjectMap<Scene> scenes;

    @Getter private EntityWorld entity;
    private int nextEntityId = 0;
    private int nextPeerId = 0;
    private int worldLevel;

    @Getter private boolean isMultiplayer = false;
    @Getter private boolean timeLocked;

    private long lastUpdateTime;
    @Getter protected int tickCount = 0;
    @Getter private boolean isPaused = false;
    @Getter private long currentWorldTime;

    public World(Player player) {
        this(player, false);
    }

    public World(Player player, boolean isMultiplayer) {
        this.host = player;
        this.server = player.getServer();
        this.players = Collections.synchronizedList(new ArrayList<>());
        this.scenes = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

        // this.levelEntityId = this.getNextEntityId(EntityIdType.MPLEVEL);
        this.entity = new EntityWorld(this);
        this.worldLevel = player.getWorldLevel();
        this.isMultiplayer = isMultiplayer;
        this.timeLocked = player.getProperty(PlayerProperty.PROP_IS_GAME_TIME_LOCKED) != 0;

        this.lastUpdateTime = System.currentTimeMillis();
        this.currentWorldTime = host.getPlayerGameTime();

        this.host.getServer().registerWorld(this);
    }

    public World(GameServer server, Player owner) {
        this.server = server;
        this.host = owner;
        this.players = Collections.synchronizedList(new ArrayList<>());
        this.scenes = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
        this.entity = new EntityWorld(this);
        this.lastUpdateTime = System.currentTimeMillis();

        server.registerWorld(this);
    }

    public int getLevelEntityId() {
        return entity.getId();
    }

    /**
     * Gets the peer ID of the world's host.
     *
     * @return The peer ID of the world's host. 0 if the host is null.
     */
    public int getHostPeerId() {
        return this.getHost() == null ? 0 : this.getHost().getPeerId();
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

    protected synchronized void setHost(Player host) {
        this.host = host;
    }

    /**
     * Gets an associated scene by ID. Creates a new instance of the scene if it doesn't exist.
     *
     * @param sceneId The scene ID.
     * @return The scene.
     */
    public Scene getSceneById(int sceneId) {
        // Get scene normally
        var scene = this.getScenes().get(sceneId);
        if (scene != null) {
            return scene;
        }

        // Create scene from scene data if it doesn't exist
        var sceneData = GameData.getSceneDataMap().get(sceneId);
        if (sceneData != null) {
            scene = new Scene(this, sceneData);
            this.registerScene(scene);
            return scene;
        }

        return null;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    /**
     * Gets the next entity ID for the specified entity type.
     *
     * @param idType The entity type.
     * @return The next entity ID.
     */
    public int getNextEntityId(EntityIdType idType) {
        return (idType.getId() << 24) + ++this.nextEntityId;
    }

    public synchronized void addPlayer(Player player) {
        // Check if player already in
        if (this.getPlayers().contains(player)) {
            return;
        }

        // Remove player from prev world
        if (player.getWorld() != null) {
            player.getWorld().removePlayer(player);
        }

        // Register
        player.setWorld(this);
        this.getPlayers().add(player);

        // Set player variables
        player.setPeerId(this.getNextPeerId());
        player.getTeamManager().setEntity(new EntityTeam(player));
        // player.getTeamManager().setEntityId(this.getNextEntityId(EntityIdType.TEAM));

        // Copy main team to multiplayer team
        if (this.isMultiplayer()) {
            player
                    .getTeamManager()
                    .getMpTeam()
                    .copyFrom(
                            player.getTeamManager().getCurrentSinglePlayerTeamInfo(),
                            player.getTeamManager().getMaxTeamSize());
            player.getTeamManager().setCurrentCharacterIndex(0);

            if (player != this.getHost()) {
                this.broadcastPacket(
                        new PacketPlayerChatNotify(
                                player,
                                0,
                                SystemHint.newBuilder()
                                        .setType(SystemHintType.SYSTEM_HINT_TYPE_CHAT_ENTER_WORLD.getNumber())
                                        .build()));
            }
        }

        // Add to scene
        Scene scene = this.getSceneById(player.getSceneId());
        scene.addPlayer(player);

        // Info packet for other players
        if (this.getPlayers().size() > 1) {
            this.updatePlayerInfos(player);
        }
    }

    public synchronized void addPlayer(Player player, int newSceneId) {
        // Check if player already in
        if (this.getPlayers().contains(player)) {
            return;
        }

        // Remove player from prev world
        if (player.getWorld() != null) {
            player.getWorld().removePlayer(player);
        }

        // Register
        player.setWorld(this);
        this.getPlayers().add(player);

        // Set player variables
        player.setPeerId(this.getNextPeerId());
        player.getTeamManager().setEntity(new EntityTeam(player));
        // player.getTeamManager().setEntityId(this.getNextEntityId(EntityIdType.TEAM));

        // Copy main team to multiplayer team
        if (this.isMultiplayer()) {
            player
                    .getTeamManager()
                    .getMpTeam()
                    .copyFrom(
                            player.getTeamManager().getCurrentSinglePlayerTeamInfo(),
                            player.getTeamManager().getMaxTeamSize());
            player.getTeamManager().setCurrentCharacterIndex(0);

            if (player != this.getHost()) {
                this.broadcastPacket(
                        new PacketPlayerChatNotify(
                                player,
                                0,
                                SystemHint.newBuilder()
                                        .setType(SystemHintType.SYSTEM_HINT_TYPE_CHAT_ENTER_WORLD.getNumber())
                                        .build()));
            }
        }

        // Add to scene
        player.setSceneId(newSceneId);
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
                        this.getPlayers().stream()
                                .map(
                                        p ->
                                                p.getTeamManager().getEntity() == null
                                                        ? 0
                                                        : p.getTeamManager().getEntity().getId())
                                .toList()));

        // Deregister
        this.getPlayers().remove(player);
        player.setWorld(null);

        // Remove from scene
        Scene scene = this.getSceneById(player.getSceneId());
        scene.removePlayer(player);

        // Info packet for other players
        if (this.getPlayers().size() > 0) {
            this.updatePlayerInfos(player);
        }

        // Disband world if host leaves
        if (this.getHost() == player) {
            List<Player> kicked = new ArrayList<>(this.getPlayers());
            for (Player victim : kicked) {
                World world = new World(victim);
                world.addPlayer(victim);

                victim.sendPacket(
                        new PacketPlayerEnterSceneNotify(
                                victim,
                                EnterType.ENTER_TYPE_SELF,
                                EnterReason.TeamKick,
                                victim.getSceneId(),
                                victim.getPosition()));
            }
        } else {
            this.broadcastPacket(
                    new PacketPlayerChatNotify(
                            player,
                            0,
                            SystemHint.newBuilder()
                                    .setType(SystemHintType.SYSTEM_HINT_TYPE_CHAT_LEAVE_WORLD.getNumber())
                                    .build()));
        }
    }

    public void registerScene(Scene scene) {
        this.getScenes().put(scene.getId(), scene);
    }

    public void deregisterScene(Scene scene) {
        scene.saveGroups();
        this.getScenes().remove(scene.getId());
    }

    public void save() {
        this.getScenes().values().forEach(Scene::saveGroups);
    }

    public boolean transferPlayerToScene(Player player, int sceneId, Position pos) {
        return this.transferPlayerToScene(player, sceneId, TeleportType.INTERNAL, null, pos);
    }

    public boolean transferPlayerToScene(
            Player player, int sceneId, TeleportType teleportType, Position pos) {
        return this.transferPlayerToScene(player, sceneId, teleportType, null, pos);
    }

    public boolean transferPlayerToScene(Player player, int sceneId, DungeonData data) {
        return this.transferPlayerToScene(player, sceneId, TeleportType.DUNGEON, data, null);
    }

    public boolean transferPlayerToScene(
            Player player,
            int sceneId,
            TeleportType teleportType,
            DungeonData dungeonData,
            Position teleportTo) {
        EnterReason enterReason =
                switch (teleportType) {
                        // shouldn't affect the teleportation, but its clearer when inspecting the packets
                        // TODO add more conditions for different reason.
                    case INTERNAL -> EnterReason.TransPoint;
                    case WAYPOINT -> EnterReason.TransPoint;
                    case MAP -> EnterReason.TransPoint;
                    case COMMAND -> EnterReason.Gm;
                    case SCRIPT -> EnterReason.Lua;
                    case CLIENT -> EnterReason.ClientTransmit;
                    case DUNGEON -> EnterReason.DungeonEnter;
                    default -> EnterReason.None;
                };
        return transferPlayerToScene(
                player, sceneId, teleportType, enterReason, dungeonData, teleportTo);
    }

    public boolean transferPlayerToScene(
            Player player,
            int sceneId,
            TeleportType teleportType,
            EnterReason enterReason,
            DungeonData dungeonData,
            Position teleportTo) {
        // Get enter types
        val teleportProps =
                TeleportProperties.builder()
                        .sceneId(sceneId)
                        .teleportType(teleportType)
                        .enterReason(enterReason)
                        .teleportTo(teleportTo)
                        .enterType(EnterType.ENTER_TYPE_JUMP);

        val sceneData = GameData.getSceneDataMap().get(sceneId);
        if (dungeonData != null) {
            teleportProps
                    .teleportTo(dungeonData.getStartPosition())
                    .teleportRot(dungeonData.getStartRotation());
            teleportProps.enterType(EnterType.ENTER_TYPE_DUNGEON).enterReason(EnterReason.DungeonEnter);
            teleportProps.dungeonId(dungeonData.getId());
        } else if (player.getSceneId() == sceneId) {
            teleportProps.enterType(EnterType.ENTER_TYPE_GOTO);
        } else if (sceneData != null && sceneData.getSceneType() == SceneType.SCENE_HOME_WORLD) {
            // Home
            teleportProps.enterType(EnterType.ENTER_TYPE_SELF_HOME).enterReason(EnterReason.EnterHome);
        }

        return transferPlayerToScene(player, teleportProps.build());
    }

    public boolean transferPlayerToScene(Player player, TeleportProperties teleportProperties) {
        // Check if the teleport properties are valid.
        if (teleportProperties.getTeleportTo() == null)
            teleportProperties.setTeleportTo(player.getPosition());

        // Call player teleport event.
        PlayerTeleportEvent event =
                new PlayerTeleportEvent(player, teleportProperties, player.getPosition());
        // Call event and check if it was canceled.
        event.call();
        if (event.isCanceled()) {
            return false; // Teleport was canceled.
        }

        if (GameData.getSceneDataMap().get(teleportProperties.getSceneId()) == null) {
            return false;
        }

        Scene oldScene = player.getScene();
        var newScene = this.getSceneById(teleportProperties.getSceneId());

        // Move directly in the same scene.
        if (newScene == oldScene && teleportProperties.getTeleportType() == TeleportType.COMMAND) {
            // Set player position and rotation
            if (teleportProperties.getTeleportTo() != null) {
                player.getPosition().set(teleportProperties.getTeleportTo());
            }
            if (teleportProperties.getTeleportRot() != null) {
                player.getRotation().set(teleportProperties.getTeleportRot());
            }
            player.sendPacket(new PacketSceneEntityAppearNotify(player));
            return true;
        }

        if (oldScene != null) {
            // Don't deregister scenes if the player is going to tp back into them
            if (oldScene == newScene) {
                oldScene.setDontDestroyWhenEmpty(true);
            }
            oldScene.removePlayer(player);
        }

        if (newScene != null) {
            newScene.addPlayer(player);

            player.getTeamManager().applyAbilities(newScene);

            // Dungeon
            // Dungeon system is handling this already
            // if(dungeonData!=null){
            //     var dungeonManager = new DungeonManager(newScene, dungeonData);
            //     dungeonManager.startDungeon();
            // }

            SceneConfig config = newScene.getScriptManager().getConfig();
            if (teleportProperties.getTeleportTo() == null && config != null) {
                if (config.born_pos != null) {
                    teleportProperties.setTeleportTo(config.born_pos);
                }
                if (config.born_rot != null) {
                    teleportProperties.setTeleportRot(config.born_rot);
                }
            }
        }

        // Set player position and rotation
        if (teleportProperties.getTeleportTo() != null) {
            player.getPosition().set(teleportProperties.getTeleportTo());
        }
        if (teleportProperties.getTeleportRot() != null) {
            player.getRotation().set(teleportProperties.getTeleportRot());
        }

        if (oldScene != null && newScene != null && newScene != oldScene) {
            newScene.setPrevScenePoint(oldScene.getPrevScenePoint());
            oldScene.setDontDestroyWhenEmpty(false);
        }

        // Teleport packet
        player.sendPacket(new PacketPlayerEnterSceneNotify(player, teleportProperties));

        if (teleportProperties.getTeleportType() != TeleportType.INTERNAL
                && teleportProperties.getTeleportType() != SCRIPT) {
            player.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_ANY_MANUAL_TRANSPORT);
        }

        return true;
    }

    protected void updatePlayerInfos(Player paramPlayer) {
        for (Player player : this.getPlayers()) {
            // Dont send packets if player is logging in and filter out joining player
            if (!player.hasSentLoginPackets() || player == paramPlayer) {
                continue;
            }

            // Update team of all players since max players has been changed - Probably not the best way
            // to do it
            if (this.isMultiplayer()) {
                player
                        .getTeamManager()
                        .getMpTeam()
                        .copyFrom(
                                player.getTeamManager().getMpTeam(), player.getTeamManager().getMaxTeamSize());
                player.getTeamManager().updateTeamEntities(null);
            }

            // Dont send packets if player is loading into the scene
            if (player.getSceneLoadState().getValue() >= SceneLoadState.INIT.getValue()) {
                // World player info packets
                player.getSession().send(new PacketWorldPlayerInfoNotify(this));
                player.getSession().send(new PacketScenePlayerInfoNotify(this));
                player.getSession().send(new PacketWorldPlayerRTTNotify(this));

                // Team packets
                player.getSession().send(new PacketSyncTeamEntityNotify(player));
                player.getSession().send(new PacketSyncScenePlayTeamEntityNotify(player));
            }
        }
    }

    public void broadcastPacket(BasePacket packet) {
        // Send to all players - might have to check if player has been sent data packets
        for (Player player : this.getPlayers()) {
            player.getSession().send(packet);
        }
    }

    /**
     * Invoked every game tick.
     *
     * @return True if the world should be removed.
     */
    public boolean onTick() {
        // Check if there are players in this world.
        if (this.getPlayerCount() == 0) return true;
        // Tick all associated scenes.
        this.getScenes()
                .forEach(
                        (k, scene) -> {
                            if (scene.getPlayerCount() > 0) scene.onTick();
                        });

        // sync time every 10 seconds
        if (this.tickCount % 10 == 0) {
            this.getPlayers().forEach(p -> p.sendPacket(new PacketPlayerGameTimeNotify(p)));
        }

        // store updated world time every 60 seconds. (in-game hour)
        if (this.tickCount % 60 == 0 && !this.timeLocked) {
            this.getHost().updatePlayerGameTime(this.currentWorldTime);
        }

        this.tickCount++;
        return false;
    }

    public void close() {}

    /** Returns the in-game world time in real milliseconds. */
    public long getWorldTime() {
        if (!this.isPaused && !this.timeLocked) {
            var newUpdateTime = System.currentTimeMillis();
            this.currentWorldTime += (newUpdateTime - lastUpdateTime);
            this.lastUpdateTime = newUpdateTime;
        }

        return this.currentWorldTime;
    }

    /** Returns the current in game days world time in in-game minutes (0-1439) */
    public int getGameTime() {
        return (int) (getTotalGameTimeMinutes() % 1440);
    }

    /** Returns the current in game days world time in ingame hours (0-23) */
    public int getGameTimeHours() {
        return this.getGameTime() / 60;
    }

    /** Returns the total number of in game days that got completed since the beginning of the game */
    public long getTotalGameTimeDays() {
        return ConversionUtils.gameTimeToDays(getTotalGameTimeMinutes());
    }

    /**
     * Returns the total number of in game hours that got completed since the beginning of the game
     */
    public long getTotalGameTimeHours() {
        return ConversionUtils.gameTimeToHours(getTotalGameTimeMinutes());
    }

    /** Returns the elapsed in-game minutes since the creation of the world. */
    public long getTotalGameTimeMinutes() {
        return this.getWorldTime() / 1000;
    }

    /**
     * Sets the world's pause status. Updates players and scenes accordingly.
     *
     * @param paused True if the world should be paused.
     */
    public void setPaused(boolean paused) {
        // Check if this world is a multiplayer world.
        if (this.isMultiplayer) return;

        // Update the world time.
        this.getWorldTime();
        this.updateTime();

        // If the world is being un-paused, update the last update time.
        if (this.isPaused != paused && !paused) {
            this.lastUpdateTime = System.currentTimeMillis();
        }

        this.isPaused = paused;
        this.getPlayers().forEach(player -> player.setPaused(paused));
        this.getScenes().forEach((key, scene) -> scene.setPaused(paused));
    }

    /**
     * Changes the game time of the world.
     *
     * @param gameTime The time in game minutes.
     */
    public void changeTime(long gameTime) {
        this.currentWorldTime = gameTime;
    }

    /**
     * Changes the time of the world.
     *
     * @param time The new time in minutes.
     * @param days The number of days to add.
     */
    public void changeTime(int time, int days) {
        // Check if the time is locked.
        if (this.timeLocked) return;

        // Calculate time differences.
        var currentTime = this.getGameTime();
        var diff = time - currentTime;
        if (diff < 0) diff = 1440 + diff;

        // Update the world time.
        this.currentWorldTime += days * 1440 * 1000L + diff * 1000L;

        // Update all players.
        this.host.updatePlayerGameTime(currentWorldTime);
        this.players.forEach(
                player -> player.getQuestManager().queueEvent(QuestContent.QUEST_CONTENT_GAME_TIME_TICK));
    }

    /** Notifies all players of the current world time. */
    public void updateTime() {
        this.getPlayers().forEach(p -> p.sendPacket(new PacketPlayerGameTimeNotify(p)));
        this.getPlayers().forEach(p -> p.sendPacket(new PacketSceneTimeNotify(p)));
    }

    /**
     * Locks the world time.
     *
     * @param locked True if the world time should be locked.
     */
    public void lockTime(boolean locked) {
        this.timeLocked = locked;

        // Notify players of the locking.
        this.updateTime();
        this.getPlayers()
                .forEach(player -> player.setProperty(PlayerProperty.PROP_IS_GAME_TIME_LOCKED, locked));
    }

    @NotNull @Override
    public Iterator<Player> iterator() {
        return this.getPlayers().iterator();
    }
}
