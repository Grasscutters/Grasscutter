package emu.grasscutter.scripts.data;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneReplaceable {
    public boolean value;
    public int version;
    public boolean new_bin_only;
}
