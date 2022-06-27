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

    public void setIsOneoff(boolean isOneoff){
        this.isOneoff = isOneoff;
    }
}
