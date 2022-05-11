package emu.grasscutter.utils;

import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

import static emu.grasscutter.Grasscutter.config;

/**
 * *when your JVM fails*
 */
public class ConfigContainer {
    private static int version() {
        return 1;
    }

    /**
     * Attempts to update the server's existing configuration to the latest 
     */
    public static void updateConfig() {
        try { // Check if the server is using a legacy config.
            JsonObject configObject = Grasscutter.getGsonFactory()
                    .fromJson(new FileReader(Grasscutter.configFile), JsonObject.class);
            if(!configObject.has("version")) {
                Grasscutter.getLogger().info("Updating legacy ..");
                Grasscutter.saveConfig(null);
            }
        } catch (Exception ignored) { }

        var existing = config.version;
        var latest = version();

        if(existing == latest)
            return;

        // Create a new configuration instance.
        ConfigContainer updated = new ConfigContainer();
        // Update all configuration fields.
        Field[] fields = ConfigContainer.class.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                field.set(updated, field.get(config));
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to update a configuration field.", exception);
            }
        }); updated.version = version();

        try { // Save configuration & reload.
            Grasscutter.saveConfig(updated);
            Grasscutter.loadConfig();
        } catch (Exception exception) {
            Grasscutter.getLogger().warn("Failed to inject the updated ", exception);
        }
    }
    
    public Structure folderStructure = new Structure();
    public Database databaseInfo = new Database();
    public Language language = new Language();
    public Account account = new Account();
    public Server server = new Server();

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
        public String VERSION = "./VERSION";
        // UNUSED (potentially added later?)
        // public String dumps = "./dumps/";
    }

    public static class Server {
        public Grasscutter.ServerDebugMode debugLevel = Grasscutter.ServerDebugMode.NONE;
        public Grasscutter.ServerRunMode runMode = Grasscutter.ServerRunMode.HYBRID;

        public Dispatch dispatch = new Dispatch();
        public Game game = new Game();
    }

    public static class Language {
        public Locale language = Locale.getDefault();
        public Locale fallback = Locale.US;
    }

    public static class Account {
        public boolean autoCreate = true;
        public String[] defaultPermissions = {"server.spawn","server.drop","player.give","player.godmode","player.clearinv","player.setstats","player.heal","player.changescene","player.givechar","player.setworldlevel","server.killall","player.giveall","player.resetconstellation","player.giveart","player.setfetterlevel","player.enterdungeon","player.settalent","player.killcharacter","player.teleport","player.weather","player.tower"};
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

        public int bindPort = 22102;
        /* This is the port used in the default region. */
        public int accessPort = 22102;

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
        public Policies.CORS cors = new Policies.CORS();

        public static class CORS {
            public boolean enabled = false;
            public String[] allowedOrigins = new String[]{"*"};
        }
    }

    public static class GameOptions {
        public GameOptions.InventoryLimits inventoryLimits = new GameOptions.InventoryLimits();
        public GameOptions.AvatarLimits avatarLimits = new GameOptions.AvatarLimits();
        public int worldEntityLimit = 1000; // Unenforced. TODO: Implement.

        public boolean watchGachaConfig = false;
        public boolean enableShopItems = true;
        public boolean staminaUsage = true;
        public GameOptions.Rates rates = new GameOptions.Rates();

        public Database databaseInfo = new Database();

        // Limiting for Public Servers
	     	public int CMD_Spawn = 150;
		    public int CMD_Give = 10000000;
		    public int CMD_Drop = 100;
        public int CMD_DayLogin = 3;
		    public boolean CMD_NoGiveTes = true;
        public boolean CMD_ListOnline = false;
		    // Dangerous feature for public server, make your database go crazy!
		    public boolean DropMo = true;
		    public boolean DungeonMT = false;

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
        public String welcomeMessage = "Welcome to Yuuki server.";
        public JoinOptions.Mail welcomeMail = new JoinOptions.Mail();

        public static class Mail {
            public String title = "Welcome to Yuuki Server";
            public String content = "tes";
            public String sender = "Yuuki";
            public emu.grasscutter.game.mail.Mail.MailItem[] items = {
                    // Intertwined Fate
                    new emu.grasscutter.game.mail.Mail.MailItem(223, 1000),
                    // Acquaint Fate
                    new emu.grasscutter.game.mail.Mail.MailItem(224, 1000),
                    // Mora
                    new emu.grasscutter.game.mail.Mail.MailItem(202, 6000000),
                    // Primogem
                    new emu.grasscutter.game.mail.Mail.MailItem(201, 60000),
                    // Genesis Crystal
                    new emu.grasscutter.game.mail.Mail.MailItem(203, 10000),
                    // Realm Currency
                    new emu.grasscutter.game.mail.Mail.MailItem(204, 1000000),
                    // Dendro Sigil
                    new emu.grasscutter.game.mail.Mail.MailItem(303, 1000)
            };
        }
    }

    public static class ConsoleAccount {
        public int avatarId = 10000002;
        public int nameCardId = 210081;
        public int adventureRank = 60;
        public int worldLevel = 8;

        public String nickName = "Ayaka";
        public String signature = "Hello, have fun playing :)";
    }

    /* Objects. */

    public static class Region {
        public String Name = "os_usa";
        public String Title = "Yuuki";
        public String Ip = "127.0.0.1";
        public int Port = 22102;
    }
}
