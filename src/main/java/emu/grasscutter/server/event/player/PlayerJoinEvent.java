package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;

public final class PlayerJoinEvent extends PlayerEvent implements Cancellable {
    public PlayerJoinEvent(Player player) {
        super(player);
    }
}
