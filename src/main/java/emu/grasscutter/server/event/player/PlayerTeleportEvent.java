package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import emu.grasscutter.utils.Position;

public final class PlayerTeleportEvent extends PlayerEvent implements Cancellable {
    private final TeleportType type;
    private final Position from;
    private Position to;

    public PlayerTeleportEvent(Player player, TeleportType type, Position from, Position to) {
        super(player);

        this.type = type;
        this.from = from;
        this.to = to;
    }

    public TeleportType getTeleportType() {
        return this.type;
    }

    public Position getSource() {
        return this.from;
    }

    public Position getDestination() {
        return this.to;
    }

    public void setDestination(Position to) {
        this.to = to;
    }

    public enum TeleportType {
        /**
         * There is no specified reason to teleport.
         */
        INTERNAL,

        /**
         * The player has asked to teleport to a waypoint.
         */
        WAYPOINT,

        /**
         * The player has asked to teleport using the map.
         */
        MAP,

        /**
         * The player has asked to teleport into a dungeon.
         */
        DUNGEON,

        /**
         * The player has asked to teleport using the command.
         */
        COMMAND
    }
}
