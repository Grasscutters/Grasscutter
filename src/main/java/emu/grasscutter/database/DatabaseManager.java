package emu.grasscutter.database;

import com.mongodb.MongoClientURI;
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
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;

public final class DatabaseManager {

	private static MongoClient mongoClient;
	private static MongoClient dispatchMongoClient;

	private static Datastore datastore;
	private static Datastore dispatchDatastore;
	
	private static final Class<?>[] mappedClasses = new Class<?>[] {
		DatabaseCounter.class, Account.class, Player.class, Avatar.class, GameItem.class, Friendship.class, GachaRecord.class, Mail.class
	};
    
    public static Datastore getDatastore() {
    	return datastore;
    }
    
    public static MongoDatabase getDatabase() {
    	return getDatastore().getDatabase();
    }

	// Yes. I very dislike this method. However, this will be good for now.
	// TODO: Add dispatch routes for player account management
	public static Datastore getAccountDatastore() {
		if(Grasscutter.getConfig().RunMode == ServerRunMode.GAME_ONLY) {
			return dispatchDatastore;
		} else {
			return datastore;
		}
	}
	
	public static void initialize() {
		// Initialize
		MongoClient mongoClient = MongoClients.create(Grasscutter.getConfig().DatabaseUrl);
		
		// Set mapper options.
		MapperOptions mapperOptions = MapperOptions.builder()
				.storeEmpties(true).storeNulls(false).build();
		// Create data store.
		datastore = Morphia.createDatastore(mongoClient, Grasscutter.getConfig().DatabaseCollection, mapperOptions);
		// Map classes.
		datastore.getMapper().map(mappedClasses);
		
		// Ensure indexes
		try {
			datastore.ensureIndexes();
		} catch (MongoCommandException exception) {
			Grasscutter.getLogger().info("Mongo index error: ", exception);
			// Duplicate index error
			if (exception.getCode() == 85) {
				// Drop all indexes and re add them
				MongoIterable<String> collections = datastore.getDatabase().listCollectionNames();
				for (String name : collections) {
					datastore.getDatabase().getCollection(name).dropIndexes();
				}
				// Add back indexes
				datastore.ensureIndexes();
			}
		}

		if(Grasscutter.getConfig().RunMode == ServerRunMode.GAME_ONLY) {
			dispatchMongoClient = MongoClients.create(Grasscutter.getConfig().getGameServerOptions().DispatchServerDatabaseUrl);
			dispatchDatastore = Morphia.createDatastore(dispatchMongoClient, Grasscutter.getConfig().getGameServerOptions().DispatchServerDatabaseCollection);

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
		DatabaseCounter counter = getDatastore().find(DatabaseCounter.class).filter(Filters.eq("_id", c.getSimpleName())).first();
		if (counter == null) {
			counter = new DatabaseCounter(c.getSimpleName());
		}
		try {
			return counter.getNextId();
		} finally {
			getDatastore().save(counter);
		}
	}

	public static synchronized int getNextId(Object o) {
		return getNextId(o.getClass());
	}
}