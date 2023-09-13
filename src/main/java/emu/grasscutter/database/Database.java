package emu.grasscutter.database;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.objects.DatabaseObject;
import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Complicated manager of the MongoDB database.
 * Handles caching, data operations, and more.
 */
public interface Database {
    Logger logger = LoggerFactory.getLogger("Database");
    List<DatabaseObject<?>> objects = new CopyOnWriteArrayList<>();

    /**
     * Queues an object to be saved.
     *
     * @param object The object to save.
     */
    static void save(DatabaseObject<?> object) {
        if (object.saveImmediately()) {
            object.save();
        } else {
            objects.add(object);
        }
    }

    /**
     * Performs a bulk save of all deferred objects.
     */
    static void saveAll() {
        var size = objects.size();
        Database.saveAll(objects);

        logger.debug("Performed auto save on {} objects.", size);
    }

    /**
     * Performs a bulk save of all deferred objects.
     *
     * @param objects The objects to save.
     */
    static void saveAll(List<? extends DatabaseObject<?>> objects) {
        // Sort all objects into their respective databases.
        var gameObjects = objects.stream()
                .filter(DatabaseObject::isGameObject)
                .toList();
        var accountObjects = objects.stream()
                .filter(o -> !o.isGameObject())
                .toList();

        // Clear the collective list.
        objects.clear();

        // Save all objects.
        var executor = DatabaseHelper.getEventExecutor();
        if (Grasscutter.getRunMode() != Grasscutter.ServerRunMode.DISPATCH_ONLY) {
            executor.submit(() -> {
                DatabaseManager.getGameDatastore().save(gameObjects);
            });
        }
        if (Grasscutter.getRunMode() != Grasscutter.ServerRunMode.GAME_ONLY) {
            executor.submit(() -> {
                DatabaseManager.getAccountDatastore().save(accountObjects);
            });
        }
    }

    /**
     * Starts the auto-save thread.
     * Runs every 5 minutes.
     */
    static void startSaveThread() {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Database.saveAll();
            }
        }, 0, 1000 * 60 * 5);
    }
}
