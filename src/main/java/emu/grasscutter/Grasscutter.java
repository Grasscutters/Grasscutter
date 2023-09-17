package emu.grasscutter;

import static emu.grasscutter.config.Configuration.SERVER;
import static emu.grasscutter.utils.lang.Language.translate;

import ch.qos.logback.classic.*;
import emu.grasscutter.auth.*;
import emu.grasscutter.command.*;
import emu.grasscutter.config.ConfigContainer;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.database.*;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.plugin.api.ServerHelper;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.HttpServer;
import emu.grasscutter.server.http.dispatch.*;
import emu.grasscutter.server.http.documentation.*;
import emu.grasscutter.server.http.handlers.*;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.*;
import emu.grasscutter.utils.lang.Language;
import io.netty.util.concurrent.FastThreadLocalThread;
import java.io.*;
import java.util.Calendar;
import java.util.concurrent.*;
import javax.annotation.Nullable;
import lombok.*;
import org.jline.reader.*;
import org.jline.terminal.*;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

public final class Grasscutter {
    public static final File configFile = new File("./config.json");
    public static final Reflections reflector = new Reflections("emu.grasscutter");
    @Getter private static final Logger logger = (Logger) LoggerFactory.getLogger(Grasscutter.class);

    @Getter public static ConfigContainer config;

    @Getter @Setter private static Language language;
    @Getter @Setter private static String preferredLanguage;

    @Getter private static int currentDayOfWeek;
    @Setter private static ServerRunMode runModeOverride = null; // Config override for run mode
    @Setter private static boolean noConsole = false;

    @Getter private static HttpServer httpServer;
    @Getter private static GameServer gameServer;
    @Getter private static DispatchServer dispatchServer;
    @Getter private static PluginManager pluginManager;
    @Getter private static CommandMap commandMap;

    @Getter @Setter private static AuthenticationSystem authenticationSystem;
    @Getter @Setter private static PermissionHandler permissionHandler;

    private static LineReader consoleLineReader = null;

    @Getter
    private static final ExecutorService threadPool =
            new ThreadPoolExecutor(
                    6,
                    6,
                    60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(),
                    FastThreadLocalThread::new,
                    new ThreadPoolExecutor.AbortPolicy());

    static {
        // Declare logback configuration.
        System.setProperty("logback.configurationFile", "src/main/resources/logback.xml");

        // Disable the MongoDB logger.
        var mongoLogger = (Logger) LoggerFactory.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);

        // Load server configuration.
        Grasscutter.loadConfig();
        // Attempt to update configuration.
        ConfigContainer.updateConfig();

        Grasscutter.getLogger().info("Loading Grasscutter...");

        // Load translation files.
        Grasscutter.loadLanguage();

        // Check server structure.
        Utils.startupCheck();
    }

    public static void main(String[] args) throws Exception {
        Crypto.loadKeys(); // Load keys from buffers.

        // Parse start-up arguments.
        if (StartupArguments.parse(args)) {
            System.exit(0); // Exit early.
        }

        // Get the server run mode.
        var runMode = Grasscutter.getRunMode();

        // Create command map.
        commandMap = new CommandMap(true);

        // Initialize server.
        logger.info(translate("messages.status.starting"));
        logger.info(translate("messages.status.game_version", GameConstants.VERSION));
        logger.info(translate("messages.status.version", BuildConfig.VERSION, BuildConfig.GIT_HASH));

        // Initialize database.
        DatabaseManager.initialize();

        // Initialize the default systems.
        authenticationSystem = new DefaultAuthentication();
        permissionHandler = new DefaultPermissionHandler();

        // Create server instances.
        if (runMode == ServerRunMode.HYBRID || runMode == ServerRunMode.GAME_ONLY)
            Grasscutter.gameServer = new GameServer();
        if (runMode == ServerRunMode.HYBRID || runMode == ServerRunMode.DISPATCH_ONLY)
            Grasscutter.httpServer = new HttpServer();

        // Create a server hook instance with both servers.
        new ServerHelper(gameServer, httpServer);

        // Create plugin manager instance.
        pluginManager = new PluginManager();

        if (runMode != ServerRunMode.GAME_ONLY) {
            // Add HTTP routes after loading plugins.
            httpServer.addRouter(HttpServer.UnhandledRequestRouter.class);
            httpServer.addRouter(HttpServer.DefaultRequestRouter.class);
            httpServer.addRouter(RegionHandler.class);
            httpServer.addRouter(LogHandler.class);
            httpServer.addRouter(GenericHandler.class);
            httpServer.addRouter(AnnouncementsHandler.class);
            httpServer.addRouter(AuthenticationHandler.class);
            httpServer.addRouter(GachaHandler.class);
            httpServer.addRouter(DocumentationServerHandler.class);
            httpServer.addRouter(HandbookHandler.class);
        }

        // Check if the HTTP server should start.
        var started = config.server.http.startImmediately;
        if (started) {
            Grasscutter.getLogger().info("HTTP server is starting...");
            Grasscutter.startDispatch();

            Grasscutter.getLogger().info("Game server is starting...");
        }

        // Load resources.
        if (runMode != ServerRunMode.DISPATCH_ONLY) {
            // Load all resources.
            Grasscutter.updateDayOfWeek();
            ResourceLoader.loadAll();

            // Generate handbooks.
            Tools.createGmHandbooks(false);
            // Generate gacha mappings.
            Tools.generateGachaMappings();
        }

        // Start servers.
        if (runMode == ServerRunMode.HYBRID) {
            if (!started) Grasscutter.startDispatch();
            gameServer.start();
        } else if (runMode == ServerRunMode.DISPATCH_ONLY) {
            if (!started) Grasscutter.startDispatch();
        } else if (runMode == ServerRunMode.GAME_ONLY) {
            gameServer.start();
        } else {
            logger.error(translate("messages.status.run_mode_error", runMode));
            logger.error(translate("messages.status.run_mode_help"));
            logger.error(translate("messages.status.shutdown"));
            System.exit(1);
        }

        // Enable all plugins.
        pluginManager.enablePlugins();

        // Hook into shutdown event.
        Runtime.getRuntime().addShutdownHook(new Thread(Grasscutter::onShutdown));

        // Open console.
        Grasscutter.startConsole();
    }

    /** Server shutdown event. */
    private static void onShutdown() {
        // Disable all plugins.
        if (pluginManager != null) pluginManager.disablePlugins();
        // Shutdown the game server.
        if (gameServer != null) gameServer.onServerShutdown();

        try {
            // Wait for Grasscutter's thread pool to finish.
            var executor = Grasscutter.getThreadPool();
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }

            // Wait for database operations to finish.
            var dbExecutor = DatabaseHelper.getEventExecutor();
            dbExecutor.shutdown();
            if (!dbExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                dbExecutor.shutdownNow();
            }
        } catch (InterruptedException ignored) {
        }
    }

    /** Utility method for starting the: - SDK server - Dispatch server */
    public static void startDispatch() throws Exception {
        httpServer.start(); // Start the SDK/HTTP server.

        if (Grasscutter.getRunMode() == ServerRunMode.DISPATCH_ONLY) {
            dispatchServer = new DispatchServer("0.0.0.0", 1111); // Create the dispatch server.
            dispatchServer.start(); // Start the dispatch server.
        }
    }

    /*
     * Methods for the language system component.
     */

    public static void loadLanguage() {
        var locale = config.language.language;
        language = Language.getLanguage(Utils.getLanguageCode(locale));
    }

    /*
     * Methods for the configuration system component.
     */

    /** Attempts to load the configuration from a file. */
    public static void loadConfig() {
        // Check if config.json exists. If not, we generate a new config.
        if (!configFile.exists()) {
            getLogger().info("config.json could not be found. Generating a default configuration ...");
            config = new ConfigContainer();
            Grasscutter.saveConfig(config);
            return;
        }

        // If the file already exists, we attempt to load it.
        try {
            config = JsonUtils.loadToClass(configFile.toPath(), ConfigContainer.class);
        } catch (Exception exception) {
            getLogger()
                    .error(
                            "There was an error while trying to load the configuration from config.json. Please make sure that there are no syntax errors. If you want to start with a default configuration, delete your existing config.json.");
            System.exit(1);
        }
    }

    /**
     * Saves the provided server configuration.
     *
     * @param config The configuration to save, or null for a new one.
     */
    public static void saveConfig(@Nullable ConfigContainer config) {
        if (config == null) config = new ConfigContainer();

        try (FileWriter file = new FileWriter(configFile)) {
            file.write(JsonUtils.encode(config));
        } catch (IOException ignored) {
            logger.error("Unable to write to config file.");
        } catch (Exception e) {
            logger.error("Unable to save config file.", e);
        }
    }

    /*
     * Getters for the various server components.
     */

    public static Language getLanguage(String langCode) {
        return Language.getLanguage(langCode);
    }

    public static ServerRunMode getRunMode() {
        return Grasscutter.runModeOverride != null ? Grasscutter.runModeOverride : SERVER.runMode;
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

            consoleLineReader = LineReaderBuilder.builder().terminal(terminal).build();
        }

        return consoleLineReader;
    }

    /*
     * Utility methods.
     */

    public static void updateDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        Grasscutter.currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        logger.debug("Set day of week to " + currentDayOfWeek);
    }

    public static void startConsole() {
        // Console should not start in dispatch only mode.
        if (Grasscutter.getRunMode() == ServerRunMode.DISPATCH_ONLY && Grasscutter.noConsole) {
            logger.info(translate("messages.dispatch.no_commands_error"));
            return;
        } else {
            logger.info(translate("messages.status.done"));
        }

        String input = null;
        var isLastInterrupted = false;
        while (config.server.game.enableConsole) {
            try {
                input = consoleLineReader.readLine("> ");
            } catch (UserInterruptException e) {
                if (!isLastInterrupted) {
                    isLastInterrupted = true;
                    logger.info("Press Ctrl-C again to shutdown.");
                    continue;
                } else {
                    Runtime.getRuntime().exit(0);
                }
            } catch (EndOfFileException e) {
                logger.info("EOF detected.");
                continue;
            } catch (IOError e) {
                logger.error("An IO error occurred while trying to read from console.", e);
                return;
            }

            isLastInterrupted = false;

            try {
                commandMap.invoke(null, null, input);
            } catch (Exception e) {
                logger.error(translate("messages.game.command_error"), e);
            }
        }
    }

    /*
     * Enums for the configuration.
     */

    public enum ServerRunMode {
        HYBRID,
        DISPATCH_ONLY,
        GAME_ONLY
    }

    public enum ServerDebugMode {
        ALL,
        MISSING,
        WHITELIST,
        BLACKLIST,
        NONE
    }
}
