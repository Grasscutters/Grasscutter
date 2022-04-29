package emu.grasscutter.scripts.data;

import java.util.List;

import emu.grasscutter.utils.Position;

public class SceneBlock {
	public int id;
	public Position max;
	public Position min;
	public List<SceneGroup> groups;
	
	public boolean contains(Position pos) {
		return 	pos.getX() <= max.getX() && pos.getX() >= min.getX() &&
				pos.getZ() <= max.getZ() && pos.getZ() >= min.getZ();
	}
}
