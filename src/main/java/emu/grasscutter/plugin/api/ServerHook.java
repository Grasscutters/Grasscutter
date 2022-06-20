package emu.grasscutter.plugin.api;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.auth.AuthenticationSystem;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.PermissionHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameServer;
import emu.grasscutter.server.http.HttpServer;
import emu.grasscutter.server.http.Router;

import java.util.LinkedList;
import java.util.List;

/**
 * Hooks into the {@link GameServer} class, adding convenient ways to do certain things.
 * Accessible via {@link emu.grasscutter.plugin.Plugin#getHandle()} or {@link #getInstance()}.
 */
public final class ServerHook {
    private static ServerHook instance;
    private final GameServer gameServer;
    private final HttpServer httpServer;

    /**
     * Gets the server hook instance.
     *
     * @return A {@link ServerHook} singleton.
     */
    public static ServerHook getInstance() {
        return instance;
    }

    /**
     * Hooks into a server.
     *
     * @param gameServer The game server to hook into.
     * @param httpServer The HTTP server to hook into.
     */
    public ServerHook(GameServer gameServer, HttpServer httpServer) {
        this.gameServer = gameServer;
        this.httpServer = httpServer;

        instance = this;
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
        return new LinkedList<>(this.gameServer.getPlayers().values());
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
        this.gameServer.getCommandMap().registerCommand(commandData.label(), handler);
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
     * Registers a {@link PacketHandler} to the server's packet handler.
     *
     * @param handler The class of the packet handler to register.
     */
    public void registerPacket(Class<? extends PacketHandler> handler) {
        this.gameServer.getPacketHandler().registerPacketHandler(handler);
    }
}