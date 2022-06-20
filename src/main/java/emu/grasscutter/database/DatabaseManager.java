package emu.grasscutter.database;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.experimental.filters.Filters;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.gacha.GachaRecord;
import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;

import static emu.grasscutter.Configuration.DATABASE;
import static emu.grasscutter.Configuration.SERVER;

public final class DatabaseManager {
    private static Datastore gameDatastore;
    private static Datastore dispatchDatastore;

    private static final Class<?>[] mappedClasses = new Class<?>[]{
        DatabaseCounter.class, Account.class, Player.class, Avatar.class, GameItem.class, Friendship.class,
        GachaRecord.class, Mail.class, GameMainQuest.class, GameHome.class
    };

    public static Datastore getGameDatastore() {
        return gameDatastore;
    }

    public static MongoDatabase getGameDatabase() {
        return getGameDatastore().getDatabase();
    }

    // Yes. I very dislike this method. However, this will be good for now.
    // TODO: Add dispatch routes for player account management
    public static Datastore getAccountDatastore() {
        if (SERVER.runMode == ServerRunMode.GAME_ONLY) {
            return dispatchDatastore;
        } else {
            return gameDatastore;
        }
    }

    public static void initialize() {
        // Initialize
        MongoClient gameMongoClient = MongoClients.create(DATABASE.game.connectionUri);

        // Set mapper options.
        MapperOptions mapperOptions = MapperOptions.builder()
            .storeEmpties(true).storeNulls(false).build();
        // Create data store.
        gameDatastore = Morphia.createDatastore(gameMongoClient, DATABASE.game.collection, mapperOptions);
        // Map classes.
        gameDatastore.getMapper().map(mappedClasses);

        // Ensure indexes
        try {
            gameDatastore.ensureIndexes();
        } catch (MongoCommandException exception) {
            Grasscutter.getLogger().info("Mongo index error: ", exception);
            // Duplicate index error
            if (exception.getCode() == 85) {
                // Drop all indexes and re add them
                MongoIterable<String> collections = gameDatastore.getDatabase().listCollectionNames();
                for (String name : collections) {
                    gameDatastore.getDatabase().getCollection(name).dropIndexes();
                }
                // Add back indexes
                gameDatastore.ensureIndexes();
            }
        }

        if (SERVER.runMode == ServerRunMode.GAME_ONLY) {
            MongoClient dispatchMongoClient = MongoClients.create(DATABASE.server.connectionUri);
            dispatchDatastore = Morphia.createDatastore(dispatchMongoClient, DATABASE.server.collection);

            // Ensure indexes for dispatch server
            try {
                dispatchDatastore.ensureIndexes();
            } catch (MongoCommandException e) {
                Grasscutter.getLogger().info("Mongo index error: ", e);
                // Duplicate index error
                if (e.getCode() == 85) {
                    // Drop all indexes and re add them
                    MongoIterable<String> collections = dispatchDatastore.getDatabase().listCollectionNames();
                    for (String name : collections) {
                        dispatchDatastore.getDatabase().getCollection(name).dropIndexes();
                    }
                    // Add back indexes
                    dispatchDatastore.ensureIndexes();
                }
            }
        }
    }

    public static synchronized int getNextId(Class<?> c) {
        DatabaseCounter counter = getGameDatastore().find(DatabaseCounter.class).filter(Filters.eq("_id", c.getSimpleName())).first();
        if (counter == null) {
            counter = new DatabaseCounter(c.getSimpleName());
        }
        try {
            return counter.getNextId();
        } finally {
            getGameDatastore().save(counter);
        }
    }

    public static synchronized int getNextId(Object o) {
        return getNextId(o.getClass());
    }
}