package emu.grasscutter.server.event.player;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import javax.annotation.Nullable;
import lombok.*;

@Getter
@Setter
public final class PlayerChatEvent extends PlayerEvent implements Cancellable {
    private String message;

    /**
     * This field being null signifies a message is being sent to a public chat. This can include
     * either a global chat or a team chat.
     */
    @Nullable private Player to;

    /**
     * This field is not null when the message is being sent to a public chat. Refer to {@link #to}
     * for more information.
     */
    @Nullable private Integer channelId;

    public PlayerChatEvent(Player player, String message, @Nullable Player to) {
        super(player);

        this.message = message;
        this.to = to;
    }

    public PlayerChatEvent(Player player, int emoteId, @Nullable Player to) {
        super(player);

        this.message = String.valueOf(emoteId);
        this.to = to;
    }

    public PlayerChatEvent(Player player, String message, int channelId) {
        super(player);

        this.message = message;
        this.channelId = channelId;
    }

    public PlayerChatEvent(Player player, int emoteId, int channelId) {
        super(player);

        this.message = String.valueOf(emoteId);
        this.channelId = channelId;
    }

    /**
     * @return The target player's UID.
     */
    public int getTargetUid() {
        return this.to == null ? -1 : this.to.getUid();
    }

    /**
     * @return The message as an integer, or -1 if it's not an integer.
     */
    public int getMessageAsInt() {
        try {
            return Integer.parseInt(this.message);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * @return The channel ID, or -1 if it's not a channel message.
     */
    public int getChannel() {
        return this.channelId == null ? -1 : this.channelId;
    }
}
