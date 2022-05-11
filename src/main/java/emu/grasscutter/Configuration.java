package emu.grasscutter;

import emu.grasscutter.Grasscutter.*;
import emu.grasscutter.game.mail.Mail.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

import static emu.grasscutter.Grasscutter.config;

/**
 * A data container for the server's configuration.
 * 
 * Use `import static emu.grasscutter.Configuration.*;`
 * to import all configuration constants.
 */
public final class Configuration {
    private static int version() {
        return 1;
    }
    
    /**
     * Attempts to update the server's existing configuration to the latest configuration.
     */
    public static void updateConfig() {
        var existing = config.version; 
        var latest = version();
        
        if(existing == latest) 
            return;
        
        // Create a new configuration instance.
        Configuration updated = new Configuration();
        // Update all configuration fields.
        Field[] fields = Configuration.class.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                field.set(updated, field.get(config));
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to update a configuration field.", exception);
            }
        });
        
        try { // Save configuration & reload.
            Grasscutter.saveConfig(updated);
            Grasscutter.reloadConfig();
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to inject the updated configuration.", exception);
        }
    }
    
    /*
     * Constants
     */
    
    // 'c' is short for 'config' and makes code look 'cleaner'.
    public static final Configuration c = config;
    
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
    
    /*
     * Configuration data.
     */
    
    public Structure folderStructure;
    public Database databaseInfo;
    public Language language;
    public Account account;
    public Server server;
    
    // DO NOT. TOUCH. THE VERSION NUMBER.
    public int version = version();
    
    /* Option containers. */
    
    public static class Database {
        public String connectionUri = "mongodb://localhost:27017";
        public String collection = "grasscutter";
    }
    
    public static class Structure {
        public String resources = "./resources/";
        public String data = "./data/";
        public String packets = "./packets/";
        public String keys = "./keys/";
        public String scripts = "./resources/scripts/";
        public String plugins = "./plugins/";
        
        // UNUSED (potentially added later?)
        // public String dumps = "./dumps/";
    }
    
    public static class Server {
        public ServerDebugMode debugLevel = ServerDebugMode.NONE;
        public ServerRunMode runMode = ServerRunMode.HYBRID;
        
        public Dispatch dispatch = new Dispatch();
        public Game game = new Game();
    }
    
    public static class Language {
        public Locale language = Locale.getDefault();
        public Locale fallback = Locale.US;
    }
    
    public static class Account {
        public boolean autoCreate = false;
        public String[] defaultPermissions = {};
    }

    /* Server options. */

    public static class Dispatch {
        public String bindAddress = "0.0.0.0";
        /* This is the address used in URLs. */
        public String accessAddress = "127.0.0.1";

        public int bindPort = 443;
        /* This is the port used in URLs. */
        public int accessPort = 443;
        
        public Encryption encryption = new Encryption();
        public Policies policies = new Policies();
        public Region[] regions = {};
        
        public String defaultName = "Grasscutter";
    }
    
    public static class Game {
        public String bindAddress = "0.0.0.0";
        /* This is the address used in the default region. */
        public String accessAddress = "127.0.0.1";

        public int bindPort = 443;
        /* This is the port used in the default region. */
        public int accessPort = 443;
        
        public GameOptions gameOptions = new GameOptions();
        public JoinOptions joinOptions = new JoinOptions();
        public ConsoleAccount serverAccount = new ConsoleAccount();
    }
    
    /* Data containers. */
    
    public static class Encryption {
        public boolean useEncryption = true;
        /* Should 'https' be appended to URLs? */
        public boolean useInRouting = true;
        public String keystore = "./keystore.p12";
        public String keystorePassword = "123456";
    }
    
    public static class Policies {
        public CORS cors = new CORS();
        
        public static class CORS {
            public boolean enabled = false;
            public String[] allowedOrigins = new String[]{"*"};
        }
    }
    
    public static class GameOptions {
        public InventoryLimits inventoryLimits = new InventoryLimits();
        public AvatarLimits avatarLimits = new AvatarLimits();
        public int worldEntityLimit = 1000; // Unenforced. TODO: Implement.
        
        public boolean watchGachaConfig = false;
        public boolean enableShopItems = true;
        public boolean staminaUsage = true;
        public Rates rates = new Rates();
        
        public Database databaseInfo = new Database();
        
        public static class InventoryLimits {
            public int weapons = 2000;
            public int relics = 2000;
            public int materials = 2000;
            public int furniture = 2000;
            public int all = 30000;
        }
        
        public static class AvatarLimits {
            public int singlePlayerTeam = 4;
            public int multiplayerTeam = 4;
        }
        
        public static class Rates {
            public float adventureExp = 1.0f;
            public float mora = 1.0f;
            public float leyLines = 1.0f;
        }
    }

    public static class JoinOptions {
        public int[] welcomeEmotes = {2007, 1002, 4010};
        public String welcomeMessage = "Welcome to a Grasscutter server.";
        public Mail welcomeMail = new Mail();
        
        public static class Mail {
            public String title = "Welcome to Grasscutter!";
            public String content = """
                    Hi there!\r
                    First of all, welcome to Grasscutter. If you have any issues, please let us know so that Lawnmower can help you! \r
                    \r
                    Check out our:\r
                    <type="browser" text="Discord" href="https://discord.gg/T5vZU6UyeG"/>
                    """;
            public String sender = "Lawnmower";
            public MailItem[] items = {
                    new MailItem(13509, 1, 1),
                    new MailItem(201, 99999, 1)
            };
        }
    }
    
    public static class ConsoleAccount {
        public int avatarId = 10000007;
        public int nameCardId = 210001;
        public int adventureRank = 1;
        public int worldLevel = 0;
        
        public String nickName = "Server";
        public String signature = "Welcome to Grasscutter!";
    }

    /* Objects. */
    
    public static class Region {
        public String Name = "os_usa";
        public String Title = "Grasscutter";
        public String Ip = "127.0.0.1";
        public int Port = 22102;
    }
}