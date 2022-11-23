package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneGadget extends SceneObject{
    public int config_id;
    public int gadget_id;
    public int level;
    public int chest_drop_id;
    public int drop_count;
    public String drop_tag;
    boolean showcutscene;
    boolean persistence;
    public int state;

    public int point_type;
    public int owner;
    public SceneBossChest boss_chest;
    public int interact_id;
    /**
     * Note: this field indicates whether the gadget should disappear permanently.
     * For example, if isOneOff=true, like most chests, it will disappear permanently after interacted.
     * If isOneOff=false, like investigation points, it will disappear temporarily, and appear again in next big world resource refresh routine.
     */
    public boolean isOneoff;
    public int area_id;
    public int draft_id;

    public void setIsOneoff(boolean isOneoff) {
        this.isOneoff = isOneoff;
    }
}
