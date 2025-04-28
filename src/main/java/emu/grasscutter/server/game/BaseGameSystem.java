package emu.grasscutter.server.game;

import lombok.Getter;

@Getter
public abstract class BaseGameSystem {
    protected final GameServer server;

    public BaseGameSystem(GameServer server) {
        this.server = server;
    }
}
