package emu.grasscutter.game.entity;

import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.*;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass;
import emu.grasscutter.scripts.data.SceneRegion;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public class EntityRegion extends GameEntity {
    private final Position position;
    private final Set<Integer> entities; // Ids of entities inside this region
    private final SceneRegion metaRegion;
    private boolean entityEnter;
    private boolean entityLeave;

    public EntityRegion(Scene scene, SceneRegion region) {
        super(scene);

        this.id = getScene().getWorld().getNextEntityId(EntityIdType.REGION);
        this.setGroupId(region.group.id);
        this.setBlockId(region.group.block_id);
        this.setConfigId(region.config_id);
        this.position = region.pos.clone();
        this.entities = ConcurrentHashMap.newKeySet();
        this.metaRegion = region;
    }

    @Override
    public int getEntityTypeId() {
        return this.metaRegion.config_id;
    }

    public void resetNewEntities() {
        this.entityEnter = false;
        this.entityLeave = false;
    }

    public void addEntity(GameEntity entity) {
        if (this.getEntities().contains(entity.getId())) {
            return;
        }

        this.getEntities().add(entity.getId());
        this.entityEnter = true;
    }

    public void removeEntity(int entityId) {
        this.getEntities().remove(entityId);
        this.entityLeave = true;
    }

    public void removeEntity(GameEntity entity) {
        this.removeEntity(entity.getId());
    }

    public boolean entityHasEntered() {
        return this.entityEnter;
    }

    public boolean entityHasLeft() {
        return this.entityLeave;
    }

    @Override
    public Int2FloatMap getFightProperties() {
        return null;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Position getRotation() {
        return null;
    }

    @Override
    public SceneEntityInfoOuterClass.SceneEntityInfo toProto() {
        /** The Region Entity would not be sent to client. */
        return null;
    }

    public int getFirstEntityId() {
        return entities.stream().findFirst().orElse(0);
    }

    @Override
    public void initAbilities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initAbilities'");
    }
}
