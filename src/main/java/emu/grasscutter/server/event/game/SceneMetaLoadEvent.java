package emu.grasscutter.server.event.game;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.event.types.ServerEvent;

public final class SceneMetaLoadEvent extends ServerEvent {
    public Scene scene;
    public boolean hasOverride;

    public SceneMetaLoadEvent(Scene scene) {
        super(Type.GAME);

        this.scene = scene;
    }
}
