package emu.grasscutter;

import java.io.*;
import java.util.Calendar;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.managers.StaminaManager.StaminaManager;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.plugin.api.ServerHook;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.utils.ConfigContainer;
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
import emu.grasscutter.utils.Language;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.BuildConfig;

import javax.annotation.Nullable;

import static emu.grasscutter.utils.Language.translate;
import static emu.grasscutter.Configuration.*;

public final class Grasscutter {
	private static final Logger log = (Logger) LoggerFactory.getLogger(Grasscutter.class);
	private static LineReader consoleLineReader = null;
	
	private static Language language;

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static final File configFile = new File("./config.json");

	private static int day; // Current day of week.

	private static DispatchServer dispatchServer;
	private static GameServer gameServer;
	private static PluginManager pluginManager;

	public static final Reflections reflector = new Reflections("emu.grasscutter");
	public static ConfigContainer config;
  
	static {
		// Declare logback configuration.
		System.setProperty("logback.configurationFile", "src/main/resources/logback.xml");

		// Load server configuration.
		Grasscutter.loadConfig();
		// Attempt to update configuration.
		ConfigContainer.updateConfig();

		// Load translation files.
		Grasscutter.loadLanguage();

		// Check server structure.
		Utils.startupCheck();
	}

  	public static void main(String[] args) throws Exception {
		Crypto.loadKeys(); // Load keys from buffers.
	
		// Parse arguments.
		boolean exitEarly = false;
		for (String arg : args) {
			switch (arg.toLowerCase()) {
				case "-handbook" -> {
					Tools.createGmHandbook(); exitEarly = true;
				}
				case "-gachamap" -> {
					Tools.createGachaMapping(DATA("gacha_mappings.js")); exitEarly = true;
				}
				case "-version" -> {
					System.out.println("Grasscutter version: " + BuildConfig.VERSION + "-" + BuildConfig.GIT_HASH); exitEarly = true;
				}
			}
		} 
		
		// Exit early if argument sets it.
		if(exitEarly) System.exit(0);
	
		// Initialize server.
		Grasscutter.getLogger().info(translate("messages.status.starting"));
	
		// Load all resources.
		Grasscutter.updateDayOfWeek();
		ResourceLoader.loadAll();
		ScriptLoader.init();
	
		// Initialize database.
		DatabaseManager.initialize();
	
		// Create server instances.
		dispatchServer = new DispatchServer();
		gameServer = new GameServer();
		// Create a server hook instance with both servers.
		new ServerHook(gameServer, dispatchServer);
		// Create plugin manager instance.
		pluginManager = new PluginManager();

		// TODO: find a better place?
		StaminaManager.initialize();
	
		// Start servers.
		var runMode = SERVER.runMode;
		if (runMode == ServerRunMode.HYBRID) {
			dispatchServer.start();
			gameServer.start();
		} else if (runMode == ServerRunMode.DISPATCH_ONLY) {
			dispatchServer.start();
		} else if (runMode == ServerRunMode.GAME_ONLY) {
			gameServer.start();
		} else {
			getLogger().error(translate("messages.status.run_mode_error", runMode));
			getLogger().error(translate("messages.status.run_mode_help"));
			getLogger().error(translate("messages.status.shutdown"));
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

	/**
	 * Attempts to load the configuration from a file.
	 */
	public static void loadConfig() {
		// Check if config.json exists. If not, we generate a new config.
		if (!configFile.exists()) {
			getLogger().info("config.json could not be found. Generating a default configuration ...");
			config = new ConfigContainer();
			Grasscutter.saveConfig(config);
			return;
		} 

		// If the file already exists, we attempt to load it.
		try (FileReader file = new FileReader(configFile)) {
			config = gson.fromJson(file, ConfigContainer.class);
		} 
		catch (Exception exception) {
			getLogger().error("There was an error while trying to load the configuration from config.json. Please make sure that there are no syntax errors. If you want to start with a default configuration, delete your existing config.json.");
			System.exit(1);
		} 
	}

	public static void loadLanguage() {
		var locale = config.language.language;
        language = Language.getLanguage(Utils.getLanguageCode(locale));
	}

	/**
	 * Saves the provided server configuration.
	 * @param config The configuration to save, or null for a new one.
	 */
	public static void saveConfig(@Nullable ConfigContainer config) {
		if(config == null) config = new ConfigContainer();
		
		try (FileWriter file = new FileWriter(configFile)) {
			file.write(gson.toJson(config));
		} catch (IOException ignored) {
			Grasscutter.getLogger().error("Unable to write to config file.");
		} catch (Exception e) {
			Grasscutter.getLogger().error("Unable to save config file.", e);
		}
	}

	public static void startConsole() {
		// Console should not start in dispatch only mode.
		if (SERVER.runMode == ServerRunMode.DISPATCH_ONLY) {
			getLogger().info(translate("messages.dispatch.no_commands_error"));
			return;
		}

		getLogger().info(translate("messages.status.done"));
		getLogger().info(translate("messages.status.version", BuildConfig.VERSION, BuildConfig.GIT_HASH));
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
				CommandMap.getInstance().invoke(null, null, input);
			} catch (Exception e) {
				Grasscutter.getLogger().error(translate("messages.game.command_error"), e);
			}
		}
	}

	public static ConfigContainer getConfig() {
		return config;
	}

	public static Language getLanguage() {
		return language;
	}

	public static void setLanguage(Language language) {
        Grasscutter.language = language;
	}

	public static Language getLanguage(String langCode) {
        return Language.getLanguage(langCode);
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
