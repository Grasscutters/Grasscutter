package emu.grasscutter.scripts.data;

import lombok.*;

@ToString
@Setter
public class SceneVar {
    public String name;
    public int value;
    public boolean no_refresh;
    public int configId;
}
