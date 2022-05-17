package emu.grasscutter.server.event.game;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.GameEvent;
import emu.grasscutter.server.event.types.ServerEvent;

public class CommandResponseEvent extends ServerEvent {
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
