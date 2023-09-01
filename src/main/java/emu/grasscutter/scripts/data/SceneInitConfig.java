package emu.grasscutter.scripts.data;

import lombok.*;

@ToString
@Setter
public final class SceneInitConfig {
    public int suite;
    public int end_suite;
    public int io_type;
    public boolean rand_suite;
}
