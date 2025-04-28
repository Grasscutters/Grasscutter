package emu.grasscutter.server.event.game;

import emu.grasscutter.scripts.data.SceneBlock;
import emu.grasscutter.server.event.types.ServerEvent;
import lombok.*;

@Getter
public final class SceneBlockLoadedEvent extends ServerEvent {
    private final SceneBlock block;

    public SceneBlockLoadedEvent(SceneBlock block) {
        super(Type.GAME);

        this.block = block;
    }
}
