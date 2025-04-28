package emu.grasscutter.game.dungeons;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DungeonDrop {
    private int dungeonId;
    private List<DungeonDropEntry> drops;
}
