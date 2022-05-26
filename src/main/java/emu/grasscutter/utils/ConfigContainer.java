package emu.grasscutter.utils;

import java.io.File;
import java.util.Locale;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerDebugMode;
import emu.grasscutter.Grasscutter.ServerRunMode;

/**
 * *when your JVM fails*
 */
public class ConfigContainer {
    private static int version() {
        return 3;
    }

    /**
     * Attempts to update the server's existing configuration to the latest 
     */
    public static void updateConfig() {

        // DockerGC Stuuf
        try {
          ConfigContainer config = Grasscutter.getConfig();
          File vv = new File(config.folderStructure.VERSION);
		      if (vv.exists()) {
            version_DockerGC = new String(FileUtils.read(vv));
		      }
        } catch (Exception e) {
          //TODO: handle exception
        }

    }
    
    public Structure folderStructure = new Structure();
    public Database databaseInfo = new Database();
    public Language language = new Language();
    public Account account = new Account();
    public Server server = new Server();

    // DO NOT. TOUCH. THE VERSION NUMBER.
    public static int version = version();
    public static String version_DockerGC = "0.0.0";

    /* Option containers. */

    public static class Database {
        public DataStore server = new DataStore();
        public DataStore game = new DataStore();
        
        public static class DataStore {
            public String connectionUri = "mongodb://localhost:27017";
            public String collection = "grasscutter";
        }
    }

    public static class Structure {
        public String resources = "./resources/";
        public String data = "./data/";
        public String packets = "./packets/";
        public String keys = "./keys/";
        public String scripts = "./resources/Scripts/";
        public String plugins = "./plugins/";
        public String VERSION = "./VERSION";
        // UNUSED (potentially added later?)
        // public String dumps = "./dumps/";
    }

    public static class Server {
        public ServerDebugMode debugLevel = ServerDebugMode.NONE;
        public ServerRunMode runMode = ServerRunMode.HYBRID;

        public HTTP http = new HTTP();
        public Game game = new Game();
        
        public Dispatch dispatch = new Dispatch();
    }

    public static class Language {
        public Locale language = Locale.getDefault();
        public Locale fallback = Locale.US;
        public String document = "EN";
    }

    public static class Account {
        public boolean autoCreate = true;
        public String[] defaultPermissions = {
            "-server.restart",
            "-server.stop",
            "-server.permission",
            "-server.account",
            "-server.broadcast",
            "-server.coop",
            "-server.kick",
            "-server.reload",
            "-server.resetshop",
            "-server.sendmail",
            "-player.tpall",
            "-player.*.others",
            "-server.*.others",
            "*"
        };
        public int maxPlayer = 1000;
    }

    /* Server options. */
    
    public static class HTTP {
        public String bindAddress = "0.0.0.0";
        /* This is the address used in URLs. */
        public String accessAddress = "127.0.0.1";

        public int bindPort = 443;
        /* This is the port used in URLs. */
        public int accessPort = 0;
        
        public Encryption encryption = new Encryption();
        public Policies policies = new Policies();
        public Files files = new Files();
    }

    public static class Game {
        public String bindAddress = "0.0.0.0";
        /* This is the address used in the default region. */
        public String accessAddress = "127.0.0.1";

        public int bindPort = 22102;
        /* This is the port used in the default region. */
        public int accessPort = 0;
        public boolean enableConsole = true;
        public GameOptions gameOptions = new GameOptions();
        public JoinOptions joinOptions = new JoinOptions();
        public ConsoleAccount serverAccount = new ConsoleAccount();
    }

    /* Data containers. */

    public static class Dispatch {
        public Region[] regions = {};

        public String defaultName = "Grasscutter";
    }

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
            public boolean enabled = true;
            public String[] allowedOrigins = new String[]{"*"};
        }
    }

    public static class GameOptions {
        public InventoryLimits inventoryLimits = new InventoryLimits();
        public AvatarLimits avatarLimits = new AvatarLimits();
        public int worldEntityLimit = 1000; // Unenforced. TODO: Implement.

        public boolean watchGachaConfig = false;
        public boolean enableShopItems = true;
        public boolean staminaUsage = false;
        public boolean energyUsage = false;
        public Rates rates = new Rates();

        // DockerGC
        public int CMD_Give_WP = 10;
	    public int CMD_Spawn = 150;
		public int CMD_Give = 10000000;
		public int CMD_Drop = 100;
        public int CMD_DayLogin = 8;
		public boolean CMD_NoGiveTes = true;
        public boolean CMD_ListOnline = false;
		public boolean DropMo = true;
		public boolean DungeonMT = false;
        public boolean AbyssMT = false;

        public static class InventoryLimits {
            public int weapons = 2000;
            public int relics = 2000;
            public int materials = 2000;
            public int furniture = 2000;
            public int all = 8000;
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
            public String content = "Hi";
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
    
    public static class Files {
        public String indexFile = "./index.html";
        public String errorFile = "./404.html";
    }

    /* Objects. */

    public static class Region {
        public Region() { }
        
        public Region(
                String name, String title,
                String address, int port
        ) {
            this.Name = name;
            this.Title = title;
            this.Ip = address;
            this.Port  = port;
        }
        
        public String Name = "os_usa";
        public String Title = "Yuuki";
        public String Ip = "127.0.0.1";
        public int Port = 22102;
    }
}
