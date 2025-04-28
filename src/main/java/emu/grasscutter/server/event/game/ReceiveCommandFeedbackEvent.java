package emu.grasscutter.server.event.game;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.ServerEvent;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

public final class ReceiveCommandFeedbackEvent extends ServerEvent implements Cancellable {
    @Nullable private final Player player;
    @Setter @Getter private String message;

    public ReceiveCommandFeedbackEvent(@Nullable Player player, String message) {
        super(Type.GAME);

        this.player = player;
        this.message = message;
    }

    @Nullable public Player getPlayer() {
        return this.player;
    }
}
