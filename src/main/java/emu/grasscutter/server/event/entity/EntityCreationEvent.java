package emu.grasscutter.server.event.entity;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.server.event.Event;
import javax.annotation.Nullable;
import lombok.*;

/** Invoked when an entity is created. */
@AllArgsConstructor
public final class EntityCreationEvent extends Event {
    /**
     * Helper method to call EntityCreationEvent. Returns the result of the event call.
     *
     * @param type The type of entity to create.
     * @return The result of the event call.
     * @param <T> The type of entity to create.
     */
    public static <T extends GameEntity> T call(Class<T> type, Class<?>[] argTypes, Object[] args) {
        var event = new EntityCreationEvent(type, argTypes, args);
        event.call();
        return type.cast(event.getEntity());
    }

    @Getter @Setter private Class<? extends GameEntity> entityType;
    @Getter @Setter private Class<?>[] constructorArgTypes;
    @Getter @Setter private Object[] constructorArgs;

    /**
     * Creates a new entity. Returns null if the entity could not be created.
     *
     * @return The created entity.
     */
    @Nullable public GameEntity getEntity() {
        try {
            return this.entityType
                    .getConstructor(this.constructorArgTypes)
                    .newInstance(this.constructorArgs);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
