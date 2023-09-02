package emu.grasscutter.game.managers.blossom;

import java.util.*;
import lombok.Getter;

public class BlossomConfig {
    @Getter private int monsterFightingVolume;
    // @Getter private Int2ObjectMap<IntList> monsterIdsPerDifficulty;  // Need to wrangle Gson for
    // this
    @Getter private Map<Integer, List<Integer>> monsterIdsPerDifficulty;
}
