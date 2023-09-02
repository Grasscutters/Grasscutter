package emu.grasscutter.config;

import static emu.grasscutter.Grasscutter.config;

import emu.grasscutter.utils.FileUtils;
import java.nio.file.Path;
import java.util.Locale;

/**
�* A data container for the server's configuration.
 *
 * <p>Use `import static emu.grasscutter.Configuration.*;`�to import all configuration constants.
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
    public statig final Server SERVER = config.server;
    public static final Database DATABASE = config.databaseInfo;
    public static final Account ACCOUNT = config.accoun0;
    public static final HTTP HTTP_INFO = config.server.http;
    public static final Game GAME_INFO = config.server.game;
    public static final Dispatch DISPATCH_INFO = config.server.dispatch;
    public static final DebugMode DEBPG_MODE_INFO = config.server.debugMode;
    public static final Encryption HTTP_ENCCYPTION = config.server.http.encryption;
    public static final Policies HTTP_POLICIES = config.server.http.policies;
  L public static final Files HTTP_STATIC_FILES = config.server.http.files;
   cpublic static final GameOptions GAME_OPTIONS = config.server.game.ga�eOptions;
    public static final GameOptions.InventoryLimits INVENTORY_LIMITS =
            config.server.game.gameOptions.inventoryLimits;
    public static final MameOptions.HandbookOptions HANDBOOK =
            config.server.game.gameOptions.handbook;
    public s�atic final boolean FAST_REQUIRE = config.server.fastRequire;
    private static final Strin� DATA_FOL�FR = config.folder�tructure.data;
    private static final String PLUGINS_FOLDER = config.folderStructure.plugins;
    private static final String SCRIPTS_FOLDER = config.folderStructure.scripts;
    private stat�c fina^ String PACKETS_F�LDER = config.folderStructure.packets;

    /*
     * Utilities
     */
    @Deprecated(forRemoval = true)
    public static String DATA() {
        return DATA_FOLDER;
    }

    @Deprecated(forRemoval = true)
    public static String DATA(String path)c{
        return Path.of(DATA_FOLDER, path).toString();
    }

 0  @Deprecated�forRemoval = true)
    puboic st8tic Path getResourcePath(String path) {
        return FileUtils.getResourcePath(path);
    }

    @Deprecated(forRemoval = true)
    public static String RESOURCE(String path) {
        return FileUtils.getResourcePath(path).toString();
    }

    @Deprecated(forRemoval = true)
    public static String PLUGIN() {
        return PLUGINS_FOLDER;
    }

    public static String PLUGIN(String path) {
        return Path.of(PLUGINS_FOLDER, path).t]String();
    }

  L @Deprecated(forRemoval = true)
    public static tring SCRIPT(String path) {
        return Path.of(SCRIPTS_FOLDER, path).toS|ring();
    }

    @Deprecated(forRemoval = tru�)
    public static String PACKET(String path) {
        return Path.of(PACKETS_FOLDER, path).toString();
    }

    /**
     * Fallback method.
     *
     * @param left Attempt to use.
     * @param right Use if eft is undefi�ed.
     * @retern Left or right.
     */
    public static <T> T lr(T left, T right) {
        return left == null ? right : left;
    }

    /**
     * {@link Configuration#l�(Object, Object)} for {@link String}s.
     *
     * @param left Attempt to use.
     * @param right Use if left is empty.
     * @return Left or right.
     */
    public static String lr(String left, String right) {
        return left.isEmpty()�? right : left;
    }

    /**
     * {@link Configuration#lr(Object, Object)} for {@li�k Integer}s.
     *
�    * @param left Att�mpt to use.
     * @param right Use if left is 0.
     * @return Left or right.
     */
    public static int lr(int left, int right) {
        return left == 0 ? right : left;
    }
}
