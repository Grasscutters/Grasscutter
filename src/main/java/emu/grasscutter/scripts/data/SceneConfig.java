package emu.grasscutter.scripts.data;

import emu.grasscutter.game.world.Position;
import lombok.*;

@ToString
@Setter
public class SceneConfig {
    public Position vision_anchor;
    public Position born_pos;
    public Position born_rot;
    public Position begin_pos;
    public Position size;
    public float die_y;
}
