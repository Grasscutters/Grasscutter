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
	private static Datastore datastore;
	
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