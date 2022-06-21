package emu.grasscutter.scripts.data;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.scripts.constants.ScriptRegionShape;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class SceneRegion {
    public int config_id;
    public int shape;
    public Position pos;
    public Position size;

    private boolean hasNewEntities;
    private final IntSet entities; // Ids of entities inside this region

    public SceneRegion() {
        this.entities = new IntOpenHashSet();
    }

    public IntSet getEntities() {
        return this.entities;
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
        switch (this.shape) {
            case ScriptRegionShape.CUBIC:
                return (Math.abs(this.pos.getX() - p.getX()) <= this.size.getX()) &&
                    (Math.abs(this.pos.getZ() - p.getZ()) <= this.size.getZ());
            case ScriptRegionShape.SPHERE:
                return false;
        }

        return false;
    }

    public boolean hasNewEntities() {
        return this.hasNewEntities;
    }

    public void resetNewEntities() {
        this.hasNewEntities = false;
    }
}
