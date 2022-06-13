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

import static emu.grasscutter.Configuration.*;

public final class DatabaseManager {
	private static Datastore gameDatastore;
	private static Datastore dispatchDatastore;
    private static Datastore hybridDatastore;

	private static final Class<?>[] mappedGameClasses = new Class<?>[] {
		DatabaseCounter.class, Player.class, Avatar.class, GameItem.class, Friendship.class,
		GachaRecord.class, Mail.class, GameMainQuest.class
	};

	private static final Class<?>[] mappedDispatchClasses = new Class<?>[]{
        DatabaseCounter.class, Account.class
	};

    private static final Class<?>[] mappedHybridClasses = new Class<?>[] {
        DatabaseCounter.class, Account.class, Player.class, Avatar.class, GameItem.class, Friendship.class,
        GachaRecord.class, Mail.class, GameMainQuest.class
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
        return switch (SERVER.runMode){
            case GAME_ONLY -> gameDatastore;
            case DISPATCH_ONLY -> dispatchDatastore;
            default -> hybridDatastore;
        };
    }

	public static void initialize() {
		// Initialize
		MongoClient gameMongoClient = MongoClients.create(DATABASE.game.connectionUri);
		MapperOptions mapperOptionsGame = MapperOptions.builder().storeEmpties(true).storeNulls(false).build();
		gameDatastore = Morphia.createDatastore(gameMongoClient, DATABASE.game.collection, mapperOptionsGame);
		gameDatastore.getMapper().map(mappedGameClasses);

        MongoClient dispatchMongoClient = MongoClients.create(DATABASE.server.connectionUri);
        MapperOptions mapperOptionsDispatch = MapperOptions.builder().storeEmpties(true).storeNulls(false).build();
        dispatchDatastore = Morphia.createDatastore(dispatchMongoClient,DATABASE.server.collection,mapperOptionsDispatch);
        dispatchDatastore.getMapper().map(mappedDispatchClasses);

        MongoClient hybridMongoClient = MongoClients.create(DATABASE.game.connectionUri);
        MapperOptions mapperOptionsHybrid = MapperOptions.builder().storeEmpties(true).storeNulls(false).build();
        hybridDatastore = Morphia.createDatastore(hybridMongoClient,DATABASE.game.collection,mapperOptionsHybrid);
        hybridDatastore.getMapper().map(mappedHybridClasses);

		/*
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

		if(SERVER.runMode == ServerRunMode.GAME_ONLY) {
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
		 */
	}

	public static synchronized int getNextId(Class<?> c) {
		DatabaseCounter counter = getAccountDatastore().find(DatabaseCounter.class).filter(Filters.eq("_id", c.getSimpleName())).first();
		if (counter == null) {
			counter = new DatabaseCounter(c.getSimpleName());
		}
		try {
			return counter.getNextId();
		} finally {
			getAccountDatastore().save(counter);
		}
	}

	public static synchronized int getNextId(Object o) {
		return getNextId(o.getClass());
	}
}