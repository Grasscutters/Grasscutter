package emu.grasscutter.server.event.entity;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.proto.MotionStateOuterClass.MotionState;
import emu.grasscutter.server.event.types.EntityEvent;

public final class EntityMoveEvent extends EntityEvent {
    private final Position position, rotation;
    private final MotionState motionState;

    public EntityMoveEvent(
            GameEntity entity, Position position, Position rotation, MotionState motionState) {
        super(entity);

        this.position = position;
        this.rotation = rotation;
        this.motionState = motionState;
    }

    public Position getPosition() {
        return this.position;
    }

    public Position getRotation() {
        return this.rotation;
    }

    public MotionState getMotionState() {
        return this.motionState;
    }
}
