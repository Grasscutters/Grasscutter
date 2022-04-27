package emu.grasscutter.plugin.api;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.server.game.GameServer;

import java.util.LinkedList;
import java.util.List;

/**
 * Hooks into the {@link GameServer} class, adding convenient ways to do certain things.
 */
public final class ServerHook {
    private static ServerHook instance;
    private final GameServer gameServer;
    private final DispatchServer dispatchServer;

    /**
     * Gets the server hook instance.
     * @return A {@link ServerHook} singleton.
     */
    public static ServerHook getInstance() {
        return instance;
    }

    /**
     * Hooks into a server.
     * @param gameServer The game server to hook into.
     * @param dispatchServer The dispatch server to hook into.
     */
    public ServerHook(GameServer gameServer, DispatchServer dispatchServer) {
        this.gameServer = gameServer;
        this.dispatchServer = dispatchServer;
        
        instance = this;
    }

    /**
     * Gets all online players.
     * @return Players connected to the server.
     */
    public List<GenshinPlayer> getOnlinePlayers() {
        return new LinkedList<>(this.gameServer.getPlayers().values());
    }
}