package emu.grasscutter.scripts.data;

import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.utils.Position;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneRegion {
	public int config_id;
	public int shape;
	public Position pos;
	public Position size;
	public SceneGroup group;
	
	public SceneRegion() {

	}


	public boolean contains(Position p) {
		switch (shape) {
			case ScriptRegionShape.CUBIC:
				return (Math.abs(pos.getX() - p.getX()) <= size.getX()) &&
				       (Math.abs(pos.getZ() - p.getZ()) <= size.getZ());
			case ScriptRegionShape.SPHERE:
				return false;
		}
		
		return false;
	}


}
