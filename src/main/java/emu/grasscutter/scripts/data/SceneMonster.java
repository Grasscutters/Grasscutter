package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
public class SceneMonster extends SceneObject {
    public int monster_id;
    public int pose_id;
    public int drop_id;
    public boolean disableWander;
    public int title_id;
    public int special_name_id;
    public String drop_tag;
	public List<Integer> affix;
	public boolean isElite;
}
