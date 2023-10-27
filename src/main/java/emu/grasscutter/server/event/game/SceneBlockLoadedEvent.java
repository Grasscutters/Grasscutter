package emu.grasscutter.server.event.game;

import emu.grasscutter.server.event.types.ServerEvent;
import emu.grasscutter.scripts.data.SceneBlock;

public final class SceneBlockLoadedEvent extends ServerEvent {
    public SceneBlock block;

    public SceneBlockLoadedEvent(SceneBlock block) {
        super(Type.GAME);

        this.block = block;
    }
}
