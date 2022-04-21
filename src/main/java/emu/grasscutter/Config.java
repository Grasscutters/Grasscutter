package emu.grasscutter;

public final class Config {

	public String DatabaseUrl = "mongodb://localhost:27017";
	public String DatabaseCollection = "grasscutter";
	
	public String RESOURCE_FOLDER = "./resources/";
	public String DATA_FOLDER = "./data/";
	public String PACKETS_FOLDER = "./packets/";
	public String DUMPS_FOLDER = "./dumps/";
	public String KEY_FOLDER = "./keys/";

	public GameServerOptions GameServer = new GameServerOptions();
	public DispatchServerOptions DispatchServer = new DispatchServerOptions();

	public GameServerOptions getGameServerOptions() {
		return GameServer;
	}

	public DispatchServerOptions getDispatchOptions() { return DispatchServer; }

	public static class DispatchServerOptions {
		public String Ip = "127.0.0.1";
		public String PublicIp = "";
		public int Port = 443;
		public int UploadLogPort = 80;
		public String KeystorePath = "./keystore.p12";
		public String KeystorePassword = "";
		public Boolean UseSSL = true;

		public boolean AutomaticallyCreateAccounts = false;
	}
	
	public static class GameServerOptions {
		public String Name = "Test";
		public String Ip = "127.0.0.1";
		public String PublicIp = "";
		public int Port = 22102;

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
		public String WelcomeMotd = "Welcome to Grasscutter emu";

		public GameRates Game = new GameRates();

		public GameRates getGameRates() { return Game; }

		public static class GameRates {
			public float ADVENTURE_EXP_RATE = 1.0f;
			public float MORA_RATE = 1.0f;
			public float DOMAIN_DROP_RATE = 1.0f;
		}
	}
}
