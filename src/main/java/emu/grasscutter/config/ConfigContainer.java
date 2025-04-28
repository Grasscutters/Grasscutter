package emu.grasscutter.config;

import ch.qos.logback.classic.Level;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.*;
import lombok.NoArgsConstructor;

import java.util.*;

import static emu.grasscutter.Grasscutter.*;

/**
 * *when your JVM fails*
 */
public class ConfigContainer {
    /*
     * Configuration changes:
     * Version  5 - 'questing' has been changed from a boolean
     *              to a container of options ('questOptions').
     *              This field will be removed in future versions.
     * Version  6 - 'questing' has been fully replaced with 'questOptions'.
     *              The field for 'legacyResources' has been removed.
     * Version  7 - 'regionKey' is being added for authentication
     *              with the new dispatch server.
     * Version  8 - 'server' is being added for enforcing handbook server
     *              addresses.
     * Version  9 - 'limits' was added for handbook requests.
     * Version 10 - 'trialCostumes' was added for enabling costumes
     *              on trial avatars.
     * Version 11 - 'server.fastRequire' was added for disabling the new
     *              Lua script require system if performance is a concern.
     * Version 12 - 'http.startImmediately' was added to control whether the
     *              HTTP server should start immediately.
     * Version 13 - 'game.useUniquePacketKey' was added to control whether the
     *              encryption key used for packets is a constant or randomly generated.
     */
    private static int version() {
        return 13;
    }

    /**
     * Attempts to update the server's existing configuration.
     */
    public static void updateConfig() {
        try { // Check if the server is using a legacy config.
            var configObject = JsonUtils.loadToClass(Grasscutter.configFile.toPath(), JsonObject.class);
            if (!configObject.has("version")) {
                Grasscutter.getLogger().info("Updating legacy config...");
                Grasscutter.saveConfig(null);
            }
        } catch (Exception ignored) { }

        var existing = config.version;
        var latest = version();

        if (existing == latest)
            return;

        // Create a new configuration instance.
        var updated = new ConfigContainer();
        // Update all configuration fields.
        var fields = ConfigContainer.class.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                field.set(updated, field.get(config));
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to update a configuration field.", exception);
            }
        }); updated.version = version();

        try { // Save configuration and reload.
            Grasscutter.saveConfig(updated);
            Grasscutter.loadConfig();
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to save the updated configuration.", exception);
        }
    }

    public final Structure folderStructure = new Structure();
    public final Database databaseInfo = new Database();
    public final Language language = new Language();
    public final Account account = new Account();
    public final Server server = new Server();

    // DO NOT. TOUCH. THE VERSION NUMBER.
    public int version = version();

    /* Option containers. */

    public static class Database {
        public final DataStore server = new DataStore();
        public final DataStore game = new DataStore();

        public static class DataStore {
            public final String connectionUri = "mongodb://localhost:27017";
            public final String collection = "grasscutter";
        }
    }

    public static class Structure {
        public final String resources = "./resources/";
        public final String data = "./data/";
        public final String packets = "./packets/";
        public final String scripts = "resources:Scripts/";
        public final String plugins = "./plugins/";
        public final String cache = "./cache/";

        // UNUSED (potentially added later?)
        // public String dumps = "./dumps/";
    }

    public static class Server {
        public final Set<Integer> debugWhitelist = Set.of();
        public final Set<Integer> debugBlacklist = Set.of();
        public final ServerRunMode runMode = ServerRunMode.HYBRID;
        public final boolean logCommands = false;

        /**
         * If enabled, the 'require' Lua function will load the script's compiled varient into the context. (faster; doesn't work as well)
         * If disabled, all 'require' calls will be replaced with the referenced script's source. (slower; works better)
         */
        public final boolean fastRequire = true;

        public final HTTP http = new HTTP();
        public final Game game = new Game();

        public final Dispatch dispatch = new Dispatch();
        public final DebugMode debugMode = new DebugMode();
    }

    public static class Language {
        public Locale language = Locale.getDefault();
        public final Locale fallback = Locale.US;
        public final String document = "EN";
    }

    public static class Account {
        public final boolean autoCreate = false;
        public final boolean EXPERIMENTAL_RealPassword = false;
        public final String[] defaultPermissions = {};
        public final String playerEmail = "grasscutter.io";
        public final int maxPlayer = -1;
    }

    /* Server options. */

    public static class HTTP {
        /* This starts the HTTP server before the game server. */
        public final boolean startImmediately = false;

        public final String bindAddress = "0.0.0.0";
        public final int bindPort = 443;

        /* This is the address used in URLs. */
        public final String accessAddress = "127.0.0.1";
        /* This is the port used in URLs. */
        public final int accessPort = 0;

        public final Encryption encryption = new Encryption();
        public final Policies policies = new Policies();
        public final Files files = new Files();
    }

    public static class Game {
        public final String bindAddress = "0.0.0.0";
        public final int bindPort = 22102;

        /* This is the address used in the default region. */
        public final String accessAddress = "127.0.0.1";
        /* This is the port used in the default region. */
        public final int accessPort = 0;

        /* Enabling this will generate a unique packet encryption key for each player. */
        public final boolean useUniquePacketKey = true;

        /* Entities within a certain range will be loaded for the player */
        public final int loadEntitiesForPlayerRange = 300;
        /* Start in 'unstable-quests', Lua scripts will be enabled by default. */
        public final boolean enableScriptInBigWorld = true;
        public boolean enableConsole = true;

        /* Kcp internal work interval (milliseconds) */
        public final int kcpInterval = 20;
        /* Controls whether packets should be logged in console or not */
        public ServerDebugMode logPackets = ServerDebugMode.NONE;
        /* Show packet payload in console or no (in any case the payload is shown in encrypted view) */
        public boolean isShowPacketPayload = false;
        /* Show annoying loop packets or no */
        public boolean isShowLoopPackets = false;

        public final boolean cacheSceneEntitiesEveryRun = false;

        public final GameOptions gameOptions = new GameOptions();
        public final JoinOptions joinOptions = new JoinOptions();
        public final ConsoleAccount serverAccount = new ConsoleAccount();

        public final VisionOptions[] visionOptions = new VisionOptions[] {
            new VisionOptions("VISION_LEVEL_NORMAL"         , 80    , 20),
            new VisionOptions("VISION_LEVEL_LITTLE_REMOTE"  , 16    , 40),
            new VisionOptions("VISION_LEVEL_REMOTE"         , 1000  , 250),
            new VisionOptions("VISION_LEVEL_SUPER"          , 4000  , 1000),
            new VisionOptions("VISION_LEVEL_NEARBY"         , 40    , 20),
            new VisionOptions("VISION_LEVEL_SUPER_NEARBY"   , 20    , 20)
        };
    }

    /* Data containers. */

    public static class Dispatch {
        /* An array of servers. */
        public final List<Region> regions = List.of();

        /* The URL used to make HTTP requests to the dispatch server. */
        public final String dispatchUrl = "ws://127.0.0.1:1111";
        /* A unique key used for encryption. */
        public final byte[] encryptionKey = Crypto.createSessionKey(32);
        /* A unique key used for authentication. */
        public final String dispatchKey = Utils.base64Encode(
            Crypto.createSessionKey(32));

        public final String defaultName = "Grasscutter";

        /* Controls whether http requests should be logged in console or not */
        public ServerDebugMode logRequests = ServerDebugMode.NONE;
    }

    /* Debug options container, used when jar launch argument is -debug | -debugall and override default values
     *  (see StartupArguments.enableDebug) */
    public static class DebugMode {
        /* Log level of the main server code (works only with -debug arg) */
        public final Level serverLoggerLevel = Level.DEBUG;

        /* Log level of the third-party services (works only with -debug arg):
           javalin, quartz, reflections, jetty, mongodb.driver */
        public final Level servicesLoggersLevel = Level.INFO;

        /* Controls whether packets should be logged in console or not */
        public final ServerDebugMode logPackets = ServerDebugMode.ALL;

        /* Show packet payload in console or no (in any case the payload is shown in encrypted view) */
        public final boolean isShowPacketPayload = false;

        /* Show annoying loop packets or no */
        public final boolean isShowLoopPackets = false;

        /* Controls whether http requests should be logged in console or not */
        public final ServerDebugMode logRequests = ServerDebugMode.ALL;
    }

    public static class Encryption {
        public boolean useEncryption = true;
        /* Should 'https' be appended to URLs? */
        public boolean useInRouting = true;
        public final String keystore = "./keystore.p12";
        public final String keystorePassword = "123456";
    }

    public static class Policies {
        public final Policies.CORS cors = new Policies.CORS();

        public static class CORS {
            public final boolean enabled = true;
            public final String[] allowedOrigins = new String[]{"*"};
        }
    }

    public static class GameOptions {
        public final InventoryLimits inventoryLimits = new InventoryLimits();
        public final AvatarLimits avatarLimits = new AvatarLimits();
        public final int sceneEntityLimit = 1000; // Unenforced. TODO: Implement.

        public final boolean watchGachaConfig = false;
        public final boolean enableShopItems = true;
        public final boolean staminaUsage = true;
        public final boolean energyUsage = true;
        public final boolean fishhookTeleport = true;
        public final boolean trialCostumes = false;

        @SerializedName(value = "questing", alternate = "questOptions")
        public final Questing questing = new Questing();
        public final ResinOptions resinOptions = new ResinOptions();
        public final Rates rates = new Rates();

        public final HandbookOptions handbook = new HandbookOptions();

        public static class InventoryLimits {
            public final int weapons = 2000;
            public final int relics = 2000;
            public final int materials = 2000;
            public final int furniture = 2000;
            public final int all = 30000;
        }

        public static class AvatarLimits {
            public final int singlePlayerTeam = 4;
            public final int multiplayerTeam = 4;
        }

        public static class Rates {
            public final float adventureExp = 1.0f;
            public float mora = 1.0f;
            public float leyLines = 1.0f;
        }

        public static class ResinOptions {
            public final boolean resinUsage = false;
            public final int cap = 160;
            public final int rechargeTime = 480;
        }

        public static class Questing {
            /* Should questing behavior be used? */
            public final boolean enabled = true;
        }

        public static class HandbookOptions {
            public final boolean enable = false;
            public final boolean allowCommands = true;

            public final Limits limits = new Limits();
            public final Server server = new Server();

            public static class Limits {
                /* Are rate limits checked? */
                public final boolean enabled = false;
                /* The time for limits to expire. */
                public final int interval = 3;

                /* The maximum amount of normal requests. */
                public final int maxRequests = 10;
                /* The maximum amount of entities to be spawned in one request. */
                public final int maxEntities = 25;
            }

            public static class Server {
                /* Are the server settings sent to the handbook? */
                public final boolean enforced = false;
                /* The default server address for the handbook's authentication. */
                public final String address = "127.0.0.1";
                /* The default server port for the handbook's authentication. */
                public final int port = 443;
                /* Should the defaults be enforced? */
                public final boolean canChange = true;
            }
        }
    }

    public static class VisionOptions {
        public final String name;
        public final int visionRange;
        public final int gridWidth;

        public VisionOptions(String name, int visionRange, int gridWidth) {
            this.name = name;
            this.visionRange = visionRange;
            this.gridWidth = gridWidth;
        }
    }

    public static class JoinOptions {
        public final int[] welcomeEmotes = {2007, 1002, 4010};
        public final String welcomeMessage = "Welcome to a Grasscutter server.";
        public final JoinOptions.Mail welcomeMail = new JoinOptions.Mail();

        public static class Mail {
            public final String title = "Welcome to Grasscutter!";
            public final String content = """
                    Hi there!\r
                    First of all, welcome to Grasscutter. If you have any issues, please let us know so that Lawnmower can help you! \r
                    \r
                    Check out our:\r
                    <type="browser" text="Discord" href="https://discord.gg/T5vZU6UyeG"/>
                    """;
            public final String sender = "Lawnmower";
            public final emu.grasscutter.game.mail.Mail.MailItem[] items = {
                new emu.grasscutter.game.mail.Mail.MailItem(13509, 1, 1),
                new emu.grasscutter.game.mail.Mail.MailItem(201, 99999, 1)
            };
        }
    }

    public static class ConsoleAccount {
        public final int avatarId = 10000007;
        public final int nameCardId = 210001;
        public final int adventureRank = 1;
        public final int worldLevel = 0;

        public final String nickName = "Server";
        public final String signature = "Welcome to Grasscutter!";
    }

    public static class Files {
        public final String indexFile = "./index.html";
        public final String errorFile = "./404.html";
    }

    /* Objects. */

    @NoArgsConstructor
    public static class Region {
        public String Name = "os_usa";
        public String Title = "Grasscutter";
        public String Ip = "127.0.0.1";
        public int Port = 22102;

        public Region(
            String name, String title,
            String address, int port
        ) {
            this.Name = name;
            this.Title = title;
            this.Ip = address;
            this.Port  = port;
        }
    }
}
