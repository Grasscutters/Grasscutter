package emu.grasscutter.game.world;

import emu.grasscutter.data.GameDepot;
import java.util.*;
import lombok.*;

public class SpawnDataEntry {
    @Getter @Setter private transient SpawnGroupEntry group;
    @Getter private int monsterId;
    @Getter private int gadgetId;
    @Getter private int configId;
    @Getter private int level;
    @Getter private int poseId;
    @Getter private int gatherItemId;
    @Getter private int gadgetState;
    @Getter private Position pos;
    @Getter private Position rot;

    public GridBlockId getBlockId() {
        int scale = GridBlockId.getScale(gadgetId);
        return new GridBlockId(
                group.sceneId,
                scale,
                (int) (pos.getX() / GameDepot.BLOCK_SIZE[scale]),
                (int) (pos.getZ() / GameDepot.BLOCK_SIZE[scale]));
    }

    public static class SpawnGroupEntry {
        @Getter private int sceneId;
        @Getter private int groupId;
        @Getter private int blockId;
        @Getter @Setter private List<SpawnDataEntry> spawns;
    }

    public static class GridBlockId {
        @Getter private int sceneId;
        @Getter private int scale;
        @Getter private int x;
        @Getter private int z;

        public GridBlockId(int sceneId, int scale, int x, int z) {
            this.sceneId = sceneId;
            this.scale = scale;
            this.x = x;
            this.z = z;
        }

        public static GridBlockId[] getAdjacentGridBlockIds(int sceneId, Position pos) {
            GridBlockId[] results = new GridBlockId[5 * 5 * GameDepot.BLOCK_SIZE.length];
            int t = 0;
            for (int scale = 0; scale < GameDepot.BLOCK_SIZE.length; scale++) {
                int x = ((int) (pos.getX() / GameDepot.BLOCK_SIZE[scale]));
                int z = ((int) (pos.getZ() / GameDepot.BLOCK_SIZE[scale]));
                for (int i = x - 2; i < x + 3; i++) {
                    for (int j = z - 2; j < z + 3; j++) {
                        results[t++] = new GridBlockId(sceneId, scale, i, j);
                    }
                }
            }
            return results;
        }

        public static int getScale(int gadgetId) {
            return 0; // you should implement here,this is index of GameDepot.BLOCK_SIZE
        }

        @Override
        public String toString() {
            return "SpawnDataEntryScaledPoint{"
                    + "sceneId="
                    + sceneId
                    + ", scale="
                    + scale
                    + ", x="
                    + x
                    + ", z="
                    + z
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GridBlockId that = (GridBlockId) o;
            return sceneId == that.sceneId && scale == that.scale && x == that.x && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sceneId, scale, x, z);
        }
    }
}
