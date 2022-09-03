package emu.grasscutter.server.event.player;

import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * This event is invoked when the player uses food on an avatar.
 */
public final class PlayerUseFoodEvent extends PlayerEvent implements Cancellable {
    @Getter @Setter private ItemData foodUsed;
    @Getter private final EntityAvatar selectedAvatar;

    public PlayerUseFoodEvent(Player player, ItemData foodUsed, EntityAvatar selectedAvatar) {
        super(player);

        this.foodUsed = foodUsed;
        this.selectedAvatar = selectedAvatar;
    }
}
