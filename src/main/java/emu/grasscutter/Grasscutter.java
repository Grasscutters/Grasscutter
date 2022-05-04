package emu.grasscutter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.net.InetSocketAddress;
import java.util.Calendar;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.plugin.api.ServerHook;
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
import emu.grasscutter.languages.CNLanguage;
import emu.grasscutter.languages.Language;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Crypto;

public final class Grasscutter {
	private static final Logger log = (Logger) LoggerFactory.getLogger(Grasscutter.class);
	private static Config config;
	private static LineReader consoleLineReader = null;
	private static Language language;
	private static CNLanguage cn_language;

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

		// Load Language
		Grasscutter.loadLanguage();
		
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
				case "-gachamap" -> {
					Tools.createGachaMapping(); return;
				}
			}
		}
		
		// Initialize server.
		Grasscutter.getLogger().info(language.Starting_Grasscutter);
		
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
		// Create a server hook instance with both servers.
		new ServerHook(gameServer, dispatchServer);
		
		// Start servers.
		if (getConfig().RunMode == ServerRunMode.HYBRID) {
			dispatchServer.start();
			gameServer.start();
		} else if (getConfig().RunMode == ServerRunMode.DISPATCH_ONLY) {
			dispatchServer.start();
		} else if (getConfig().RunMode == ServerRunMode.GAME_ONLY) {
			gameServer.start();
		} else {
			getLogger().error(language.Invalid_server_run_mode + " " + getConfig().RunMode);
			getLogger().error(language.Server_run_mode);
			getLogger().error(language.Shutting_down);
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

	public static void loadLanguage() {
		try (FileReader file = new FileReader(String.format("%s%s.json", getConfig().LANGUAGE_FOLDER, Grasscutter.config.Language))) {
			language = gson.fromJson(file, Language.class);
		} catch (Exception e) {
			Grasscutter.language = new Language();
			Grasscutter.cn_language = new CNLanguage();
			Grasscutter.config.Language = "en_us";
			saveConfig();

			try {
				File folder = new File("./languages");
				if (!folder.exists() && !folder.isDirectory()) {
					//noinspection ResultOfMethodCallIgnored
					folder.mkdirs();
				}
			} catch (Exception ee) {
				Grasscutter.getLogger().error("Unable to create language folder.");
			}
			try (FileWriter file = new FileWriter("./languages/en_us.json")) {
				file.write(gson.toJson(language));
			} catch (Exception ee) {
				Grasscutter.getLogger().error("Unable to create language file.");
			}
			try (FileWriter file = new FileWriter("./languages/zh_cn.json")) {
				file.write(gson.toJson(cn_language));
			} catch (Exception ee) {
				Grasscutter.getLogger().error("无法创建中文语言文件。");
			}
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
			getLogger().info(language.Dispatch_mode_not_support_command);
			return;
		}

		getLogger().info(language.Start_done);
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
				Grasscutter.getLogger().error(language.Command_error, e);
			}
		}
	}

	public static Config getConfig() {
		return config;
	}

	public static Language getLanguage() {
		return language;
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
