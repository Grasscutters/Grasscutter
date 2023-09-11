package emu.grasscutter.scripts.data;

import emu.grasscutter.game.world.Position;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import java.util.List;
import lombok.*;

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
            case ScriptRegionShape.SPHERE -> {
                val x = pos.getX() - position.getX();
                val y = pos.getY() - position.getY();
                val z = pos.getZ() - position.getZ();
                // x^2 + y^2 + z^2 = radius^2
                return x * x + y * y + z * z <= radius * radius;
            }
            case ScriptRegionShape.CUBIC -> {
                return (Math.abs(pos.getX() - position.getX()) <= size.getX() / 2f)
                        && (Math.abs(pos.getY() - position.getY()) <= size.getY() / 2f)
                        && (Math.abs(pos.getZ() - position.getZ()) <= size.getZ() / 2f);
            }
            case ScriptRegionShape.POLYGON -> {
                // algorithm is "ray casting": https://www.youtube.com/watch?v=RSXM9bgqxJM
                if (Math.abs(pos.getY() - position.getY()) > height / 2f) return false;
                var count = 0;
                for (var i = 0; i < point_array.size(); ++i) {
                    val j = (i + 1) % point_array.size();

                    val yp = position.getZ();
                    val y1 = point_array.get(i).getY();
                    val y2 = point_array.get(j).getY();

                    val xp = position.getX();
                    val x1 = point_array.get(i).getX();
                    val x2 = point_array.get(j).getX();

                    if ((yp < y1) != (yp < y2) && xp < x1 + ((yp - y1) / (y2 - y1)) * (x2 - x1)) {
                        ++count;
                    }
                }

                return count % 2 == 1;
            }
            case ScriptRegionShape.CYLINDER -> {
                if (Math.abs(pos.getY() - position.getY()) > height / 2f) return false;
                val x = pos.getX() - position.getX();
                val z = pos.getZ() - position.getZ();
                // x^2 + z^2 = radius^2
                return x * x + z * z <= radius * radius;
            }
        }
        return false;
    }
}
