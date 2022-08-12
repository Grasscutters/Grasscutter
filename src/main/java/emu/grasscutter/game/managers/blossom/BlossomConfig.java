package emu.grasscutter.game.managers.blossom;

import java.util.List;
import java.util.Map;

// import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// import it.unimi.dsi.fastutil.ints.IntList;

public class BlossomConfig {
    // Int2ObjectMap<IntList> monsterIdsPerDifficulty;  // Need to wrangle Gson for this
    Map<Integer, List<Integer>> monsterIdsPerDifficulty;
}
