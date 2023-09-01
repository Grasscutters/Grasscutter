package emu.grasscutter.game.world;

import lombok.Data;

import java.util.List;

@Data
public class GroupReplacementData {
    int id;
    List<Integer> replace_groups;
}
