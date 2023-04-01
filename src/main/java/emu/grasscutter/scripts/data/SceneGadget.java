package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneGadget extends SceneObject {
    public int gadget_id;
    public int state;
    public int point_type;
    public SceneBossChest boss_chest;
    public int interact_id;
    public boolean isOneoff;
    public int draft_id;
    public int route_id;
    public boolean start_route = true;
    public boolean is_use_point_array = false;
    public boolean persistent = false;

    public void setIsOneoff(boolean isOneoff) {
        this.isOneoff = isOneoff;
    }
}
