package emu.grasscutter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.utils.Utils;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Logger;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Crypto;


public final class Grasscutter {
	private static final Logger log = (Logger) LoggerFactory.getLogger(Grasscutter.class);
	private static Config config;
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final File configFile = new File("./config.json");
	
	public static RunMode MODE = RunMode.BOTH;
	private static DispatchServer dispatchServer;
	private static GameServer gameServer;
	private static PluginManager pluginManager;
	
	public static final Reflections reflector = new Reflections();
	
	static {
		// Declare logback configuration.
		System.setProperty("logback.configurationFile", "src/main/resources/logback.xml");
		
		// Load server configuration.
		Grasscutter.loadConfig();
		
		// Check server structure.
		Utils.startupCheck();
	}
	
    public static void main(String[] args) throws Exception {
    	Crypto.loadKeys();
		for (String arg : args) {
			switch (arg.toLowerCase()) {
				case "-auth" -> MODE = RunMode.AUTH;
				case "-game" -> MODE = RunMode.GAME;
				case "-handbook" -> {
					Tools.createGmHandbook(); return;
				}
			}
		}
		
		// Initialize server.
		Grasscutter.getLogger().info("Starting Grasscutter...");
		
		// Load all resources.
		ResourceLoader.loadAll();
		// Database
		DatabaseManager.initialize();

		// Create server instances.
		dispatchServer = new DispatchServer();
		gameServer = new GameServer(new InetSocketAddress(getConfig().getGameServerOptions().Ip, getConfig().getGameServerOptions().Port));
		
		// Create plugin manager instance.
		pluginManager = new PluginManager();
		
		// Start servers.
		if(getConfig().RunMode.equalsIgnoreCase("HYBRID")) {
			dispatchServer.start();
			gameServer.start();
		} else if (getConfig().RunMode.equalsIgnoreCase("DISPATCH_ONLY")) {
			dispatchServer.start();
		} else if (getConfig().RunMode.equalsIgnoreCase("GAME_ONLY")) {
			gameServer.start();
		} else {
			getLogger().error("Invalid server run mode. " + getConfig().RunMode);
			getLogger().error("Server run mode must be 'HYBRID', 'DISPATCH_ONLY', or 'GAME_ONLY'. Unable to start Grasscutter...");
			getLogger().error("Shutting down...");
			System.exit(1);
		}
		
		// Enable all plugins.
		pluginManager.enablePlugins();
		
		// Open console.
		startConsole();
		// Hook into shutdown event.
		Runtime.getRuntime().addShutdownHook(new Thread(Grasscutter::onShutdown));
    }

	/**
	 * Server shutdown event.
	 */
	private static void onShutdown() {
		// Disable all plugins.
		pluginManager.disablePlugins();
	}
	
	public static void loadConfig() {
		try (FileReader file = new FileReader(configFile)) {
			config = gson.fromJson(file, Config.class);
			saveConfig();
		} catch (Exception e) {
			Grasscutter.config = new Config(); 
			saveConfig();
		}
	}
	
	public static void saveConfig() {
		try (FileWriter file = new FileWriter(configFile)) {
			file.write(gson.toJson(config));
		} catch (Exception e) {
			Grasscutter.getLogger().error("Unable to save config file.");
		}
	}
	
	public static void startConsole() {
		String input;
		getLogger().info("Done! For help, type \"help\"");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while ((input = br.readLine()) != null) {
				try {
					if(getConfig().RunMode.equalsIgnoreCase("DISPATCH_ONLY")) {
						getLogger().error("Commands are not supported in dispatch only mode.");
						return;
					}
					
					CommandMap.getInstance().invoke(null, input);
				} catch (Exception e) {
					Grasscutter.getLogger().error("Command error:", e);
				}
			}
		} catch (Exception e) {
			Grasscutter.getLogger().error("An error occurred.", e);
		}
	}
	
	public enum RunMode {
		BOTH,
		AUTH,
		GAME
	}

	public static Config getConfig() {
		return config;
	}

	public static Logger getLogger() {
		return log;
	}

	public static Gson getGsonFactory() {
		return gson;
	}

	public static DispatchServer getDispatchServer() {
		return dispatchServer;
	}

	public static GameServer getGameServer() {
		return gameServer;
	}
	
	public static PluginManager getPluginManager() {
		return pluginManager;
	}
}
