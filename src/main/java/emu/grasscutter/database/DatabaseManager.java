package emu.grasscutter.database;

import static emu.grasscutter.config.Configuration.*;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.annotations.Entity;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.experimental.filters.Filters;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.game.Account;

import org.reflections.Reflections;

public final class DatabaseManager {
    private static Datastore gameDatastore;
    private static Datastore dispatchDatastore;

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
        Class<?>[] entities = new Reflections(Grasscutter.class.getPackageName())
                .getTypesAnnotatedWith(Entity.class)
                .stream()
                .filter(cls -> {
                    Entity e = cls.getAnnotation(Entity.class);
                    return e != null && !e.value().equals(Mapper.IGNORED_FIELDNAME);
                })
                .toArray(Class<?>[]::new);

        gameDatastore.getMapper().map(entities);

        // Ensure indexes for the game datastore
        ensureIndexes(gameDatastore);

        if (SERVER.runMode == ServerRunMode.GAME_ONLY) {
            MongoClient dispatchMongoClient = MongoClients.create(DATABASE.server.connectionUri);

            dispatchDatastore = Morphia.createDatastore(dispatchMongoClient, DATABASE.server.collection, mapperOptions);
            dispatchDatastore.getMapper().map(new Class<?>[] {DatabaseCounter.class, Account.class});

            // Ensure indexes for dispatch datastore
            ensureIndexes(dispatchDatastore);
        }
    }

    /**
     * Ensures the database indexes exist and rebuilds them if there is an error with them
     * @param datastore The datastore to ensure indexes on
     */
    private static void ensureIndexes(Datastore datastore) {
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
