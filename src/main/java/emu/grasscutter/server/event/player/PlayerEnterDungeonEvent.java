package emu.grasscutter.server.event.player;

import emu.grasscutter.data.excels.dungeon.DungeonData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.types.PlayerEvent;
import lombok.Getter;

@Getter
public final class PlayerEnterDungeonEvent extends PlayerEvent {
    private final DungeonData dungeon;

    public PlayerEnterDungeonEvent(Player player, DungeonData dungeon) {
        super(player);

        this.dungeon = dungeon;
    }
}
