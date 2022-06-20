package emu.grasscutter.server.game;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.combine.CombineManger;
import emu.grasscutter.game.drop.DropManager;
import emu.grasscutter.game.dungeons.DungeonManager;
import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.expedition.ExpeditionManager;
import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.game.managers.InventoryManager;
import emu.grasscutter.game.managers.MultiplayerManager;
import emu.grasscutter.game.managers.chat.ChatManager;
import emu.grasscutter.game.managers.chat.ChatManagerHandler;
import emu.grasscutter.game.managers.energy.EnergyManager;
import emu.grasscutter.game.managers.stamina.StaminaManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.ServerQuestHandler;
import emu.grasscutter.game.shop.ShopManager;
import emu.grasscutter.game.tower.TowerScheduleManager;
import emu.grasscutter.game.world.World;
import emu.grasscutter.game.world.WorldDataManager;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.server.event.internal.ServerStartEvent;
import emu.grasscutter.server.event.internal.ServerStopEvent;
import emu.grasscutter.server.event.types.ServerEvent;
import emu.grasscutter.server.scheduler.ServerTaskScheduler;
import emu.grasscutter.task.TaskMap;
import kcp.highway.ChannelConfig;
import kcp.highway.KcpServer;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static emu.grasscutter.Configuration.GAME_INFO;
import static emu.grasscutter.utils.Language.translate;

public final class GameServer extends KcpServer {
    private final InetSocketAddress address;
    @Getter
    private final GameServerPacketHandler packetHandler;
    @Getter
    private final ServerQuestHandler questHandler;
    @Getter
    private final ServerTaskScheduler scheduler;

    @Getter
    private final Map<Integer, Player> players;
    @Getter
    private final Set<World> worlds;

    @Getter
    @Setter
    private ChatManagerHandler chatManager;

    @Getter
    private final InventoryManager inventoryManager;
    @Getter
    private final GachaManager gachaManager;
    @Getter
    private final ShopManager shopManager;
    @Getter
    private final MultiplayerManager multiplayerManager;
    @Getter
    private final DungeonManager dungeonManager;
    @Getter
    private final ExpeditionManager expeditionManager;
    @Getter
    private final CommandMap commandMap;
    @Getter
    private final TaskMap taskMap;
    @Getter
    private final DropManager dropManager;
    @Getter
    private final WorldDataManager worldDataManager;

    @Getter
    private final CombineManger combineManger;
    @Getter
    private final TowerScheduleManager towerScheduleManager;

    public GameServer() {
        this(getAdapterInetSocketAddress());
    }

    public GameServer(InetSocketAddress address) {
        ChannelConfig channelConfig = new ChannelConfig();
        channelConfig.nodelay(true, 40, 2, true);
        channelConfig.setMtu(1400);
        channelConfig.setSndwnd(256);
        channelConfig.setRcvwnd(256);
        channelConfig.setTimeoutMillis(30 * 1000);//30s
        channelConfig.setUseConvChannel(true);
        channelConfig.setAckNoDelay(false);

        this.init(GameSessionManager.getListener(), channelConfig, address);

        this.address = address;
        this.packetHandler = new GameServerPacketHandler(PacketHandler.class);
        this.questHandler = new ServerQuestHandler();
        this.scheduler = new ServerTaskScheduler();
        this.players = new ConcurrentHashMap<>();
        this.worlds = Collections.synchronizedSet(new HashSet<>());

        this.chatManager = new ChatManager(this);
        this.inventoryManager = new InventoryManager(this);
        this.gachaManager = new GachaManager(this);
        this.shopManager = new ShopManager(this);
        this.multiplayerManager = new MultiplayerManager(this);
        this.dungeonManager = new DungeonManager(this);
        this.commandMap = new CommandMap(true);
        this.taskMap = new TaskMap(true);
        this.dropManager = new DropManager(this);
        this.expeditionManager = new ExpeditionManager(this);
        this.combineManger = new CombineManger(this);
        this.towerScheduleManager = new TowerScheduleManager(this);
        this.worldDataManager = new WorldDataManager(this);

        StaminaManager.initialize();
        EnergyManager.initialize();
        DungeonChallenge.initialize();

        // Hook into shutdown event.
        Runtime.getRuntime().addShutdownHook(new Thread(this::onServerShutdown));
    }

    private static InetSocketAddress getAdapterInetSocketAddress() {
        InetSocketAddress inetSocketAddress;
        if (GAME_INFO.bindAddress.equals("")) {
            inetSocketAddress = new InetSocketAddress(GAME_INFO.bindPort);
        } else {
            inetSocketAddress = new InetSocketAddress(
                GAME_INFO.bindAddress,
                GAME_INFO.bindPort
            );
        }
        return inetSocketAddress;
    }

    public void registerPlayer(Player player) {
        this.getPlayers().put(player.getUid(), player);
    }

    public Player getPlayerByUid(int id) {
        return this.getPlayerByUid(id, false);
    }

    public Player getPlayerByUid(int id, boolean allowOfflinePlayers) {
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
        Optional<Player> playerOpt = this.getPlayers().values().stream().filter(player -> player.getAccount().getId().equals(accountId)).findFirst();
        return playerOpt.orElse(null);
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
        Optional<Player> playerOpt = this.getPlayers().values().stream().filter(player -> player.getAccount().getUsername().equals(username)).findFirst();
        if (playerOpt.isPresent()) {
            return playerOpt.get().getAccount();
        }
        return DatabaseHelper.getAccountByName(username);
    }

    public synchronized void onTick() {
        var tickStart = Instant.now();

        // Tick worlds.
        Iterator<World> it = this.getWorlds().iterator();
        while (it.hasNext()) {
            World world = it.next();

            if (world.getPlayerCount() == 0) {
                it.remove();
            }

            world.onTick();
        }

        // Tick players.
        for (Player player : this.getPlayers().values()) {
            player.onTick();
        }

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

    }

    public void start() {
        // Schedule game loop.
        Timer gameLoop = new Timer();
        gameLoop.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    GameServer.this.onTick();
                } catch (Exception e) {
                    Grasscutter.getLogger().error(translate("messages.game.game_update_error"), e);
                }
            }
        }, new Date(), 1000L);
        Grasscutter.getLogger().info(translate("messages.status.free_software"));
        Grasscutter.getLogger().info(translate("messages.game.port_bind", Integer.toString(this.address.getPort())));
        ServerStartEvent event = new ServerStartEvent(ServerEvent.Type.GAME, OffsetDateTime.now());
        event.call();
    }

    public void onServerShutdown() {
        ServerStopEvent event = new ServerStopEvent(ServerEvent.Type.GAME, OffsetDateTime.now());
        event.call();

        // Kick and save all players
        List<Player> list = new ArrayList<>(this.getPlayers().size());
        list.addAll(this.getPlayers().values());

        for (Player player : list) {
            player.getSession().close();
        }
    }
}
