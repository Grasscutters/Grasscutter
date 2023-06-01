package emu.grasscutter.server.event.game;

import emu.grasscutter.server.event.types.ServerEvent;
import java.time.Instant;

public final class ServerTickEvent extends ServerEvent {
    private final Instant start, end;

    public ServerTickEvent(Instant start, Instant end) {
        super(Type.GAME);

        this.start = start;
        this.end = end;
    }

    public Instant getTickStart() {
        return this.start;
    }

    public Instant getTickEnd() {
        return this.end;
    }
}
