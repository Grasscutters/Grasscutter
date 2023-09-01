package emu.grasscutter.server.event.player;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerLevelItemEvent extends PlayerEvent {
    private final int oldLevel;
    private final GameItem item;

    public PlayerLevelItemEvent(Player player, int oldLevel, GameItem item) {
        super(player);

        this.oldLevel = oldLevel;
        this.item = item;
    }

    /**
     * @return The item's new level.
     */
    public int getNewLevel() {
        return this.getItem().getLevel();
    }
}
