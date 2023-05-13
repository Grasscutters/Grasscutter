package emu.grasscutter.plugin.api;

import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.HttpServer;

@Deprecated(since = "2.0.0", forRemoval = true)
public final class ServerHook extends ServerHelper {
    private static ServerHook instance;

    /**
     * Hooks into a server.
     *
     * @param gameServer The game server to hook into.
     * @param httpServer The HTTP server to hook into.
     */
    public ServerHook(GameServer gameServer, HttpServer httpServer) {
        super(gameServer, httpServer);

        instance = this;
    }

    /**
     * Gets the server hook instance.
     *
     * @return A {@link ServerHook} singleton.
     */
    public static ServerHook getInstance() {
        return instance;
    }
}
