package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneMonster extends SceneObject {
    public int config_id;
    public int monster_id;
    public int pose_id;
    public int level;
    public int drop_id;
    public boolean disableWander;
    public int title_id;
    public int special_name_id;
    public String drop_tag;
}
