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

    public SpawnDataEntryScaledPoint getSpawnDataEntryScaledPoint(){
        return new SpawnDataEntryScaledPoint(group.sceneId,
            (int)(pos.getX() / GameDepot.BLOCK_SIZE),
            (int)(pos.getZ() / GameDepot.BLOCK_SIZE)
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

    public static class SpawnDataEntryScaledPoint{
        int sceneId;
        int x;
        int z;
        public SpawnDataEntryScaledPoint(int sceneId, int x, int z) {
            this.sceneId = sceneId;
            this.x = x;
            this.z = z;
        }
        @Override
        public String toString() {
            return "SpawnDataEntryScaledPoint{" +
                "sceneId=" + sceneId +
                ", x=" + x +
                ", z=" + z +
                '}';
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SpawnDataEntryScaledPoint that = (SpawnDataEntryScaledPoint) o;
            return sceneId == that.sceneId && x == that.x && z == that.z;
        }
        @Override
        public int hashCode() {
            return Objects.hash(sceneId, x, z);
        }
        public static SpawnDataEntryScaledPoint[] getAdjacentPointsIncludePosition(int sceneId, Position pos){
            SpawnDataEntryScaledPoint[] results = new SpawnDataEntryScaledPoint[9];
            int t=0;
            int x = ((int)(pos.getX()/ GameDepot.BLOCK_SIZE));
            int z = ((int)(pos.getZ()/GameDepot.BLOCK_SIZE));
            for(int i=x-1; i<x+2; i++){ //  the i-1/j-1 will be -1,0,-1
                for(int j=z-1; j<z+2; j++){
                    results[t++] = new SpawnDataEntryScaledPoint(sceneId,
                        x+i,
                        z+j);
                }
            }
            return results;
        }
    }
}
