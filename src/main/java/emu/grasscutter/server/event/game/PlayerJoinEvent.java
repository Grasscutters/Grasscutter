package emu.grasscutter.server.event.game;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.GameEvent;

public final class PlayerJoinEvent extends GameEvent implements Cancellable {
    private final Player player;
    
    public PlayerJoinEvent(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
