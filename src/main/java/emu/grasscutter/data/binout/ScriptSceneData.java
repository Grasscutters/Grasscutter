package emu.grasscutter.data.binout;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.scripts.data.SceneGroup;
import emu.grasscutter.utils.Position;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScriptSceneData {
    Map<String,ScriptObject> scriptObjectList;

    @Data
    public static class ScriptObject {
        //private SceneGroup groups;
        @SerializedName("dummy_points")
        private Map<String, List<Float>> dummyPoints;


    }
}
