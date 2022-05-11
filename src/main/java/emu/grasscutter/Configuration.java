package emu.grasscutter;

import emu.grasscutter.Grasscutter.*;
import emu.grasscutter.game.mail.Mail.*;

import java.util.Locale;

/**
 * A data container for the server's configuration.
 */
public final class Configuration {
    public Structure folderStructure;
    public Database databaseInfo;
    public Language language;
    public Server server;
    
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
        
        public boolean watchGachaConfiguration = false;
        public boolean enableShopItems = true;
        public Rates rates = new Rates();
        
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