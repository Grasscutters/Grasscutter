package emu.grasscutter.game.world;

import java.util.List;
import lombok.Data;

@Data
public class GroupReplacementData {
    int id;
    List<Integer> replace_groups;
}
