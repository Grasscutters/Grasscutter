package emu.grasscutter.scripts.data;

import emu.grasscutter.utils.Position;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneObject {
    public int level;
    public int config_id;

    public Position pos;
    public Position rot;
    /**
     * not set by lua
     */
    public transient int groupId;
}
