package emu.grasscutter.server.event.player;

import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;

public class PlayerLeaveHomeEvent extends PlayerEvent {
    private final GameHome home;
    private final Player homeOwner;
    private final boolean isOtherHome;
    private final Reason reason;

    public PlayerLeaveHomeEvent(Player player, Player homeOwner, GameHome home, Reason reason) {
        super(player);

        this.homeOwner = homeOwner;
        this.home = home;
        this.reason = reason;
        this.isOtherHome = !this.getPlayer().equals(this.homeOwner);
    }

    public enum Reason {
        PLAYER_LEAVE,
        KICKED
    }
}
