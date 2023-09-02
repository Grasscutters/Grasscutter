package emu.grasscutter.server.event.entity;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.EntityEvent;
import javax.annotation.Nullable;
import lombok.*;

public final class EntityDamageEvent extends EntityEvent implements Cancellable {
    @Getter @Setter private float damage;
    @Getter @Setter private ElementType attackElementType;
    @Getter @Nullable private final GameEntity damager;

    public EntityDamageEvent(
            GameEntity entity,
            float damage,
            ElementType attackElementType,
            @Nullable GameEntity damager) {
        super(entity);

        this.damage = damage;
        this.attackElementType = attackElementType;
        this.damager = damager;
    }
}
