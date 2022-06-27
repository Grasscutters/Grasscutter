package emu.grasscutter.server.event.game;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.ServerEvent;

/**
 * @deprecated Will be removed in 1.2.3-dev or 1.3.0.
 */
@Deprecated(since = "1.2.2-dev", forRemoval = true)
public final class CommandResponseEvent extends ServerEvent {
    private String message;
    private Player player;

    public CommandResponseEvent(Type type,  Player player,String message) {
        super(type);
        this.message = message;
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }
}
