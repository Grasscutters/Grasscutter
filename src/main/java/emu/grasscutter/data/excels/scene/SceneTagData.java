package emu.grasscutter.data.excels.scene;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "SceneTagConfigData.json")
@Getter
public final class SceneTagData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    @SerializedName("DJCOAOBDIHP")
    private boolean idk1;

    @SerializedName("LOLNNMPKHIB")
    private boolean idk2;

    private boolean isDefaultValid; // shld be there by default

    private String sceneTagName;
    private int sceneId;
    private List<SceneTagCondition> cond;

    @Getter
    public static class SceneTagCondition {
        private CondType condType;
        private int param1;
        private int param2;

        public enum CondType {
            SCENE_TAG_COND_TYPE_ACTIVITY_CONTENT_OPEN,
            SCENE_TAG_COND_TYPE_QUEST_FINISH,
            SCENE_TAG_COND_TYPE_QUEST_GLOBAL_VAR_EQUAL,
            SCENE_TAG_COND_TYPE_SPECIFIC_ACTIVITY_OPEN
        }
    }
}
