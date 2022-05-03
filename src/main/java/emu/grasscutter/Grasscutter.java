package emu.grasscutter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.net.InetSocketAddress;
import java.util.Calendar;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.Utils;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
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
	private static LineReader consoleLineReader = null;

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final File configFile = new File("./config.json");
	
	private static int day; // Current day of week
	
	private static DispatchServer dispatchServer;
	private static GameServer gameServer;
	private static PluginManager pluginManager;
	
	public static final Reflections reflector = new Reflections("emu.grasscutter");
	
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
				case "-handbook" -> {
					Tools.createGmHandbook(); return;
				}
			}
		}
		
		// Initialize server.
		Grasscutter.getLogger().info("Starting Grasscutter...");
		
		// Load all resources.
		Grasscutter.updateDayOfWeek();
		ResourceLoader.loadAll();
		ScriptLoader.init();
		
		// Database
		DatabaseManager.initialize();

		// Create plugin manager instance.
		pluginManager = new PluginManager();
		
		// Create server instances.
		dispatchServer = new DispatchServer();
		gameServer = new GameServer(new InetSocketAddress(getConfig().getGameServerOptions().Ip, getConfig().getGameServerOptions().Port));
		
		// Start servers.
		if (getConfig().RunMode == ServerRunMode.HYBRID) {
			dispatchServer.start();
			gameServer.start();
		} else if (getConfig().RunMode == ServerRunMode.DISPATCH_ONLY) {
			dispatchServer.start();
		} else if (getConfig().RunMode == ServerRunMode.GAME_ONLY) {
			gameServer.start();
		} else {
			getLogger().error("Invalid server run mode. " + getConfig().RunMode);
			getLogger().error("Server run mode must be 'HYBRID', 'DISPATCH_ONLY', or 'GAME_ONLY'. Unable to start Grasscutter...");
			getLogger().error("Shutting down...");
			System.exit(1);
		}
		
		// Enable all plugins.
		pluginManager.enablePlugins();

		// Hook into shutdown event.
		Runtime.getRuntime().addShutdownHook(new Thread(Grasscutter::onShutdown));

		// Open console.
		startConsole();
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
		// Console should not start in dispatch only mode.
		if (getConfig().RunMode == ServerRunMode.DISPATCH_ONLY) {
			getLogger().info("Commands are not supported in dispatch only mode.");
			return;
		}

		getLogger().info("Done! For help, type \"help\"");
		String input = null;
		boolean isLastInterrupted = false;
		while (true) {
			try {
				input = consoleLineReader.readLine("> ");
			} catch (UserInterruptException e) {
				if (!isLastInterrupted) {
					isLastInterrupted = true;
					Grasscutter.getLogger().info("Press Ctrl-C again to shutdown.");
					continue;
				} else {
					Runtime.getRuntime().exit(0);
				}
			} catch (EndOfFileException e) {
				Grasscutter.getLogger().info("EOF detected.");
				continue;
			} catch (IOError e) {
				Grasscutter.getLogger().error("An IO error occurred.", e);
				continue;
			}

			isLastInterrupted = false;
			try {
				CommandMap.getInstance().invoke(null, input);
			} catch (Exception e) {
				Grasscutter.getLogger().error("Command error:", e);
			}
		}
	}

	public static Config getConfig() {
		return config;
	}

	public static Logger getLogger() {
		return log;
	}

	public static LineReader getConsole() {
		if (consoleLineReader == null) {
			Terminal terminal = null;
			try {
				terminal = TerminalBuilder.builder().jna(true).build();
			} catch (Exception e) {
				try {
					// Fallback to a dumb jline terminal.
					terminal = TerminalBuilder.builder().dumb(true).build();
				} catch (Exception ignored) {
					// When dumb is true, build() never throws.
				}
			}
			consoleLineReader = LineReaderBuilder.builder()
					.terminal(terminal)
					.build();
		}
		return consoleLineReader;
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
	
	public static void updateDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		day = calendar.get(Calendar.DAY_OF_WEEK); 
	}

	public static int getCurrentDayOfWeek() {
		return day;
	}
	
	public enum ServerRunMode {
		HYBRID, DISPATCH_ONLY, GAME_ONLY
	}
	
	public enum ServerDebugMode {
		ALL, MISSING, NONE
	}
}
