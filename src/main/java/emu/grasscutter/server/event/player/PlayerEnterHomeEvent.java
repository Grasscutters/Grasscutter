package emu.grasscutter.server.event.player;

import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerEnterHomeEvent extends PlayerEvent implements Cancellable {
    private final GameHome home;
    private final Player homeOwner;
    private final boolean isOtherHome;

    public PlayerEnterHomeEvent(Player player, Player homeOwner, GameHome home) {
        super(player);

        this.home = home;
        this.homeOwner = homeOwner;
        this.isOtherHome = this.getPlayer().equals(this.homeOwner);
    }
}
