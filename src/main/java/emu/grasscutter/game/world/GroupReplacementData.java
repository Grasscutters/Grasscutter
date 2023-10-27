package emu.grasscutter.game.world;

import java.util.List;
import lombok.Data;

@Data
public class GroupReplacementData {
    public int id;
    public List<Integer> replace_groups;

    public GroupReplacementData() {}

    public GroupReplacementData(int id, List<Integer> replace_groups) {
        this.id = id;
        this.replace_groups = replace_groups;
    }
}
