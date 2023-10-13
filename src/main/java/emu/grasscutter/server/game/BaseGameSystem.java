package emu.grasscutter.server.game;

/**
 * This is an abstract class representing the base game system.
 * It contains a protected final field server which represents the game server.
 */
public abstract class BaseGameSystem {
    protected final GameServer server;

    /**
     * Constructor for the BaseGameSystem class.
     * Initializes the server field with the provided GameServer object.
     * @param server The GameServer object to be assigned to the server field.
     */
    public BaseGameSystem(GameServer server) {
        this.server = server;
    }

    /**
     * Getter method for the server field.
     * @return The GameServer object assigned to the server field.
     */
    public GameServer getServer() {
        return this.server;
    }
}