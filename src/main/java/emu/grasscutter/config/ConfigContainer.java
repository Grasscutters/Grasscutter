package emu.grasscutter.config;

import ch.qos.logback.classic.Level;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static emu.grasscutter.Grasscutter.ServerDebugMode;
import static emu.grasscutter.Grasscutter.ServerRunMode;

/**
 * *when your JVM fails*
 */
public class ConfigContainer {
    /**
     * Retrieves the given key from the environment variables.
     * <p>
     * When the key is not set it will return the given default value.
     *
     * @param key          The name of the environment variable
     * @param defaultValue The default value when the key is not set
     * @return The value from the environment variable or the default value
     */
    static String getStringFromEnv(String key, String defaultValue) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        return currentValue;
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as integer.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param defaultValue The default value when the environment variable does not exists or is not a valid integer
     * @return The parsed integer or the default value
     */
    static int getIntFromEnv(String key, int defaultValue) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(currentValue, 10);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as float.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param defaultValue The default value when the environment variable does not exist or is not a valid float
     * @return The parsed float or the default value
     */
    static float getFloatFromEnv(String key, float defaultValue) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(currentValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as float.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param defaultValue The default value when the environment variable does not exists or is not a valid bool
     * @return The parsed boolean or the default value
     */
    static boolean getBoolFromEnv(String key, boolean defaultValue) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        return switch (currentValue.trim()) {
            case "true", "on", "1" -> true;
            case "false", "off", "0" -> false;
            default -> defaultValue;
        };
    }

    /**
     * Retrieves the given from the environment variables and tries to parse it as a Set<String>.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param defaultValue The default value when the environment variable does not exist or is not a valid set
     * @param separator    The separator which will be used for splitting up the string
     * @return The parsed set or the default value
     */
    static Set<String> getStringSetFromEnv(String key, Set<String> defaultValue, String separator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        var parts = currentValue.split(separator);

        return Set.of(parts);
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as a string array.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable
     * @param defaultValue The default value when the environment variable does not exist
     * @param separator    The separator which will be used for splitting up the environment variable
     * @return The parsed integer set or the default value
     */
    static Set<Integer> getIntSetFromEnv(String key, Set<Integer> defaultValue, String separator) {
        var defaultValues = defaultValue.stream().map(Object::toString).collect(Collectors.toSet());
        var currentValue = getStringSetFromEnv(key, defaultValues, separator);

        return currentValue.stream().map(entry -> Integer.parseInt(entry, 10)).collect(Collectors.toSet());
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as an enum member.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param enumClass    The enum class which contains all members
     * @param defaultValue The default value when the environment variable does not exists or is not a valid enum member
     * @param <T>          The type of the enum member
     * @return The parsed enum member or the default value
     */
    static <T extends Enum<T>> T getEnumFromEnv(String key, Class<T> enumClass, T defaultValue) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        try {
            return Enum.valueOf(enumClass, currentValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves the given key from the environment variables and tries to parse it as string array.
     * <p>
     * If the environment variable is not present or the parsing fails then the default value will be returned.
     *
     * @param key          The name of the environment variable to parse
     * @param defaultValue The default value when the environment variable does not exist
     * @param separator    The separator which will be used for splitting up the string
     * @return The parsed string array or the default value
     */
    static String[] getStringArrayFromEnv(String key, String[] defaultValue, String separator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        return currentValue.split(separator);
    }

    static int[] getIntArrayFromEnv(String key, int[] defaultValue, String separator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        return Arrays.stream(currentValue.split(separator)).mapToInt(Integer::parseInt).toArray();
    }

    static emu.grasscutter.game.mail.Mail.MailItem[] getMailItemsFromEnv(String key, emu.grasscutter.game.mail.Mail.MailItem[] defaultValue, String partsSeparator, String valuesSeparator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        var parts = Arrays.stream(currentValue.split(partsSeparator)).map(part -> part.split(valuesSeparator));

        return (emu.grasscutter.game.mail.Mail.MailItem[]) parts.filter(part -> part.length != 3).map(part -> {
            var itemId = Integer.parseInt(part[0], 10);
            var itemCount = Integer.parseInt(part[1], 10);
            var itemLevel = Integer.parseInt(part[2], 10);

            return new emu.grasscutter.game.mail.Mail.MailItem(itemId, itemCount, itemLevel);
        }).toArray();
    }

    static VisionOptions[] getVisionOptionsFromEnv(String key, VisionOptions[] defaultValue, String partsSeparator, String valuesSeparator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        var parts = currentValue.split(partsSeparator);
        return (VisionOptions[]) Arrays.stream(parts).map(part -> part.split(valuesSeparator)).filter(values -> values.length == 3).map(values -> {
            var name = values[0];
            var visionRange = Integer.parseInt(values[1]);
            var gridWidth = Integer.parseInt(values[2]);

            return new VisionOptions(name, visionRange, gridWidth);
        }).toArray();
    }

    static List<Region> getRegionsFromEnv(String key, List<Region> defaultValue, String partsSeparator, String valuesSeparator) {
        var currentValue = System.getenv(key);

        if (currentValue == null) {
            return defaultValue;
        }

        var parts = currentValue.split(partsSeparator);
        return Arrays.stream(parts).map(part -> part.split(valuesSeparator)).filter(values -> values.length == 4).map(values -> {
            var name = values[0];
            var title = values[1];
            var address = values[2];
            var port = Integer.parseInt(values[3]);

            return new Region(name, title, address, port);
        }).collect(Collectors.toList());
    }

    public Structure folderStructure = new Structure();
    public Database databaseInfo = new Database();
    public Language language = new Language();
    public Account account = new Account();
    public Server server = new Server();

    /* Option containers. */

    public static class Database {
        public DataStore server = new DataStore("SERVER");
        public DataStore game = new DataStore("GAME");

        public static class DataStore {
            public String connectionUri;
            public String collection;

            public DataStore(String key) {
                this.connectionUri = getStringFromEnv("DATABASE_INFO_" + key + "_CONNECTION_URI", "mongodb://localhost:27017");
                this.collection = getStringFromEnv("DATABASE_INFO_" + key + "_COLLECTION", "grasscutter");
            }
        }
    }

    public static class Structure {
        public String resources = getStringFromEnv("FOLDER_STRUCTURE_RESOURCES", "./resources/");
        public String data = getStringFromEnv("FOLDER_STRUCTURE_DATA", "./data/");
        public String packets = getStringFromEnv("FOLDER_STRUCTURE_PACKETS", "./packets/");
        public String scripts = getStringFromEnv("FOLDER_STRUCTURE_SCRIPTS", "resources:Scripts/");
        public String plugins = getStringFromEnv("FOLDER_STRUCTURE_PLUGINS", "./plugins/");
        public String cache = getStringFromEnv("FOLDER_STRUCTURE_CACHE", "./cache/");

        // UNUSED (potentially added later?)
        // public String dumps = "./dumps/";
    }

    public static class Server {
        public Set<Integer> debugWhitelist = getIntSetFromEnv("SERVER_DEBUG_WHITELIST", Set.of(), ",");
        public Set<Integer> debugBlacklist = getIntSetFromEnv("SERVER_DEBUG_BLACKLIST", Set.of(), ",");
        public ServerRunMode runMode = getEnumFromEnv("SERVER_RUN_MODE", ServerRunMode.class, ServerRunMode.HYBRID);

        public boolean logCommands = getBoolFromEnv("SERVER_LOG_COMMANDS", false);

        /**
         * If enabled, the 'require' Lua function will load the script's compiled varient into the context. (faster; doesn't work as well)
         * If disabled, all 'require' calls will be replaced with the referenced script's source. (slower; works better)
         */
        public boolean fastRequire = getBoolFromEnv("SERVER_FAST_REQUIRE", true);

        public HTTP http = new HTTP();
        public Game game = new Game();

        public Dispatch dispatch = new Dispatch();
        public DebugMode debugMode = new DebugMode();
    }

    public static class Language {
        public Locale language = Locale.getDefault();
        public Locale fallback = Locale.US;
        public String document = getStringFromEnv("LANGUAGE_DOCUMENT", "EN");
    }

    public static class Account {
        public boolean autoCreate = getBoolFromEnv("ACCOUNT_AUTO_CREATE", false);
        public boolean EXPERIMENTAL_RealPassword = getBoolFromEnv("ACCOUNT_EXPERIMENTAL_REAL_PASSWORD", false);
        public String[] defaultPermissions = getStringArrayFromEnv("ACCOUNT_DEFAULT_PERMISSIONS", new String[]{}, ",");
        public int maxPlayer = getIntFromEnv("ACCOUNT_MAX_PLAYER", -1);
    }

    /* Server options. */

    public static class HTTP {
        /* This starts the HTTP server before the game server. */
        public boolean startImmediately = getBoolFromEnv("SERVER_HTTP_START_IMMEDIATELY", false);

        public String bindAddress = getStringFromEnv("SERVER_HTTP_BIND_ADDRESS", "0.0.0.0");
        public int bindPort = getIntFromEnv("SERVER_HTTP_BIND_PORT", 443);

        /* This is the address used in URLs. */
        public String accessAddress = getStringFromEnv("SERVER_HTTP_ACCESS_ADDRESS", "127.0.0.1");
        /* This is the port used in URLs. */
        public int accessPort = getIntFromEnv("SERVER_HTTP_ACCESS_PORT", 0);

        public Encryption encryption = new Encryption();
        public Policies policies = new Policies();
        public Files files = new Files();
    }

    public static class Game {
        public String bindAddress = getStringFromEnv("SERVER_GAME_BIND_ADDRESS", "0.0.0.0");
        public int bindPort = getIntFromEnv("SERVER_GAME_BIND_PORT", 22102);

        /* This is the address used in the default region. */
        public String accessAddress = getStringFromEnv("SERVER_GAME_ACCESS_ADDRESS", "127.0.0.1");
        /* This is the port used in the default region. */
        public int accessPort = getIntFromEnv("SERVER_GAME_ACCESS_PORT", 0);

        /* Enabling this will generate a unique packet encryption key for each player. */
        public boolean useUniquePacketKey = getBoolFromEnv("SERVER_GAME_USE_UNIQUE_PACKET_KEY", true);

        /* Entities within a certain range will be loaded for the player */
        public int loadEntitiesForPlayerRange = getIntFromEnv("SERVER_GAME_LOAD_ENTITIES_FOR_PLAYER_RANGE", 300);
        /* Start in 'unstable-quests', Lua scripts will be enabled by default. */
        public boolean enableScriptInBigWorld = getBoolFromEnv("SERVER_GAME_ENABLE_SCRIPT_IN_BIG_WORLD", true);
        public boolean enableConsole = getBoolFromEnv("SERVER_GAME_ENABLE_CONSOLE", true);

        /* Kcp internal work interval (milliseconds) */
        public int kcpInterval = getIntFromEnv("SERVER_GAME_KCP_INTERVAL", 20);
        /* Controls whether packets should be logged in console or not */
        public ServerDebugMode logPackets = getEnumFromEnv("SERVER_GAME_LOG_PACKETS", ServerDebugMode.class, ServerDebugMode.NONE);
        /* Show packet payload in console or no (in any case the payload is shown in encrypted view) */
        public boolean isShowPacketPayload = getBoolFromEnv("SERVER_GAME_IS_SHOW_PACKET_PAYLOAD", false);
        /* Show annoying loop packets or no */
        public boolean isShowLoopPackets = getBoolFromEnv("SERVER_GAME_IS_SHOW_LOOP_PACKETS", false);

        public boolean cacheSceneEntitiesEveryRun = getBoolFromEnv("SERVER_GAME_CACHE_SCENE_ENTITIES_EVERY_RUN", false);

        public GameOptions gameOptions = new GameOptions();
        public JoinOptions joinOptions = new JoinOptions();
        public ConsoleAccount serverAccount = new ConsoleAccount();

        public VisionOptions[] visionOptions = getVisionOptionsFromEnv("SERVER_GAME_VISION_OPTIONS", new VisionOptions[]{
            new VisionOptions("VISION_LEVEL_NORMAL", 80, 20),
            new VisionOptions("VISION_LEVEL_LITTLE_REMOTE", 16, 40),
            new VisionOptions("VISION_LEVEL_REMOTE", 1000, 250),
            new VisionOptions("VISION_LEVEL_SUPER", 4000, 1000),
            new VisionOptions("VISION_LEVEL_NEARBY", 40, 20),
            new VisionOptions("VISION_LEVEL_SUPER_NEARBY", 20, 20)
        }, "|", ",");
    }

    /* Data containers. */

    public static class Dispatch {
        /* An array of servers. */
        public List<Region> regions = getRegionsFromEnv("SERVER_DISPATCH_REGIONS", List.of(), "|", ",");

        /* The URL used to make HTTP requests to the dispatch server. */
        public String dispatchUrl = getStringFromEnv("SERVER_DISPATCH_DISPATCH_URL", "ws://127.0.0.1:1111");
        /* A unique key used for encryption. */
        public byte[] encryptionKey = Utils.base64Decode(getStringFromEnv("SERVER_DISPATCH_ENCRYPTION_KEY", Utils.base64Encode(Crypto.createSessionKey(32))));
        /* A unique key used for authentication. */
        public String dispatchKey = getStringFromEnv("SERVER_DISPATCH_DISPATCH_KEY", Utils.base64Encode(Crypto.createSessionKey(32)));

        public String defaultName = getStringFromEnv("SERVER_DISPATCH_DEFAULT_NAME", "Grasscutter");

        /* Controls whether http requests should be logged in console or not */
        public ServerDebugMode logRequests = getEnumFromEnv("SERVER_DISPATCH_SERVER_DEBUG_MODE", ServerDebugMode.class, ServerDebugMode.NONE);
    }

    /* Debug options container, used when jar launch argument is -debug | -debugall and override default values
     *  (see StartupArguments.enableDebug) */
    public static class DebugMode {
        /* Log level of the main server code (works only with -debug arg) */
        public Level serverLoggerLevel = Level.DEBUG;

        /* Log level of the third-party services (works only with -debug arg):
           javalin, quartz, reflections, jetty, mongodb.driver */
        public Level servicesLoggersLevel = Level.INFO;

        /* Controls whether packets should be logged in console or not */
        public ServerDebugMode logPackets = getEnumFromEnv("SERVER_DEBUG_MODE_LOG_PACKETS", ServerDebugMode.class, ServerDebugMode.ALL);

        /* Show packet payload in console or no (in any case the payload is shown in encrypted view) */
        public boolean isShowPacketPayload = getBoolFromEnv("SERVER_DEBUG_MODE_IS_SHOW_PACKET_PAYLOAD", false);

        /* Show annoying loop packets or no */
        public boolean isShowLoopPackets = getBoolFromEnv("SERVER_DEBUG_MODE_IS_SHOW_LOOP_PACKETS", false);

        /* Controls whether http requests should be logged in console or not */
        public ServerDebugMode logRequests = getEnumFromEnv("SERVER_DEBUG_MODE_LOG_REQUESTS", ServerDebugMode.class, ServerDebugMode.ALL);
    }

    public static class Encryption {
        public boolean useEncryption = getBoolFromEnv("SERVER_HTTP_ENCRYPTION_USE_ENCRYPTION", true);
        /* Should 'https' be appended to URLs? */
        public boolean useInRouting = getBoolFromEnv("SERVER_HTTP_ENCRYPTION_USE_IN_ROUTING", true);
        public String keystore = getStringFromEnv("SERVER_HTTP_ENCRYPTION_KEYSTORE", "./keystore.p12");
        public String keystorePassword = getStringFromEnv("SERVER_HTTP_ENCRYPTION_KEYSTORE_PASSWORD", "123456");
    }

    public static class Policies {
        public Policies.CORS cors = new Policies.CORS();

        public static class CORS {
            public boolean enabled = getBoolFromEnv("SERVER_HTTP_POLICIES_CORS_ENABLED", true);
            public String[] allowedOrigins = getStringArrayFromEnv("SERVER_HTTP_POLICIES_ALLOWED_ORIGINS", new String[]{"*"}, ",");
        }
    }

    public static class GameOptions {
        public InventoryLimits inventoryLimits = new InventoryLimits();
        public AvatarLimits avatarLimits = new AvatarLimits();
        public int sceneEntityLimit = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_SCENE_ENTITY_LIMIT", 1000); // Unenforced. TODO: Implement.

        public boolean watchGachaConfig = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_WATCH_GACHA_CONFIG", false);
        public boolean enableShopItems = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_ENABLE_SHOP_ITEMS", true);
        public boolean staminaUsage = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_STAMINA_USAGE", true);
        public boolean energyUsage = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_ENERGY_USAGE", true);
        public boolean fishhookTeleport = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_FISHHOOK_TELEPORT", true);
        public boolean trialCostumes = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_TRIAL_COSTUMES", false);

        @SerializedName(value = "questing", alternate = "questOptions")
        public Questing questing = new Questing();
        public ResinOptions resinOptions = new ResinOptions();
        public Rates rates = new Rates();

        public HandbookOptions handbook = new HandbookOptions();

        public static class InventoryLimits {
            public int weapons = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_WEAPONS", 2000);
            public int relics = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_RELICS", 2000);
            public int materials = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_MATERIALS", 2000);
            public int furniture = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_FURNITURE", 2000);
            public int all = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_ALL", 30000);
        }

        public static class AvatarLimits {
            public int singlePlayerTeam = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_AVATAR_LIMITS_SINGLE_PLAYER_TEAM", 4);
            public int multiplayerTeam = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_AVATAR_LIMITS_MULTIPLAYER_TEAM", 4);
        }

        public static class Rates {
            public float adventureExp = getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_ADVENTURE_EXP", 1.0f);
            public float mora = getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_MORA", 1.0f);
            public float leyLines = getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_LEY_LINES", 1.0f);
        }

        public static class ResinOptions {
            public boolean resinUsage = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_RESIN_USAGE", false);
            public int cap = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_CAP", 160);
            public int rechargeTime = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_RECHARGE_TIME", 480);
        }

        public static class Questing {
            /* Should questing behavior be used? */
            public boolean enabled = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_QUESTING_ENABLED", true);
        }

        public static class HandbookOptions {
            public boolean enable = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_ENABLE", false);
            public boolean allowCommands = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_ALLOW_COMMANDS", true);

            public Limits limits = new Limits();
            public Server server = new Server();

            public static class Limits {
                /* Are rate limits checked? */
                public boolean enabled = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_LIMITS_ENABLED", false);
                /* The time for limits to expire. */
                public int interval = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_LIMITS_INTERVAL", 3);

                /* The maximum amount of normal requests. */
                public int maxRequests = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_LIMITS_MAX_REQUESTS", 10);
                /* The maximum amount of entities to be spawned in one request. */
                public int maxEntities = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_OPTIONS_LIMITS_MAX_ENTITIES", 25);
            }

            public static class Server {
                /* Are the server settings sent to the handbook? */
                public boolean enforced = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_CONFIG_SERVER_ENFORCED", false);
                /* The default server address for the handbook's authentication. */
                public String address = getStringFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_CONFIG_SERVER_ADDRESS", "127.0.0.1");
                /* The default server port for the handbook's authentication. */
                public int port = getIntFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_CONFIG_SERVER_PORT", 443);
                /* Should the defaults be enforced? */
                public boolean canChange = getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_HANDBOOK_CONFIG_SERVER_CAN_CHANGE", true);
            }
        }
    }

    public static class VisionOptions {
        public String name;
        public int visionRange;
        public int gridWidth;

        public VisionOptions(String name, int visionRange, int gridWidth) {
            this.name = name;
            this.visionRange = visionRange;
            this.gridWidth = gridWidth;
        }
    }

    public static class JoinOptions {
        public int[] welcomeEmotes = getIntArrayFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_EMOTES", new int[]{2007, 1002, 4010}, ",");
        public String welcomeMessage = getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MESSAGE", "Welcome to a Grasscutter server.");
        public JoinOptions.Mail welcomeMail = new JoinOptions.Mail();

        public static class Mail {
            public String title = getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_TITLE", "Welcome to Grasscutter!");
            public String content = getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_CONTENT", """
                Hi there!\r
                First of all, welcome to Grasscutter. If you have any issues, please let us know so that Lawnmower can help you! \r
                \r
                Check out our:\r
                <type="browser" text="Discord" href="https://discord.gg/T5vZU6UyeG"/>
                """);
            public String sender = getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_SENDER", "Lawnmower");
            public emu.grasscutter.game.mail.Mail.MailItem[] items = getMailItemsFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_ITEMS", new emu.grasscutter.game.mail.Mail.MailItem[]{new emu.grasscutter.game.mail.Mail.MailItem(13509, 1, 1), new emu.grasscutter.game.mail.Mail.MailItem(201, 99999, 1)}, "|", ",");
        }
    }

    public static class ConsoleAccount {
        public int avatarId = getIntFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_AVATAR_ID", 10000007);
        public int nameCardId = getIntFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_NAME_CARD_ID", 210001);
        public int adventureRank = getIntFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_ADVENTURE_RANK", 1);
        public int worldLevel = getIntFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_WORLD_LEVEL", 0);

        public String nickName = getStringFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_NICK_NAME", "Server");
        public String signature = getStringFromEnv("SERVER_GAME_CONSOLE_ACCOUNT_SIGNATURE", "Welcome to Grasscutter!");
    }

    public static class Files {
        public String indexFile = getStringFromEnv("SERVER_HTTP_FILES_INDEX_FILE", "./index.html");
        public String errorFile = getStringFromEnv("SERVER_HTTP_FILES_ERROR_FILE", "./404.html");
    }

    /* Objects. */

    @NoArgsConstructor
    public static class Region {
        public String Name = "os_usa";
        public String Title = "Grasscutter";
        public String Ip = "127.0.0.1";
        public int Port = 22102;

        public Region(String name, String title, String address, int port) {
            this.Name = name;
            this.Title = title;
            this.Ip = address;
            this.Port = port;
        }
    }
}
