package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import java.util.*;
import lombok.Data;

@Data
public class ScriptSceneData {
    Map<String, ScriptObject> scriptObjectList;

    @Data
    public static class ScriptObject {
        // private SceneGroup groups;
        @SerializedName("dummy_points")
        private Map<String, List<Float>> dummyPoints;
    }
}
