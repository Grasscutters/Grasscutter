package emu.grasscutter.data.common;

import com.google.gson.JsonObject;

public class ScenePointConfig {
    public JsonObject points;

    public JsonObject getPoints() {
        return points;
    }

    public void setPoints(JsonObject Points) {
        points = Points;
    }
}
