package emu.grasscutter;

import java.util.Locale;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.game.mail.Mail;

public final class Config {

	public String DatabaseUrl = "mongodb://localhost:27017";
	public String DatabaseCollection = "grasscutter";

	public String RESOURCE_FOLDER = "./resources/";
	public String DATA_FOLDER = "./data/";
	public String PACKETS_FOLDER = "./packets/";
	public String DUMPS_FOLDER = "./dumps/";
	public String KEY_FOLDER = "./keys/";
	public String SCRIPTS_FOLDER = "./resources/Scripts/";
	public String PLUGINS_FOLDER = "./plugins/";
	public String VERSION = "./VERSION";
	public String LANGUAGE_FOLDER = "./languages/";

	public ServerDebugMode DebugMode = ServerDebugMode.NONE; // ALL, MISSING, NONE
	public ServerRunMode RunMode = ServerRunMode.HYBRID; // HYBRID, DISPATCH_ONLY, GAME_ONLY
	public GameServerOptions GameServer = new GameServerOptions();
	public DispatchServerOptions DispatchServer = new DispatchServerOptions();
	public Locale LocaleLanguage = Locale.getDefault();

	public Boolean OpenStamina = true;
	public GameServerOptions getGameServerOptions() {
		return GameServer;
	}

	public DispatchServerOptions getDispatchOptions() { return DispatchServer; }

	public static class DispatchServerOptions {
		public String Ip = "0.0.0.0";
		public String PublicIp = "127.0.0.1";
		public int Port = 443;
		public int PublicPort = 0;
		public String KeystorePath = "./keystore.p12";
		public String KeystorePassword = "123456";
		public Boolean UseSSL = true;
		public Boolean FrontHTTPS = true;

		public boolean AutomaticallyCreateAccounts = true;
		public String[] defaultPermissions = new String[] {""};

		public RegionInfo[] GameServers = {};

		public RegionInfo[] getGameServers() {
			return GameServers;
		}

		public static class RegionInfo {
			public String Name = "os_usa";
			public String Title = "Test";
			public String Ip = "127.0.0.1";
			public int Port = 22102;
		}
	}

	public static class GameServerOptions {
		public String Name = "Yuuki";
		public String Ip = "0.0.0.0";
		public String PublicIp = "127.0.0.1";
		public int Port = 22102;
		public int PublicPort = 0;

		public String DispatchServerDatabaseUrl = "mongodb://localhost:27017";
		public String DispatchServerDatabaseCollection = "grasscutter";

		// Limiting for Public Servers
		public int CMD_Spawn = 150;
		public int CMD_Give = 10000000;
		public int CMD_Drop = 100;
		public boolean CMD_NoGiveTes = true;

		// Dangerous feature for public server, make your database go crazy!
		public boolean DropMo = false;
		public boolean DungeonMT = false;

		public int InventoryLimitWeapon = 2000;
		public int InventoryLimitRelic = 1500;
		public int InventoryLimitMaterial = 2000;
		public int InventoryLimitFurniture = 2000;
		public int InventoryLimitAll = 10000;

		public int MaxAvatarsInTeam = 4;
		public int MaxAvatarsInTeamMultiplayer = 10;

		 // Max entity limit per world. // TODO: Enforce later.
		public int MaxEntityLimit = 1000;
		public boolean WatchGacha = false;

		public String WelcomeMailSender  = "Yuuki";
		public String WelcomeMailTitle   = "Welcome to Yuuki Server";
		public String WelcomeMailContent = "";
		public Mail.MailItem[] WelcomeMailItems = {
				new Mail.MailItem(13509, 1, 1),
				new Mail.MailItem(201, 10000, 1),
		};

		public String ServerNickname = "YukiBot";
		public int ServerAvatarId = 10000002;

		public int[] WelcomeEmotes = {2007, 1002, 4010};
		public String WelcomeMotd = "";
		
		public boolean EnableOfficialShop = true;

		public GameRates Game = new GameRates();
		public GameRates getGameRates() { return Game; }

		public static class GameRates {
			public float ADVENTURE_EXP_RATE = 6.0f;
			public float MORA_RATE = 0.1f;
			public float DOMAIN_DROP_RATE = 0.1f;
		}
	}
}
