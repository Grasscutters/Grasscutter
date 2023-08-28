package emu.grasscutter.server.event.player;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerLevelAvatarEvent extends PlayerEvent {
    private final int oldLevel;
    private final Avatar avatar;

    public PlayerLevelAvatarEvent(Player player, int oldLevel, Avatar avatar) {
        super(player);

        this.oldLevel = oldLevel;
        this.avatar = avatar;
    }

    /**
     * @return The avatar's new level.
     */
    public int getNewLevel() {
        return this.getAvatar().getLevel();
    }
}
