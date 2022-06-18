package emu.grasscutter.scripts.data;

import emu.grasscutter.utils.Position;

import java.util.List;

public class SceneBlock {
    public int id;
    public Position max;
    public Position min;
    public List<SceneGroup> groups;

    public boolean contains(Position pos) {
        return pos.getX() <= this.max.getX() && pos.getX() >= this.min.getX() &&
            pos.getZ() <= this.max.getZ() && pos.getZ() >= this.min.getZ();
    }
}
