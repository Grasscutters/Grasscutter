package emu.grasscutter.plugin.api;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
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
     * @return The game server.
     */
    public GameServer getGameServer() {
        return this.gameServer;
    }

    /**
     * @return The dispatch server.
     */
    public DispatchServer getDispatchServer() {
        return this.dispatchServer;
    }

    /**
     * Gets all online players.
     * @return Players connected to the server.
     */
    public List<Player> getOnlinePlayers() {
        return new LinkedList<>(this.gameServer.getPlayers().values());
    }

    /**
     * Registers a command to the {@link emu.grasscutter.command.CommandMap}.
     * @param handler The command handler.
     */
    public void registerCommand(CommandHandler handler) {
        Class<? extends CommandHandler> clazz = handler.getClass();
        if(!clazz.isAnnotationPresent(Command.class))
            throw new IllegalArgumentException("Command handler must be annotated with @Command.");
        Command commandData = clazz.getAnnotation(Command.class);
        this.gameServer.getCommandMap().registerCommand(commandData.label(), handler);
    }
}