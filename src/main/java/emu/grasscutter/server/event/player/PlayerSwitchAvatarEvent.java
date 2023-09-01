package emu.grasscutter.server.event.player;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.*;

@Getter
public final class PlayerSwitchAvatarEvent extends PlayerEvent implements Cancellable {
    private final Avatar previousAvatar;
    @Setter private Avatar newAvatar;

    public PlayerSwitchAvatarEvent(Player player, Avatar previousAvatar, Avatar newAvatar) {
        super(player);

        this.previousAvatar = previousAvatar;
        this.newAvatar = newAvatar;
    }

    /**
     * @return The previous avatar as an entity.
     */
    public EntityAvatar getPreviousAvatarEntity() {
        return this.previousAvatar.getAsEntity();
    }

    /**
     * @return The new avatar as an entity.
     */
    public EntityAvatar getNewAvatarEntity() {
        return this.newAvatar.getAsEntity();
    }
}
