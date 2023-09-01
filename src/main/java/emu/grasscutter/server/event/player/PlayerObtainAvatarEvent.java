package emu.grasscutter.server.event.player;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerObtainAvatarEvent extends PlayerEvent {
    private final Avatar avatar;

    public PlayerObtainAvatarEvent(Player player, Avatar avatar) {
        super(player);

        this.avatar = avatar;
    }
}
