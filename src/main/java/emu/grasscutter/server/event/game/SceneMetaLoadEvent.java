package emu.grasscutter.server.event.game;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.event.types.ServerEvent;
import lombok.*;

@Getter
public final class SceneMetaLoadEvent extends ServerEvent {
    private final Scene scene;
    @Setter private boolean override;

    public SceneMetaLoadEvent(Scene scene) {
        super(Type.GAME);

        this.scene = scene;
    }
}
