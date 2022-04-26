package emu.grasscutter.game.world;

import java.util.ArrayList;
import java.util.List;

import emu.grasscutter.utils.Position;

public class SpawnDataEntry {
	private transient SpawnGroupEntry group;
	private int monsterId;
	private int configId;
	private int level;
	private int poseId;
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
	
	public int getConfigId() {
		return configId;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getPoseId() {
		return poseId;
	}
	
	public Position getPos() {
		return pos;
	}
	
	public Position getRot() {
		return rot;
	}
	
	public static class SpawnGroupEntry {
		private int sceneId;
		private int groupId;
		private int blockId;
		private Position pos;
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

		public Position getPos() {
			return pos;
		}

		public List<SpawnDataEntry> getSpawns() {
			return spawns;
		}
	}
}
