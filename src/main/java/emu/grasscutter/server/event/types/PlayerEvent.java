package emu.grasscutter.server.event.types;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Event;
import lombok.Getter;

/** An event that is related to player interactions. */
@Getter
public abstract class PlayerEvent extends Event {
    protected final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }
}
