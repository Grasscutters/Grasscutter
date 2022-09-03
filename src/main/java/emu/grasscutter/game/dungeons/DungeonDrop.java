package emu.grasscutter.game.dungeons;

import java.util.List;

public class DungeonDrop {
	private int dungeonId;
	private List<DungeonDropEntry> drops;

	public int getDungeonId() {
		return dungeonId;
	}
	public void setDungeonId(int dungeonId) {
		this.dungeonId = dungeonId;
	}
	
	public List<DungeonDropEntry> getDrops() {
		return drops;
	}
	public void setDrops(List<DungeonDropEntry> drops) {
		this.drops = drops;
	}
}
