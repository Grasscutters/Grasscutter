package emu.grasscutter;

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

	public String RunMode = "HYBRID"; // HYBRID, DISPATCH_ONLY, GAME_ONLY
	public GameServerOptions GameServer = new GameServerOptions();
	public DispatchServerOptions DispatchServer = new DispatchServerOptions();

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

		public boolean LOG_PACKETS = false;

		public int InventoryLimitWeapon = 2000;
		public int InventoryLimitRelic = 2000;
		public int InventoryLimitMaterial = 2000;
		public int InventoryLimitFurniture = 2000;
		public int InventoryLimitAll = 30000;
		public int MaxAvatarsInTeam = 4;
		public int MaxAvatarsInTeamMultiplayer = 4;
		public int MaxEntityLimit = 1000; // Max entity limit per world. // TODO: Enforce later.
		public boolean WatchGacha = false;

		public int[] WelcomeEmotes = {2007, 1002, 4010};
		public String WelcomeMotd = "";
		
		public String WelcomeMailSender  = "Yuuki";
		public String WelcomeMailTitle   = "Welcome to Yuuki Server";
		public String WelcomeMailContent = "Thank you for registering on Yuuki Server, as a thank you to you we give you a gift, please enjoy. \r\n\r\nCheck out our:\r\n<type=\"browser\" text=\"Discord\" href=\"https://discord.gg/tRYMG7Nm2D\"/>";		
		public int[] WelcomeMailItems = {1002,1003,1005,1006,1007,1014,1015,1016,1020,1021,1022,1023,1024,1025,1026,1027,1029,1030,1031,1032,1033,1034,1035,1036,1037,1038,1039,1041,1042,1043,1044,1045,1046,1047,1048,1049,1050,1051,1052,1053,1054,1055,1056,1057,1058,1062,1063,1064};

		public boolean EnableOfficialShop = true;

		public GameRates Game = new GameRates();

		public GameRates getGameRates() { return Game; }

		public static class GameRates {
			public float ADVENTURE_EXP_RATE = 1.0f;
			public float MORA_RATE = 1.0f;
			public float DOMAIN_DROP_RATE = 1.0f;
		}
	}
}
