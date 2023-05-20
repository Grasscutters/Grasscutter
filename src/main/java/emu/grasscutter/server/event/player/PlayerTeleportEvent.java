package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.game.world.data.TeleportProperties;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;

public final class PlayerTeleportEvent extends PlayerEvent implements Cancellable {
    private final TeleportProperties properties;
    private final Position from;

    public PlayerTeleportEvent(Player player, TeleportProperties properties, Position from) {
        super(player);

        this.properties = properties;
        this.from = from;
    }

    public TeleportType getTeleportType() {
        return this.properties.getTeleportType();
    }

    public Position getSource() {
        return this.from;
    }

    public Position getDestination() {
        return this.properties.getTeleportTo();
    }

    public void setDestination(Position to) {
        this.properties.setTeleportTo(to);
    }

    public enum TeleportType {
        /** There is no specified reason to teleport. */
        INTERNAL,

        /** The player has asked to teleport to a waypoint. */
        WAYPOINT,

        /** The player has asked to teleport using the map. */
        MAP,

        /** The player has asked to teleport into a dungeon. */
        DUNGEON,

        /** The player has asked to teleport using the command. */
        COMMAND,

        /** A script has teleported the player. */
        SCRIPT,

        /** The client has requested to teleport. (script) */
        CLIENT
    }
}
