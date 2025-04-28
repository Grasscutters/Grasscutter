package emu.grasscutter.server.event.types;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.server.event.Event;
import lombok.Getter;

/** An event that is related to entity interactions. */
@Getter
public abstract class EntityEvent extends Event {
    protected final GameEntity entity;

    public EntityEvent(GameEntity entity) {
        this.entity = entity;
    }
}
