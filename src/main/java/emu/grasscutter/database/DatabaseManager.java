package emu.grasscutter.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.friends.Friendship;
import emu.grasscutter.game.inventory.GenshinItem;

public final class DatabaseManager {
	private static MongoClient mongoClient;
	private static MongoClient dispatchMongoClient;

	private static Datastore datastore;
	private static Datastore dispatchDatastore;
	
	private static final Class<?>[] mappedClasses = new Class<?>[] {
		DatabaseCounter.class, Account.class, GenshinPlayer.class, GenshinAvatar.class, GenshinItem.class, Friendship.class
	};
    
    public static MongoClient getMongoClient() {
        return mongoClient;
    }

	public static Datastore getDatastore() {
		return datastore;
	}

	public static MongoDatabase getDatabase() {
    	return getDatastore().getDatabase();
    }

	// Yes. I very dislike this method. However, this will be good for now.
	// TODO: Add dispatch routes for player account management
	public static Datastore getAccountDatastore() {
		if(Grasscutter.getConfig().RunMode.equalsIgnoreCase("GAME_ONLY")) {
			return dispatchDatastore;
		} else {
			return datastore;
		}
	}
	
	public static void initialize() {
		// Initialize
		mongoClient = new MongoClient(new MongoClientURI(Grasscutter.getConfig().DatabaseUrl));
		Morphia morphia = new Morphia();
		
		// TODO Update when migrating to Morphia 2.0
		morphia.getMapper().getOptions().setStoreEmpties(true);
		morphia.getMapper().getOptions().setStoreNulls(false);
		morphia.getMapper().getOptions().setDisableEmbeddedIndexes(true);
		
		// Map
		morphia.map(mappedClasses);
		
		// Build datastore
		datastore = morphia.createDatastore(mongoClient, Grasscutter.getConfig().DatabaseCollection);
		
		// Ensure indexes
		try {
			datastore.ensureIndexes();
		} catch (MongoCommandException e) {
			Grasscutter.getLogger().info("Mongo index error: ", e);
			// Duplicate index error
			if (e.getCode() == 85) {
				// Drop all indexes and re add them
				MongoIterable<String> collections = datastore.getDatabase().listCollectionNames();
				for (String name : collections) {
					datastore.getDatabase().getCollection(name).dropIndexes();
				}
				// Add back indexes
				datastore.ensureIndexes();
			}
		}

		if(Grasscutter.getConfig().RunMode.equalsIgnoreCase("GAME_ONLY")) {
			dispatchMongoClient = new MongoClient(new MongoClientURI(Grasscutter.getConfig().getGameServerOptions().DispatchServerDatabaseUrl));
			dispatchDatastore = morphia.createDatastore(dispatchMongoClient, Grasscutter.getConfig().getGameServerOptions().DispatchServerDatabaseCollection);

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
		DatabaseCounter counter = getDatastore().createQuery(DatabaseCounter.class).field("_id").equal(c.getSimpleName()).find().tryNext();
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