package emu.grasscutter.server.game;

public abstract class BaseGameSystem {
    protected final GameServer server;

    public BaseGameSystem(GameServer server) {
        this.server = server;
    }

    public GameServer getServer() {
        return this.server;
    }
}
