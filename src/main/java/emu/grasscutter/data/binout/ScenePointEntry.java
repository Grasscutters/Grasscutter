package emu.grasscutter.data.binout;

import emu.grasscutter.data.common.PointData;

public class ScenePointEntry {
    private final String name;
    private final PointData pointData;

    public ScenePointEntry(String name, PointData pointData) {
        this.name = name;
        this.pointData = pointData;
    }

    public String getName() {
        return this.name;
    }

    public PointData getPointData() {
        return this.pointData;
    }
}
