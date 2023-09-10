package emu.grasscutter.utils.objects;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.Grasscutter.ServerRunMode;
import emu.grasscutter.database.*;

public interface DatabaseObject<T> {
    /**
     * @return Does this object belong in the game database?
     */
    default boolean isGameObject() {
        return true;
    }

    /**
     * @return Should this object be saved immediately?
     */
    default boolean saveImmediately() {
        return false;
    }

    /**
     * Performs a deferred save.
     * This object will save as a group with other objects.
     */
    default void deferSave() {
        Database.save(this);
    }

    /**
     * Attempts to save this object to the database.
     */
    default void save() {
        if (this.isGameObject()) {
            DatabaseManager.getGameDatastore().save(this);
        } else if (Grasscutter.getRunMode() != ServerRunMode.GAME_ONLY) {
            DatabaseManager.getAccountDatastore().save(this);
        } else {
            throw new UnsupportedOperationException("Unable to store an account object while in game-only mode.");
        }
    }
}
