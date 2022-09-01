package emu.grasscutter.scripts;

import emu.grasscutter.scripts.data.SceneGroup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScriptLibContext {
    SceneScriptManager sceneScriptManager;
    SceneGroup currentGroup;
    public int uid;
}
