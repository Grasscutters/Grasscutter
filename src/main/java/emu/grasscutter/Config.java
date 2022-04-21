package emu.grasscutter;

public final class Config {
	public String DispatchServerIp = "127.0.0.1";
	public String DispatchServerPublicIp = "";
	public int DispatchServerPort = 443;
	public String DispatchServerKeystorePath = "./keystore.p12";
	public String DispatchServerKeystorePassword = "";
	public Boolean UseSSL = true;
	
	public String GameServerName = "Test";
	public String GameServerIp = "127.0.0.1";
	public String GameServerPublicIp = "";
	public int GameServerPort = 22102;

	public String DatabaseUrl = "mongodb://localhost:27017";
	public String DatabaseCollection = "grasscutter";

	public String RESOURCE_FOLDER = "./resources/";
	public String DATA_FOLDER = "./data/";
	public String PACKETS_FOLDER = "./packets/";
	public String DUMPS_FOLDER = "./dumps/";
	public String KEY_FOLDER = "./keys/";
	public boolean LOG_PACKETS = false;

	public GameRates Game = new GameRates();
	public ServerOptions ServerOptions = new ServerOptions();

	public GameRates getGameRates() {
		return Game;
	}

	public ServerOptions getServerOptions() {
		return ServerOptions;
	}

	public static class GameRates {
		public float ADVENTURE_EXP_RATE = 1.0f;
		public float MORA_RATE = 1.0f;
		public float DOMAIN_DROP_RATE = 1.0f;
	}

	public static class ServerOptions {
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
		public boolean AutomaticallyCreateAccounts = false;
	}
}
