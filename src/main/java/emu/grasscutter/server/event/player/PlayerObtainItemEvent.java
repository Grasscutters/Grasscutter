package emu.grasscutter.server.event.player;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerObtainItemEvent extends PlayerEvent {
    private final GameItem item;

    public PlayerObtainItemEvent(Player player, GameItem item) {
        super(player);

        this.item = item;
    }
}
