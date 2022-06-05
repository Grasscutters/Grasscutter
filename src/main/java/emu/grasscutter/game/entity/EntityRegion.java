package emu.grasscutter.game.entity;

import emu.grasscutter.game.props.EntityIdType;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.SceneEntityInfoOuterClass;
import emu.grasscutter.scripts.data.SceneRegion;
import emu.grasscutter.utils.Position;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

@Getter
public class EntityRegion extends GameEntity{
    private final Position position;
    private boolean hasNewEntities;
    private final Int2ObjectMap<GameEntity> entities; // Ids of entities inside this region
    private final SceneRegion metaRegion;
    public EntityRegion(Scene scene, SceneRegion region) {
        super(scene);
        this.id = getScene().getWorld().getNextEntityId(EntityIdType.REGION);
        setGroupId(region.group.id);
        setBlockId(region.group.block_id);
        setConfigId(region.config_id);
        this.position = region.pos.clone();
        this.entities = new Int2ObjectOpenHashMap<>();
        this.metaRegion = region;
    }

    public void addEntity(GameEntity entity) {
        if (this.getEntities().containsKey(entity.getId())) {
            return;
        }
        this.getEntities().put(entity.getId(), entity);
        this.hasNewEntities = true;
    }

    public boolean hasNewEntities() {
        return hasNewEntities;
    }

    public void resetNewEntities() {
        hasNewEntities = false;
    }

    public void removeEntity(GameEntity entity) {
        this.getEntities().remove(entity.getId());
    }

    @Override
    public Int2FloatOpenHashMap getFightProperties() {
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
        /**
         * The Region Entity would not be sent to client.
         */
        return null;
    }
}
