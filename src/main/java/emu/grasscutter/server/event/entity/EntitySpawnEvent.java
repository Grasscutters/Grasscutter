package emu.grasscutter.server.event.entity;

import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.types.EntityEvent;
import org.jetbrains.annotations.Nullable;

public class EntitySpawnEvent extends EntityEvent implements Cancellable {

    private Player player;

    public EntitySpawnEvent(GameEntity entity, @Nullable Player player) {
        super(entity);

        this.player = player;
    }

    @Nullable
    public Player getPlayer() {
        return player;
    }

    @Override
    public GameEntity getEntity() {
        return super.getEntity();
    }
}
