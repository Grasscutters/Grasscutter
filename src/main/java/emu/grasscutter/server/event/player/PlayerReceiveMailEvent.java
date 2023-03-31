package emu.grasscutter.server.event.player;

import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;

public final class PlayerReceiveMailEvent extends PlayerEvent implements Cancellable {
    private Mail message;

    public PlayerReceiveMailEvent(Player player, Mail message) {
        super(player);

        this.message = message;
    }

    public Mail getMessage() {
        return this.message;
    }

    public void setMessage(Mail message) {
        this.message = message;
    }
}
