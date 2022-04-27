package emu.grasscutter.plugin.api;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;

import java.util.LinkedList;
import java.util.List;

/**
 * Hooks into the {@link GameServer} class, adding convenient ways to do certain things.
 */
public final class ServerHook {
    private static ServerHook instance;
    private final GameServer server;

    /**
     * Gets the server hook instance.
     * @return A {@link ServerHook} singleton.
     */
    public static ServerHook getInstance() {
        return instance;
    }

    /**
     * Hooks into a server.
     * @param server The server to hook into.
     */
    public ServerHook(GameServer server) {
        this.server = server;
        
        instance = this;
    }

    /**
     * Gets all online players.
     * @return Players connected to the server.
     */
    public List<Player> getOnlinePlayers() {
        return new LinkedList<>(this.server.getPlayers().values());
    }
}