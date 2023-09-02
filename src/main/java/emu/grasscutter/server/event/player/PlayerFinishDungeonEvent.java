package emu.grasscutter.server.event.player;

import emu.grasscutter.game.dungeons.DungeonManager;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.event.Event;
import java.util.List;
import lombok.*;

@Getter
@RequiredArgsConstructor
public final class PlayerFinishDungeonEvent extends Event {
    private final List<Player> players;
    private final Scene dungeon;
    private final DungeonManager manager;
}
