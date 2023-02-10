package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneGadget extends SceneObject{
    public int gadget_id;
    public int state;
    public int point_type;
    public SceneBossChest boss_chest;
    public int interact_id;
    public boolean isOneoff;
    public int draft_id;
    public String drop_tag;
    public boolean persistent;
    public int mark_flag;
    public int route_id;
    public Explore explore;
    public int trigger_count;
    public boolean showcutscene;

    public void setIsOneoff(boolean isOneoff) {
        this.isOneoff = isOneoff;
    }
}
