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

    public BlockId getBlockId(){
        int scale = BlockId.getScale(gadgetId);
        return new BlockId(group.sceneId,scale,
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

    public static class BlockId {
        int sceneId;
        int x;
        int z;
        int scale;

        public BlockId(int sceneId, int scale, int x, int z) {
            this.sceneId = sceneId;
            this.x = x;
            this.z = z;
            this.scale = scale;
        }

        @Override
        public String toString() {
            return "SpawnDataEntryScaledPoint{" +
                "sceneId=" + sceneId +
                ", x=" + x +
                ", z=" + z +
                ", scale=" + scale +
                '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockId that = (BlockId) o;
            return sceneId == that.sceneId && x == that.x && z == that.z && scale == that.scale;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sceneId, x, z,scale);
        }
        public static BlockId[] getBlockIdsIncludeCenter(int sceneId, Position pos){
            BlockId[] results = new BlockId[9*GameDepot.BLOCK_SIZE.length];
            for(int s:GameDepot.BLOCK_SIZE){
                int t=0;
                int x = ((int)(pos.getX()/ GameDepot.BLOCK_SIZE[s]));
                int z = ((int)(pos.getZ()/GameDepot.BLOCK_SIZE[s]));
                for(int i=x-1; i<x+2; i++){ //  the i-1/j-1 will be -1,0,-1
                    for(int j=z-1; j<z+2; j++){
                        results[t++] = new BlockId(sceneId,s,
                            i,
                            j);
                    }
                }
            }
            return results;
        }
        public static int getScale(int gadgetId){
            return 0;//you should implement here,this is index of GameDepot.BLOCK_SIZE
        }
    }
}
