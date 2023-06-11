package emu.grasscutter.scripts.data;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
@EqualsAndHashCode
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

    @EqualsAndHashCode.Exclude public transient SceneGroup group;

    /**
     * @return The group ID for this region.
     */
    public int getGroupId() {
        return this.group == null ? -1 : this.group.id;
    }

    public boolean contains(Position position) {
        switch (shape) {
            case ScriptRegionShape.CUBIC:
                return (Math.abs(pos.getX() - position.getX()) <= size.getX() / 2f)
                        && (Math.abs(pos.getY() - position.getY()) <= size.getY() / 2f)
                        && (Math.abs(pos.getZ() - position.getZ()) <= size.getZ() / 2f);
            case ScriptRegionShape.SPHERE:
                var x = Math.pow(pos.getX() - position.getX(), 2);
                var y = Math.pow(pos.getY() - position.getY(), 2);
                var z = Math.pow(pos.getZ() - position.getZ(), 2);
                // ^ means XOR in java!
                return x + y + z <= (radius * radius);
        }
        return false;
    }
}
