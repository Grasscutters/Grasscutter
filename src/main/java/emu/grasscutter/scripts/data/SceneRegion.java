package emu.grasscutter.scripts.data;

import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.utils.Position;
import lombok.Setter;


@Setter
public class SceneRegion {
	public int config_id;
	public int shape;
	public Position pos;
    // for CUBIC
	public Position size;
    // for SPHERE
    public int radius;

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
				return x + y + z <= (radius ^ 2);
		}
		return false;
	}

}
