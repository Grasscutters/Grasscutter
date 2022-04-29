package emu.grasscutter.data.common;

import emu.grasscutter.utils.Position;

public class PointData {
	private int id;
	private String $type;
    private Position tranPos;
    private int[] dungeonIds;
    
    public int getId() {
		return id;
	}
    
    public void setId(int id) {
		this.id = id;
	}

    public String getType() {
		return $type;
	}

	public Position getTranPos() {
        return tranPos;
    }

	public int[] getDungeonIds() {
		return dungeonIds;
	}
}
