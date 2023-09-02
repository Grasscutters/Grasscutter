package emu.grasscutter.data.server;

import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.geometry.Geometry;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.world.*;
import emu.grasscutter.scripts.SceneIndexManager;
import java.util.*;

public class Grid {
    public transient RTree<Map.Entry<GridPosition, Set<Integer>>, Geometry> gridOptimized = null;
    private transient Set<Integer> nearbyGroups = new HashSet<>(100);

    public Map<GridPosition, Set<Integer>> grid = new LinkedHashMap<>();

    /** Creates an optimized cache of the grid. */
    private void optimize() {
        if (this.gridOptimized == null) {
            var gridValues = new ArrayList<Map.Entry<GridPosition, Set<Integer>>>();
            this.grid.forEach((k, v) -> gridValues.add(new AbstractMap.SimpleEntry<>(k, v)));
            this.gridOptimized =
                    SceneIndexManager.buildIndex(2, gridValues, entry -> entry.getKey().toPoint());
        }
    }

    /**
     * @return The correctly loaded grid map.
     */
    public Map<GridPosition, Set<Integer>> getGrid() {
        return this.grid;
    }

    public Set<Integer> getNearbyGroups(int vision_level, Position position) {
        this.optimize(); // Check to see if the grid is optimized.

        int width = Grasscutter.getConfig().server.game.visionOptions[vision_level].gridWidth;
        int vision_range = Grasscutter.getConfig().server.game.visionOptions[vision_level].visionRange;
        int vision_range_grid = vision_range / width;

        GridPosition pos = new GridPosition(position, width);

        this.nearbyGroups.clear();
        // construct a nearby position list, add 1 more because a player can be in an edge case, this
        // should not affect much the loading
        // var nearbyGroups = new HashSet<Integer>();
        // for (int x = 0; x < vision_range_grid + 1; x++) {
        //     for (int z = 0; z < vision_range_grid + 1; z++) {
        //         nearbyGroups.addAll(gridMap.getOrDefault(pos.addClone(x, z), new HashSet<>()));
        //         nearbyGroups.addAll(gridMap.getOrDefault(pos.addClone(-x, z), new HashSet<>()));
        //         nearbyGroups.addAll(gridMap.getOrDefault(pos.addClone(x, -z), new HashSet<>()));
        //         nearbyGroups.addAll(gridMap.getOrDefault(pos.addClone(-x, -z), new HashSet<>()));
        //     }
        // }

        // Construct a list of nearby groups.
        SceneIndexManager.queryNeighbors(gridOptimized, pos.toDoubleArray(), vision_range_grid + 1)
                .forEach(e -> nearbyGroups.addAll(e.getValue()));
        return this.nearbyGroups;
    }
}
