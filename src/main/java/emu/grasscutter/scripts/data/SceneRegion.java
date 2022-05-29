package emu.grasscutter.scripts.data;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneRegion {
	public int config_id;
	public int shape;
	public Position pos;
	public Position size;
	public int radius;
	
	private boolean hasNewEntities;
	private final IntSet entities; // Ids of entities inside this region
	
	public SceneRegion() {
		this.entities = new IntOpenHashSet();
	}
	
	public IntSet getEntities() {
		return entities;
	}

	public void addEntity(GameEntity entity) {
		if (this.getEntities().contains(entity.getId())) {
			return;
		}
		this.getEntities().add(entity.getId());
		this.hasNewEntities = true;
	}
	
	public void removeEntity(GameEntity entity) {
		this.getEntities().remove(entity.getId());
	}
	
	public boolean contains(Position p) {
		switch (shape) {
			case ScriptRegionShape.CUBIC:
				return (Math.abs(pos.getX() - p.getX()) <= size.getX()) &&
				       (Math.abs(pos.getZ() - p.getZ()) <= size.getZ());
			case ScriptRegionShape.SPHERE:
				double x1 = Math.pow(p.getX() - pos.getX(), 2);
				double y1 = Math.pow(p.getY() - pos.getY(), 2);
				double z1 = Math.pow(p.getZ() - pos.getZ(), 2);
				if((x1+y1+z1)<radius*radius){
					return true;
				}
				return false;
		}
		
		return false;
	}

	public boolean hasNewEntities() {
		return hasNewEntities;
	}
	
	public void resetNewEntities() {
		hasNewEntities = false;
	}
}
