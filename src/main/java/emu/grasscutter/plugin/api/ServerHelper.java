package emu.grasscutter.plugin.api;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem;
import emu.grasscutter.command.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.*;
import emu.grasscutter.server.scheduler.ServerTaskScheduler;
import java.util.*;
import java.util.stream.Stream;

/** Hooks into the {@link GameServer} class, adding convenient ways to do certain things. */
public final class ServerHelper {
    private static ServerHelper instance;
    private final GameServer gameServer;
    private final HttpServer httpServer;

    /**
     * Hooks into a server.
     *
     * @param gameServer The game server to hook into.
     * @param httpServer The HTTP server to hook into.
     */
    public ServerHelper(GameServer gameServer, HttpServer httpServer) {
        this.gameServer = gameServer;
        this.httpServer = httpServer;

        instance = this;
    }

    /**
     * Gets the server hook instance.
     *
     * @return A {@link ServerHelper} singleton.
     */
    public static ServerHelper getInstance() {
        return instance;
    }

    /**
     * @return The server's current run mode.
     */
    public Grasscutter.ServerRunMode getRunMode() {
        return Grasscutter.getRunMode();
    }

    /**
     * @return The game server.
     */
    public GameServer getGameServer() {
        return this.gameServer;
    }

    /**
     * @return The HTTP server.
     */
    public HttpServer getHttpServer() {
        return this.httpServer;
    }

    /**
     * Gets all online players.
     *
     * @return Players connected to the server.
     */
    public List<Player> getOnlinePlayers() {
        return new ArrayList<>(this.gameServer.getPlayers().values());
    }

    /**
     * Gets all online players.
     *
     * @return Players connected to the server.
     */
    public Stream<Player> getOnlinePlayersStream() {
        return this.gameServer.getPlayers().values().stream();
    }

    /**
     * Registers a command to the {@link emu.grasscutter.command.CommandMap}.
     *
     * @param handler The command handler.
     */
    public void registerCommand(CommandHandler handler) {
        Class<? extends CommandHandler> clazz = handler.getClass();
        if (!clazz.isAnnotationPresent(Command.class))
            throw new IllegalArgumentException("Command handler must be annotated with @Command.");
        Command commandData = clazz.getAnnotation(Command.class);
        CommandMap.getInstance().registerCommand(commandData.label(), handler);
    }

    /**
     * Adds a router using an instance of a class.
     *
     * @param router A router instance.
     */
    public void addRouter(Router router) {
        this.addRouter(router.getClass());
    }

    /**
     * Adds a router using a class.
     *
     * @param router The class of the router.
     */
    public void addRouter(Class<? extends Router> router) {
        this.httpServer.addRouter(router);
    }

    /**
     * Sets the server's authentication system.
     *
     * @param authSystem An instance of the authentication system.
     */
    public void setAuthSystem(AuthenticationSystem authSystem) {
        Grasscutter.setAuthenticationSystem(authSystem);
    }

    /**
     * Sets the server's permission handler.
     *
     * @param permHandler An instance of the permission handler.
     */
    public void setPermissionHandler(PermissionHandler permHandler) {
        Grasscutter.setPermissionHandler(permHandler);
    }

    /**
     * @return The server's task scheduler.
     */
    public ServerTaskScheduler getScheduler() {
        if (this.getGameServer() == null) return null;

        return this.getGameServer().getScheduler();
    }
}
