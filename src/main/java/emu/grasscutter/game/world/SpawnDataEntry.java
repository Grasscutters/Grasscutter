package emu.grasscutter.game.world;

import java.util.List;
import java.util.Objects;

import emu.grasscutter.data.GameDepot;
import emu.grasscutter.utils.Position;

public class SpawnDataEntry {
    private transient SpawnGroupEntry group;
    private int monsterId;
    private int gadgetId;
    private int configId;
    private int level;
    private int poseId;
    private int gatherItemId;
    private int gadgetState;
    private Position pos;
    private Position rot;

    public SpawnGroupEntry getGroup() {
        return group;
    }

    public void setGroup(SpawnGroupEntry group) {
        this.group = group;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public int getGadgetId() {
        return gadgetId;
    }

    public int getGadgetState() {
        return gadgetState;
    }

    public int getConfigId() {
        return configId;
    }

    public int getLevel() {
        return level;
    }

    public int getPoseId() {
        return poseId;
    }

    public int getGatherItemId() {
        return gatherItemId;
    }

    public Position getPos() {
        return pos;
    }

    public Position getRot() {
        return rot;
    }

    public GridBlockId getBlockId() {
        int scale = GridBlockId.getScale(gadgetId);
        return new GridBlockId(group.sceneId,scale,
            (int)(pos.getX() / GameDepot.BLOCK_SIZE[scale]),
            (int)(pos.getZ() / GameDepot.BLOCK_SIZE[scale])
        );
    }

    public static class SpawnGroupEntry {
        private int sceneId;
        private int groupId;
        private int blockId;
        private List<SpawnDataEntry> spawns;

        public int getSceneId() {
            return sceneId;
        }

        public int getGroupId() {
            return groupId;
        }

        public int getBlockId() {
            return blockId;
        }

        public void setBlockId(int blockId) {
            this.blockId = blockId;
        }

        public List<SpawnDataEntry> getSpawns() {
            return spawns;
        }
    }

    public static class GridBlockId {
        int sceneId;
        int scale;
        int x;
        int z;

        public GridBlockId(int sceneId, int scale, int x, int z) {
            this.sceneId = sceneId;
            this.scale = scale;
            this.x = x;
            this.z = z;
        }

        @Override
        public String toString() {
            return "SpawnDataEntryScaledPoint{" +
                "sceneId=" + sceneId +
                ", scale=" + scale +
                ", x=" + x +
                ", z=" + z +
                '}';
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

        public static GridBlockId[] getAdjacentGridBlockIds(int sceneId, Position pos) {
            GridBlockId[] results = new GridBlockId[5*5*GameDepot.BLOCK_SIZE.length];
            int t=0;
            for (int scale = 0; scale < GameDepot.BLOCK_SIZE.length; scale++) {
                int x = ((int)(pos.getX()/GameDepot.BLOCK_SIZE[scale]));
                int z = ((int)(pos.getZ()/GameDepot.BLOCK_SIZE[scale]));
                for (int i=x-2; i<x+3; i++) {
                    for (int j=z-2; j<z+3; j++) {
                        results[t++] = new GridBlockId(sceneId, scale, i, j);
                    }
                }
            }
            return results;
        }

        public static int getScale(int gadgetId) {
            return 0;//you should implement here,this is index of GameDepot.BLOCK_SIZE
        }
    }
}
