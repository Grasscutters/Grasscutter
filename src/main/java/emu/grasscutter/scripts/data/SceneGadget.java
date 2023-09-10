package emu.grasscutter.scripts.data;

import lombok.*;

@ToString
@Setter
public class SceneGadget extends SceneObject {
    public int gadget_id;
    public int chest_drop_id;
    public int drop_id;
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
     * Note: this field indicates whether the gadget should disappear permanently. For example, if
     * isOneOff=true, like most chests, it will disappear permanently after interacted. If
     * isOneOff=false, like investigation points, it will disappear temporarily, and appear again in
     * next big world resource refresh routine.
     */
    public boolean isOneoff;

    public int draft_id;
    public int route_id;
    public boolean start_route = true;
    public boolean is_use_point_array = false;
    public boolean persistent = false;
    public int mark_flag;
    public Explore explore;
    public int trigger_count;

    public void setIsOneoff(boolean isOneoff) {
        this.isOneoff = isOneoff;
    }
}
