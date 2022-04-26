package emu.grasscutter.server.event.game;

import emu.grasscutter.server.event.ServerEvent;

public final class ServerTickEvent extends ServerEvent {
    public ServerTickEvent() {
        super(Type.GAME);
    }
}
