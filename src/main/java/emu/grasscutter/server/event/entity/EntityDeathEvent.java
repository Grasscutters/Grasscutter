package emu.grasscutter.server.event.entity;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.world.Location;
import emu.grasscutter.server.event.types.EntityEvent;
import javax.annotation.Nullable;
import lombok.Getter;

public final class EntityDeathEvent extends EntityEvent {
    @Getter private final Location deathLocation;
    @Getter @Nullable private final GameEntity killer;

    public EntityDeathEvent(GameEntity entity, int killerId) {
        super(entity);

        this.deathLocation = new Location(entity.getScene(), entity.getPosition());
        this.killer = entity.getScene().getEntityById(killerId);
    }
}
