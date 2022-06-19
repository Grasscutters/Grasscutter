package emu.grasscutter.data.common;

import com.google.gson.JsonObject;

public class ScenePointConfig {
    public JsonObject points;

    public JsonObject getPoints() {
        return this.points;
    }

    public void setPoints(JsonObject Points) {
        this.points = Points;
    }
}
