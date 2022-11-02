package emu.grasscutter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import emu.grasscutter.auth.AuthenticationSystem;
import emu.grasscutter.auth.DefaultAuthentication;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.command.DefaultPermissionHandler;
import emu.grasscutter.command.PermissionHandler;
import emu.grasscutter.config.ConfigContainer;
import emu.grasscutter.data.ResourceLoader;
import emu.grasscutter.database.DatabaseManager;
import emu.grasscutter.plugin.PluginManager;
import emu.grasscutter.plugin.api.ServerHook;
import emu.grasscutter.scripts.ScriptLoader;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.HttpServer;
import emu.grasscutter.server.http.dispatch.DispatchHandler;
import emu.grasscutter.server.http.dispatch.RegionHandler;
import emu.grasscutter.server.http.documentation.DocumentationServerHandler;
import emu.grasscutter.server.http.handlers.AnnouncementsHandler;
import emu.grasscutter.server.http.handlers.GachaHandler;
import emu.grasscutter.server.http.handlers.GenericHandler;
import emu.grasscutter.server.http.handlers.LogHandler;
import emu.grasscutter.tools.Tools;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.JsonUtils;
import emu.grasscutter.utils.Language;
import emu.grasscutter.utils.StartupArguments;
import emu.grasscutter.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Calendar;

import static emu.grasscutter.config.Configuration.SERVER;
import static emu.grasscutter.utils.Language.translate;

public final class Grasscutter {
    @Getter private static final Logger logger = (Logger) LoggerFactory.getLogger(Grasscutter.class);
    private static LineReader consoleLineReader = null;

    @Getter @Setter private static Language language;

    public static final File configFile = new File("./config.json");
    @Setter private static ServerRunMode runModeOverride = null; // Config override for run mode

    @Getter private static int currentDayOfWeek;
    @Getter @Setter private static String preferredLanguage;

    @Getter private static HttpServer httpServer;
    @Getter private static GameServer gameServer;
    @Getter private static PluginManager pluginManager;
    @Getter private static CommandMap commandMap;

    @Getter @Setter private static AuthenticationSystem authenticationSystem;
    @Getter @Setter private static PermissionHandler permissionHandler;

    public static final Reflections reflector = new Reflections("emu.grasscutter");
    @Getter public static ConfigContainer config;

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

        // Create command map.
        commandMap = new CommandMap(true);

        // Initialize server.
        Grasscutter.getLogger().info(translate("messages.status.starting"));
        Grasscutter.getLogger().info(translate("messages.status.game_version", GameConstants.VERSION));
        Grasscutter.getLogger().info(translate("messages.status.version", BuildConfig.VERSION, BuildConfig.GIT_HASH));

        // Load all resources.
        Grasscutter.updateDayOfWeek();
        ResourceLoader.loadAll();
        ScriptLoader.init();

        // Generate handbooks.
        Tools.createGmHandbooks();

        // Initialize database.
        DatabaseManager.initialize();

        // Initialize the default systems.
        authenticationSystem = new DefaultAuthentication();
        permissionHandler = new DefaultPermissionHandler();

        // Create server instances.
        httpServer = new HttpServer();
        gameServer = new GameServer();
        // Create a server hook instance with both servers.
        new ServerHook(gameServer, httpServer);

        // Create plugin manager instance.
        pluginManager = new PluginManager();
        // Add HTTP routes after loading plugins.
        httpServer.addRouter(HttpServer.UnhandledRequestRouter.class);
        httpServer.addRouter(HttpServer.DefaultRequestRouter.class);
        httpServer.addRouter(RegionHandler.class);
        httpServer.addRouter(LogHandler.class);
        httpServer.addRouter(GenericHandler.class);
        httpServer.addRouter(AnnouncementsHandler.class);
        httpServer.addRouter(DispatchHandler.class);
        httpServer.addRouter(GachaHandler.class);
        httpServer.addRouter(DocumentationServerHandler.class);

        // Start servers.
        var runMode = Grasscutter.getRunMode();
        if (runMode == ServerRunMode.HYBRID) {
            httpServer.start();
            gameServer.start();
        } else if (runMode == ServerRunMode.DISPATCH_ONLY) {
            httpServer.start();
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
        if (pluginManager != null)
            pluginManager.disablePlugins();
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
        try {
            config = JsonUtils.loadToClass(configFile.toPath(), ConfigContainer.class);
        } catch (Exception exception) {
            getLogger().error("There was an error while trying to load the configuration from config.json. Please make sure that there are no syntax errors. If you want to start with a default configuration, delete your existing config.json.");
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
            Grasscutter.getLogger().error("Unable to write to config file.");
        } catch (Exception e) {
            Grasscutter.getLogger().error("Unable to save config file.", e);
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
            consoleLineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
        }
        return consoleLineReader;
    }

    /*
     * Utility methods.
     */

    public static void updateDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        Grasscutter.currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Grasscutter.getLogger().debug("Set day of week to "+currentDayOfWeek);
    }

    public static void startConsole() {
        // Console should not start in dispatch only mode.
        if (SERVER.runMode == ServerRunMode.DISPATCH_ONLY) {
            getLogger().info(translate("messages.dispatch.no_commands_error"));
            return;
        }

        getLogger().info(translate("messages.status.done"));
        String input = null;
        boolean isLastInterrupted = false;
        while (config.server.game.enableConsole) {
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

    /*
     * Enums for the configuration.
     */

    public enum ServerRunMode {
        HYBRID, DISPATCH_ONLY, GAME_ONLY
    }

    public enum ServerDebugMode {
        ALL, MISSING, WHITELIST, BLACKLIST, NONE
    }
}
