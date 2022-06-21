package emu.grasscutter;

import emu.grasscutter.utils.ConfigContainer;

import java.nio.file.Paths;
import java.util.Locale;

import static emu.grasscutter.Grasscutter.config;


/**
 * A data container for the server's configuration.
 * <p>
 * Use `import static emu.grasscutter.Configuration.*;`
 * to import all configuration constants.
 */
public final class Configuration extends ConfigContainer {

    /*
     * Constants
     */

    // 'c' is short for 'config' and makes code look 'cleaner'.
    public static final ConfigContainer c = config;

    public static final Locale LANGUAGE = config.language.language;
    public static final Locale FALLBACK_LANGUAGE = config.language.fallback;
    public static final String DOCUMENT_LANGUAGE = config.language.document;
    private static final String DATA_FOLDER = config.folderStructure.data;
    private static final String RESOURCES_FOLDER = config.folderStructure.resources;
    private static final String PLUGINS_FOLDER = config.folderStructure.plugins;
    private static final String SCRIPTS_FOLDER = config.folderStructure.scripts;
    private static final String PACKETS_FOLDER = config.folderStructure.packets;

    public static final Server SERVER = config.server;
    public static final Database DATABASE = config.databaseInfo;
    public static final Account ACCOUNT = config.account;

    public static final HTTP HTTP_INFO = config.server.http;
    public static final Game GAME_INFO = config.server.game;
    public static final Dispatch DISPATCH_INFO = config.server.dispatch;

    public static final Encryption HTTP_ENCRYPTION = config.server.http.encryption;
    public static final Policies HTTP_POLICIES = config.server.http.policies;
    public static final Files HTTP_STATIC_FILES = config.server.http.files;

    public static final GameOptions GAME_OPTIONS = config.server.game.gameOptions;
    public static final GameOptions.InventoryLimits INVENTORY_LIMITS = config.server.game.gameOptions.inventoryLimits;

    /*
     * Utilities
     */
    public static String DATA() {
        return DATA_FOLDER;
    }

    public static String DATA(String path) {
        return Paths.get(DATA_FOLDER, path).toString();
    }

    public static String RESOURCE(String path) {
        return Paths.get(RESOURCES_FOLDER, path).toString();
    }

    public static String PLUGIN() {
        return PLUGINS_FOLDER;
    }

    public static String PLUGIN(String path) {
        return Paths.get(PLUGINS_FOLDER, path).toString();
    }

    public static String SCRIPT(String path) {
        return Paths.get(SCRIPTS_FOLDER, path).toString();
    }

    public static String PACKET(String path) {
        return Paths.get(PACKETS_FOLDER, path).toString();
    }

    /**
     * Fallback method.
     *
     * @param left  Attempt to use.
     * @param right Use if left is undefined.
     * @return Left or right.
     */
    public static <T> T lr(T left, T right) {
        return left == null ? right : left;
    }

    /**
     * {@link Configuration#lr(Object, Object)} for {@link String}s.
     *
     * @param left  Attempt to use.
     * @param right Use if left is empty.
     * @return Left or right.
     */
    public static String lr(String left, String right) {
        return left.isEmpty() ? right : left;
    }

    /**
     * {@link Configuration#lr(Object, Object)} for {@link Integer}s.
     *
     * @param left  Attempt to use.
     * @param right Use if left is 0.
     * @return Left or right.
     */
    public static int lr(int left, int right) {
        return left == 0 ? right : left;
    }
}
