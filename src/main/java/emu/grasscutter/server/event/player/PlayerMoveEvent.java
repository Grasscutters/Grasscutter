package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.server.event.types.PlayerEvent;

public final class PlayerMoveEvent extends PlayerEvent {
    private final MoveType type;
    private final Position from;
    private final Position to;

    public PlayerMoveEvent(Player player, MoveType type, Position from, Position to) {
        super(player);

        this.type = type;
        this.from = from;
        this.to = to;
    }

    public MoveType getMoveType() {
        return this.type;
    }

    public Position getSource() {
        return this.from;
    }

    public Position getDestination() {
        return this.to;
    }

    public enum MoveType {
        /** The player has sent a combat invocation to move. */
        PLAYER,

        /** The server has requested that the player moves. */
        SERVER
    }
}
