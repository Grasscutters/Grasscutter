package emu.grasscutter.server.game.session;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.game.*;
import emu.grasscutter.utils.Utils;
import io.netty.buffer.ByteBuf;
import kcp.highway.*;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.*;

public final class GameSessionManager implements KcpListener {
    @Getter private static final GameSessionManager instance
        = new GameSessionManager();
    @Getter private static final ExecutorService executor
        = Executors.newWorkStealingPool();
    @Getter private static final Map<Ukcp, GameSession> sessions
        = new ConcurrentHashMap<>();

    /**
     * Waits for the game server to be ready.
     *
     * @return The game server.
     */
    private GameServer waitForServer() {
        var server = Grasscutter.getGameServer();
        var times = 0; while (server == null || !server.isStarted()) {
            Utils.sleep(1000); // Wait 1s for the server to start.
            if (times++ > 5) {
                Grasscutter.getLogger().error("Game server has not started in a reasonable time.");
                return null;
            }

            server = Grasscutter.getGameServer();
        }

        return server;
    }

    @Override
    public void onConnected(Ukcp ukcp) {
        // Fetch the game server.
        var server = this.waitForServer();
        if (server == null) {
            ukcp.close();
            return;
        }

        // Create a new session.
        var session = sessions.compute(ukcp, (k, existing) -> {
            // Close an existing session.
            if (existing != null) {
                existing.close();
            }

            return new GameSession(server);
        });

        // Connect the session.
        session.onConnected(new GameSessionHandler(ukcp));
    }

    @Override
    public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
        // Get the session.
        var session = sessions.get(ukcp);
        if (session == null) {
            ukcp.close(); return;
        }

        // Handle the message in a separate thread.
        executor.submit(() -> {
            var bytes = Utils.byteBufToArray(byteBuf);
            session.onMessage(bytes);
        });
    }

    @Override
    public void handleException(Throwable throwable, Ukcp ukcp) {
        Grasscutter.getLogger().error("Exception in game session.", throwable);
    }

    @Override
    public void handleClose(Ukcp ukcp) {
        var session = sessions.remove(ukcp);
        if (session != null) {
            session.close();
        }
    }
}
