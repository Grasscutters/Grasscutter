package emu.grasscutter.data.binout;

import emu.grasscutter.data.common.PointData;

public class ScenePointEntry {
	private String name;
	private PointData pointData;
	
	public ScenePointEntry(String name, PointData pointData) {
		this.name = name;
		this.pointData = pointData;
	}

	public String getName() {
		return name;
	}

	public PointData getPointData() {
        return pointData;
    }
}
