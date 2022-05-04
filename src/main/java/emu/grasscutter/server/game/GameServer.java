package emu.grasscutter.server.game;

import emu.grasscutter.GameConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.drop.DropManager;
import emu.grasscutter.game.dungeons.DungeonManager;
import emu.grasscutter.game.gacha.GachaManager;
import emu.grasscutter.game.managers.ChatManager;
import emu.grasscutter.game.managers.InventoryManager;
import emu.grasscutter.game.managers.MultiplayerManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.shop.ShopManager;
import emu.grasscutter.game.world.World;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;
import emu.grasscutter.netty.KcpServer;
import emu.grasscutter.server.event.types.ServerEvent;
import emu.grasscutter.server.event.game.ServerTickEvent;
import emu.grasscutter.server.event.internal.ServerStartEvent;
import emu.grasscutter.server.event.internal.ServerStopEvent;
import emu.grasscutter.task.TaskMap;

import java.net.InetSocketAddress;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class GameServer extends KcpServer {
	private final InetSocketAddress address;
	private final GameServerPacketHandler packetHandler;

	private final Map<Integer, Player> players;
	private final Set<World> worlds;
	
	private final ChatManager chatManager;
	private final InventoryManager inventoryManager;
	private final GachaManager gachaManager;
	private final ShopManager shopManager;
	private final MultiplayerManager multiplayerManager;
	private final DungeonManager dungeonManager;
	private final CommandMap commandMap;
	private final TaskMap taskMap;
	private final DropManager dropManager;
	
	public GameServer(InetSocketAddress address) {
		super(address);

		this.setServerInitializer(new GameServerInitializer(this));
		this.address = address;
		this.packetHandler = new GameServerPacketHandler(PacketHandler.class);
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
		
		// Schedule game loop.
		Timer gameLoop = new Timer();
		gameLoop.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					onTick();
				} catch (Exception e) {
					Grasscutter.getLogger().error(Grasscutter.getLanguage().An_error_occurred_during_game_update, e);
				}
			}
		}, new Date(), 1000L);
		
		// Hook into shutdown event.
		Runtime.getRuntime().addShutdownHook(new Thread(this::onServerShutdown));
	}
	
	public GameServerPacketHandler getPacketHandler() {
		return packetHandler;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	public Set<World> getWorlds() {
		return worlds;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public GachaManager getGachaManager() {
		return gachaManager;
	}
	
	public ShopManager getShopManager() {
		return shopManager;
	}

	public MultiplayerManager getMultiplayerManager() {
		return multiplayerManager;
	}

	public DropManager getDropManager() {
		return dropManager;
	}
	
	public DungeonManager getDungeonManager() {
		return dungeonManager;
	}
	
	public CommandMap getCommandMap() {
		return this.commandMap;
	}

	public TaskMap getTaskMap() {
		return this.taskMap;
	}
	
	public void registerPlayer(Player player) {
		getPlayers().put(player.getUid(), player);
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
			player = DatabaseHelper.getPlayerById(id);
		}
		
		return player;
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
		Optional<Player> playerOpt = getPlayers().values().stream().filter(player -> player.getAccount().getUsername().equals(username)).findFirst();
		if (playerOpt.isPresent()) {
			return playerOpt.get().getAccount();
		}
		return DatabaseHelper.getAccountByName(username);
	}
	
	public void onTick() throws Exception {
		Iterator<World> it = this.getWorlds().iterator();
		while (it.hasNext()) {
			World world = it.next();
			
			if (world.getPlayerCount() == 0) {
				it.remove();
			}
			
			world.onTick();
		}
		
		for (Player player : this.getPlayers().values()) {
			player.onTick();
		}
  
		ServerTickEvent event = new ServerTickEvent(); event.call();
	}
	
	public void registerWorld(World world) {
		this.getWorlds().add(world);
	}
	
	public void deregisterWorld(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartFinish() {
		Grasscutter.getLogger().info(Grasscutter.getLanguage().Grasscutter_is_free);
		Grasscutter.getLogger().info(Grasscutter.getLanguage().Game_start_port.replace("{port}", Integer.toString(address.getPort())));
		ServerStartEvent event = new ServerStartEvent(ServerEvent.Type.GAME, OffsetDateTime.now()); event.call();
	}
	
	public void onServerShutdown() {
		ServerStopEvent event = new ServerStopEvent(ServerEvent.Type.GAME, OffsetDateTime.now()); event.call();

		// Kick and save all players
		List<Player> list = new ArrayList<>(this.getPlayers().size());
		list.addAll(this.getPlayers().values());
		
		for (Player player : list) {
			player.getSession().close();
		}
	}
}
