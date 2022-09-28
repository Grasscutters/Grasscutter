package emu.grasscutter.config;

import java.util.Locale;
import java.util.stream.Stream;

import emu.grasscutter.Grasscutter;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public static final String DOCUMENT_LANGUAGE = config.language.document;
    private static final String DATA_FOLDER = config.folderStructure.data;
    private static final String RESOURCES_FOLDER = config.folderStructure.resources;
    private static final String PLUGINS_FOLDER = config.folderStructure.plugins;
    private static final String SCRIPTS_FOLDER = config.folderStructure.scripts;
    private static final String PACKETS_FOLDER = config.folderStructure.packets;
    private static final FileSystem RESOURCES_FILE_SYSTEM;  // Not sure about lifetime rules on this one, might be safe to remove
    private static final Path RESOURCES_PATH;
    static {
        FileSystem fs = null;
        Path path = Path.of(RESOURCES_FOLDER);
        if (RESOURCES_FOLDER.endsWith(".zip")) {  // Would be nice to support .tar.gz too at some point, but it doesn't come for free in Java
            try {
                fs = FileSystems.newFileSystem(path);
            } catch (IOException e) {
                Grasscutter.getLogger().error("Failed to load resources zip \"" + RESOURCES_FOLDER + "\"");
            }
        }

        if (fs != null) {
            var root = fs.getPath("");
            try (Stream<Path> pathStream = java.nio.file.Files.find(root, 3, (p, a) -> {
                        var filename = p.getFileName();
                        if (filename == null) return false;
                        return filename.toString().equals("ExcelBinOutput");
            })) {
                var excelBinOutput = pathStream.findFirst();
                if (excelBinOutput.isPresent()) {
                    path = excelBinOutput.get().getParent();
                    if (path == null)
                        path = root;
                    Grasscutter.getLogger().debug("Resources will be loaded from \"" + RESOURCES_FOLDER + "/" + path.toString() + "\"");
                } else {
                    Grasscutter.getLogger().error("Failed to find ExcelBinOutput in resources zip \"" + RESOURCES_FOLDER + "\"");
                }
            } catch (IOException e) {
                Grasscutter.getLogger().error("Failed to scan resources zip \"" + RESOURCES_FOLDER + "\"");
            }
        }
        RESOURCES_FILE_SYSTEM = fs;
        RESOURCES_PATH = path;
    };

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
        return Path.of(DATA_FOLDER, path).toString();
    }

    public static Path getResourcePath(String path) {
        return RESOURCES_PATH.resolve(path);
    }

    public static String RESOURCE(String path) {
        return getResourcePath(path).toString();
    }

    public static String PLUGIN() {
        return PLUGINS_FOLDER;
    }

    public static String PLUGIN(String path) {
        return Path.of(PLUGINS_FOLDER, path).toString();
    }

    public static String SCRIPT(String path) {
        return Path.of(SCRIPTS_FOLDER, path).toString();
    }

    public static String PACKET(String path) {
        return Path.of(PACKETS_FOLDER, path).toString();
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
