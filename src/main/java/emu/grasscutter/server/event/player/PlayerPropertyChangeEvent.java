package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.*;

@Getter
public final class PlayerPropertyChangeEvent extends PlayerEvent implements Cancellable {
    @Setter private PlayerProperty property;
    private final int oldValue;
    @Setter private int newValue;

    public PlayerPropertyChangeEvent(
            Player player, PlayerProperty property, int oldValue, int newValue) {
        super(player);

        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
