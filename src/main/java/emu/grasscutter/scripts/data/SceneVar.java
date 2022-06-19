package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneVar {
    public String name;
    public int value;
    public boolean no_refresh;
}
