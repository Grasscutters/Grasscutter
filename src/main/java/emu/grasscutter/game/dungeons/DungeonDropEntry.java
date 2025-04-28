package emu.grasscutter.game.dungeons;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DungeonDropEntry {
    private List<Integer> counts;
    private List<Integer> items;
    private List<Integer> probabilities;
    private List<Integer> itemProbabilities;
    private boolean mpDouble;
}
