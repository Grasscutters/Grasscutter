package emu.grasscutter.game.managers.blossom;

import java.util.*;
import lombok.Getter;

@Getter
public class BlossomConfig {
    private int monsterFightingVolume;
    // @Getter private Int2ObjectMap<IntList> monsterIdsPerDifficulty;  // Need to wrangle Gson for
    // this
    private Map<Integer, List<Integer>> monsterIdsPerDifficulty;
}
