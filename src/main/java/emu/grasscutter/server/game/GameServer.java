package emu.grasscutter.server.game;

import static emu.grasscutter.config.Configuration.*;
import static emu.grasscutter.utils.lang.Language.translate;

import emu.grasscutter.*;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.battlepass.BattlePassSystem;
import emu.grasscutter.game.chat.ChatSystem;
import emu.grasscutter.game.chat.ChatSystemHandler;
import emu.grasscutter.game.combine.CombineManger;
import emu.grasscutter.game.drop.DropSystem;
import emu.grasscutter.game.drop.DropSystemLegacy;
import emu.grasscutter.game.dungeons.DungeonSystem;
import emu.grasscutter.game.expedition.ExpeditionSystem;
import emu.grasscutter.game.gacha.GachaSystem;
import emu.grasscutter.game.home.HomeWorld;
import emu.grasscutter.game.home.HomeWorldMPSystem;
import emu.grasscutter.game.managers.cooking.CookingCompoundManager;
import emu.grasscutter.game.managers.cooking.CookingManager;
import emu.grasscutter.game.managers.energy.EnergyManager;
import emu.grasscutter.game.managers.stamina.StaminaManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestSystem;
import emu.grasscutter.game.shop.ShopSystem;
import emu.grasscutter.game.systems.AnnouncementSystem;
import emu.grasscutter.game.systems.InventorySystem;
import emu.grasscutter.game.systems.MultiplayerSystem;
import emu.grasscutter.game.talk.TalkSystem;
import emu.grasscutter.game.tower.TowerSystem;
import emu.grasscutter.game.world.World;
import emu.grasscutter.game.world.WorldDataSystem;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.server.dispatch.DispatchClient;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.server.event.internal.ServerStartEvent;
import emu.grasscutter.server.event.internal.ServerStopEvent;
import emu.grasscutter.server.event.types.ServerEvent;
import emu.grasscutter.server.scheduler.ServerTaskScheduler;
import emu.grasscutter.task.TaskMap;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import kcp.highway.*;
import lombok.*;
import org.jetbrains.annotations.*;

@Getter
public final class GameServer extends KcpServer implements Iterable<Player> {
    // Game server base
    private final InetSocketAddress address;
    private final GameServerPacketHandler packetHandler;
    private final Map<Integer, Player> players;
    private final Set<World> worlds;
    private final Int2ObjectMap<HomeWorld> homeWorlds;

    @Setter private DispatchClient dispatchClient;

    // Server systems
    private final InventorySystem inventorySystem;
    private final GachaSystem gachaSystem;
    private final ShopSystem shopSystem;
    private final MultiplayerSystem multiplayerSystem;
    private final HomeWorldMPSystem homeWorldMPSystem;
    private final DungeonSystem dungeonSystem;
    private final ExpeditionSystem expeditionSystem;
    private final DropSystem dropSystem;
    private final DropSystemLegacy dropSystemLegacy;
    private final WorldDataSystem worldDataSystem;
    private final BattlePassSystem battlePassSystem;
    private final CombineManger combineSystem;
    private final TowerSystem towerSystem;
    private final AnnouncementSystem announcementSystem;
    private final QuestSystem questSystem;
    private final TalkSystem talkSystem;

    // Extra
    private final ServerTaskScheduler scheduler;
    private final TaskMap taskMap;

    private ChatSystemHandler chatManager;

    /**
     * @return The URI for the dispatch server.
     */
    @SneakyThrows
    public static URI getDispatchUrl() {
        return new URI(DISPATCH_INFO.dispatchUrl);
    }

    public GameServer() {
        this(getAdapterInetSocketAddress());
    }

    public GameServer(InetSocketAddress address) {
        // Check if we are in dispatch only mode.
        if (Grasscutter.getRunMode() == ServerRunMode.DISPATCH_ONLY) {
            // Set all the systems to null.
            this.scheduler = null;
            this.taskMap = null;

            this.address = null;
            this.packetHandler = null;
            this.dispatchClient = null;
            this.players = null;
            this.worlds = null;
            this.homeWorlds = null;

            this.inventorySystem = null;
            this.gachaSystem = null;
            this.shopSystem = null;
            this.multiplayerSystem = null;
            this.homeWorldMPSystem = null;
            this.dungeonSystem = null;
            this.expeditionSystem = null;
            this.dropSystem = null;
            this.dropSystemLegacy = null;
            this.worldDataSystem = null;
            this.battlePassSystem = null;
            this.combineSystem = null;
            this.towerSystem = null;
            this.announcementSystem = null;
            this.questSystem = null;
            this.talkSystem = null;
            return;
        }

        var channelConfig = new ChannelConfig();
        channelConfig.nodelay(true, GAME_INFO.kcpInterval, 2, true);
        channelConfig.setMtu(1400);
        channelConfig.setSndwnd(256);
        channelConfig.setRcvwnd(256);
        channelConfig.setTimeoutMillis(30 * 1000); // 30s
        channelConfig.setUseConvChannel(true);
        channelConfig.setAckNoDelay(false);

        this.init(GameSessionManager.getListener(), channelConfig, address);

        EnergyManager.initialize();
        StaminaManager.initialize();
        CookingManager.initialize();
        CookingCompoundManager.initialize();
        CombineManger.initialize();

        // Game Server base
        this.address = address;
        this.packetHandler = new GameServerPacketHandler(PacketHandler.class);
        this.dispatchClient = new DispatchClient(GameServer.getDispatchUrl());
        this.players = new ConcurrentHashMap<>();
        this.worlds = Collections.synchronizedSet(new HashSet<>());
        this.homeWorlds = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

        // Extra
        this.scheduler = new ServerTaskScheduler();
        this.taskMap = new TaskMap(true);

        // Create game systems
        this.inventorySystem = new InventorySystem(this);
        this.gachaSystem = new GachaSystem(this);
        this.shopSystem = new ShopSystem(this);
        this.multiplayerSystem = new MultiplayerSystem(this);
        this.homeWorldMPSystem = new HomeWorldMPSystem(this);
        this.dungeonSystem = new DungeonSystem(this);
        this.dropSystem = new DropSystem(this);
        this.dropSystemLegacy = new DropSystemLegacy(this);
        this.expeditionSystem = new ExpeditionSystem(this);
        this.combineSystem = new CombineManger(this);
        this.towerSystem = new TowerSystem(this);
        this.worldDataSystem = new WorldDataSystem(this);
        this.battlePassSystem = new BattlePassSystem(this);
        this.announcementSystem = new AnnouncementSystem(this);
        this.questSystem = new QuestSystem(this);
        this.talkSystem = new TalkSystem(this);

        // Chata manager
        this.chatManager = new ChatSystem(this);
    }

    private static InetSocketAddress getAdapterInetSocketAddress() {
        InetSocketAddress inetSocketAddress;
        if (GAME_INFO.bindAddress.equals("")) {
            inetSocketAddress = new InetSocketAddress(GAME_INFO.bindPort);
        } else {
            inetSocketAddress = new InetSocketAddress(GAME_INFO.bindAddress, GAME_INFO.bindPort);
        }
        return inetSocketAddress;
    }

    @Deprecated
    public ChatSystemHandler getChatManager() {
        return chatManager;
    }

    @Deprecated
    public void setChatManager(ChatSystemHandler chatManager) {
        this.chatManager = chatManager;
    }

    public ChatSystemHandler getChatSystem() {
        return chatManager;
    }

    public void setChatSystem(ChatSystemHandler chatManager) {
        this.chatManager = chatManager;
    }

    public void registerPlayer(Player player) {
        getPlayers().put(player.getUid(), player);
    }

    @Nullable public Player getPlayerByUid(int id) {
        return this.getPlayerByUid(id, false);
    }

    @Nullable public Player getPlayerByUid(int id, boolean allowOfflinePlayers) {
        // Console check
        if (id == GameConstants.SERVER_CONSOLE_UID) {
            return null;
        }

        // Get from online players
        Player player = this.getPlayers().get(id);

        if (!allowOfflinePlayers) {
            return player;
        }

        // Check database if character isnt here
        if (player == null) {
            player = DatabaseHelper.getPlayerByUid(id);
        }

        return player;
    }

    public Player getPlayerByAccountId(String accountId) {
        Optional<Player> playerOpt =
                getPlayers().values().stream()
                        .filter(player -> player.getAccount().getId().equals(accountId))
                        .findFirst();
        return playerOpt.orElse(null);
    }

    /**
     * Tries to find a player with the matching IP address.
     *
     * @param ipAddress The IP address. This should just be numbers without a port.
     * @return The player, or null if one could not be found.
     */
    public Player getPlayerByIpAddress(String ipAddress) {
        return this.getPlayers().values().stream()
                .map(Player::getSession)
                .filter(
                        session -> session != null && session.getAddress().getHostString().equals(ipAddress))
                .map(GameSession::getPlayer)
                .findFirst()
                .orElse(null);
    }

    public SocialDetail.Builder getSocialDetailByUid(int id) {
        // Get from online players
        Player player = this.getPlayerByUid(id, true);

        if (player == null) {
            return null;
        }

        return player.getSocialDetail();
    }

    public Account getAccountByName(String username) {
        Optional<Player> playerOpt =
                getPlayers().values().stream()
                        .filter(player -> player.getAccount().getUsername().equals(username))
                        .findFirst();
        if (playerOpt.isPresent()) {
            return playerOpt.get().getAccount();
        }
        return DatabaseHelper.getAccountByName(username);
    }

    public synchronized void onTick() {
        var tickStart = Instant.now();

        // Tick worlds and home worlds.
        this.worlds.removeIf(World::onTick);

        // Tick players.
        this.players.values().forEach(Player::onTick);

        // Tick scheduler.
        this.getScheduler().runTasks();

        // Call server tick event.
        ServerTickEvent event = new ServerTickEvent(tickStart, Instant.now());
        event.call();
    }

    public void registerWorld(World world) {
        this.getWorlds().add(world);
    }

    public void deregisterWorld(World world) {
        // TODO Auto-generated method stub
        world.save(); // Save the player's world
    }

    public HomeWorld getHomeWorldOrCreate(Player owner) {
        return this.getHomeWorlds()
                .computeIfAbsent(owner.getUid(), (uid) -> new HomeWorld(this, owner));
    }

    public void start() {
        if (Grasscutter.getRunMode() == ServerRunMode.GAME_ONLY) {
            // Connect to dispatch server.
            this.dispatchClient.connect();
        }

        // Schedule game loop.
        Timer gameLoop = new Timer();
        gameLoop.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            onTick();
                        } catch (Exception e) {
                            Grasscutter.getLogger().error(translate("messages.game.game_update_error"), e);
                        }
                    }
                },
                new Date(),
                1000L);
        Grasscutter.getLogger().info(translate("messages.status.free_software"));
        Grasscutter.getLogger()
                .info(translate("messages.game.address_bind", GAME_INFO.accessAddress, address.getPort()));
        ServerStartEvent event = new ServerStartEvent(ServerEvent.Type.GAME, OffsetDateTime.now());
        event.call();
    }

    public void onServerShutdown() {
        var event = new ServerStopEvent(ServerEvent.Type.GAME, OffsetDateTime.now());
        event.call();

        // Save players & the world.
        this.getPlayers().forEach((uid, player) -> player.getSession().close());
        this.getWorlds().forEach(World::save);

        Utils.sleep(1000L); // Wait 1 second for operations to finish.
        this.stop(); // Stop the server.

        try {
            var threadPool = GameSessionManager.getLogicThread();

            // Shutdown network thread.
            threadPool.shutdownGracefully();
            // Wait for the network thread to finish.
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                Grasscutter.getLogger().error("Logic thread did not terminate!");
            }
        } catch (InterruptedException ignored) {
        }
    }

    @NotNull @Override
    public Iterator<Player> iterator() {
        return this.getPlayers().values().iterator();
    }
}
