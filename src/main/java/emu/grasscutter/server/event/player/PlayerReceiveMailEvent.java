package emu.grasscutter.server.event.player;

import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class PlayerReceiveMailEvent extends PlayerEvent implements Cancellable {
    private Mail message;

    public PlayerReceiveMailEvent(Player player, Mail message) {
        super(player);

        this.message = message;
    }
}
