package emu.grasscutter;

import emu.grasscutter.utils.ConfigContainer;

import java.util.Locale;

import static emu.grasscutter.Grasscutter.config;

/**
 * A data container for the server's configuration.
 * 
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
    public static final String DATA_FOLDER = config.folderStructure.data;
    public static final String RESOURCES_FOLDER = config.folderStructure.resources;
    public static final String KEYS_FOLDER = config.folderStructure.keys;
    public static final String PLUGINS_FOLDER = config.folderStructure.plugins;
    public static final String SCRIPTS_FOLDER = config.folderStructure.scripts;
    public static final String PACKETS_FOLDER = config.folderStructure.packets;
    
    public static final Server SERVER = config.server;
    public static final Database DATABASE = config.databaseInfo;
    public static final Account ACCOUNT = config.account;
    
    public static final Dispatch DISPATCH_INFO = config.server.dispatch;
    public static final Game GAME_INFO = config.server.game;
    
    public static final Encryption DISPATCH_ENCRYPTION = config.server.dispatch.encryption;
    public static final Policies DISPATCH_POLICIES = config.server.dispatch.policies;
    
    public static final GameOptions GAME_OPTIONS = config.server.game.gameOptions;
    public static final GameOptions.InventoryLimits INVENTORY_LIMITS = config.server.game.gameOptions.inventoryLimits;
    
    /*
     * Utilities
     */
    
    public static String DATA(String path) {
        return DATA_FOLDER + "/" + path;
    }
    
    public static String RESOURCE(String path) {
        return RESOURCES_FOLDER + "/" + path;
    }
    
    public static String SCRIPT(String path) {
        return SCRIPTS_FOLDER + "/" + path;
    }

    /**
     * Fallback method.
     * @param left Attempt to use.
     * @param right Use if left is undefined.
     * @return Left or right.
     */
    public static <T> T lr(T left, T right) {
        return left == null ? right : left;
    }

    /**
     * {@link Configuration#lr(Object, Object)} for {@link String}s.
     * @param left Attempt to use.
     * @param right Use if left is empty.
     * @return Left or right.
     */
    public static String lr(String left, String right) {
        return left.isEmpty() ? right : left;
    }

    /**
     * {@link Configuration#lr(Object, Object)} for {@link Integer}s.
     * @param left Attempt to use.
     * @param right Use if left is 0.
     * @return Left or right.
     */
    public static int lr(int left, int right) {
        return left == 0 ? right : left;
    }
}