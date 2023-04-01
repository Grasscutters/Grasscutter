package emu.grasscutter.data.server;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.GridPosition;
import emu.grasscutter.utils.Position;

public class Grid {
    public Map<GridPosition, Set<Integer>> grid;

    public Set<Integer> getNearbyGroups(int vision_level, Position position) {
        int width = Grasscutter.getConfig().server.game.visionOptions[vision_level].gridWidth;
        int vision_range = Grasscutter.getConfig().server.game.visionOptions[vision_level].visionRange;
        int vision_range_grid = vision_range / width;

        GridPosition pos = new GridPosition(position, width);

        Set<Integer> nearbyGroups = new HashSet<>();
        //construct a nearby pisition list, add 1 more because a player can be in an edge case, this should not affect much the loading
        for(int x = 0; x < vision_range_grid + 1; x++) {
            for(int z = 0; z < vision_range_grid + 1; z++) {
                nearbyGroups.addAll(grid.getOrDefault(pos.addClone( x,  z), new HashSet<>()));
                nearbyGroups.addAll(grid.getOrDefault(pos.addClone(-x,  z), new HashSet<>()));
                nearbyGroups.addAll(grid.getOrDefault(pos.addClone( x, -z), new HashSet<>()));
                nearbyGroups.addAll(grid.getOrDefault(pos.addClone(-x, -z), new HashSet<>()));
            }
        }

        return nearbyGroups;
    }
}
