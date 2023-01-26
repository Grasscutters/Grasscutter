package emu.grasscutter.scripts.data;

import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.utils.Position;
import lombok.Setter;

import java.util.List;


@Setter
public class SceneRegion {
    public int config_id;
    public int shape;
    public Position pos;
    // for CUBIC
    public Position size;
    // for SPHERE
    public int radius;
    public int area_id;
    public float height;
    public List<Position> point_array;

    public transient SceneGroup group;
    public boolean contains(Position position) {
        switch (shape) {
            case ScriptRegionShape.CUBIC:
                return (Math.abs(pos.getX() - position.getX()) <= size.getX()) &&
                       (Math.abs(pos.getY() - position.getY()) <= size.getY()) &&
                       (Math.abs(pos.getZ() - position.getZ()) <= size.getZ());
            case ScriptRegionShape.SPHERE:
                var x = Math.pow(pos.getX() - position.getX(), 2);
                var y = Math.pow(pos.getY() - position.getY(), 2);
                var z = Math.pow(pos.getZ() - position.getZ(), 2);
                // ^ means XOR in java!
                return x + y + z <= (radius*radius);
        }
        return false;
    }

}
