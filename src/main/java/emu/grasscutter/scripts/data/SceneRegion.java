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
    public SceneGroup group;

    private boolean hasNewEntities;

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
}
