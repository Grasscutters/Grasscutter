package emu.grasscutter.server.event.game;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.server.event.Cancellable;
import emu.grasscutter.server.event.ServerEvent;
import emu.grasscutter.server.game.GameSession;

public final class SendPacketEvent extends ServerEvent implements Cancellable {
    private final GameSession gameSession;
    private GenshinPacket packet;

    public SendPacketEvent(GameSession gameSession, GenshinPacket packet) {
        super(Type.GAME);

        this.gameSession = gameSession;
        this.packet = packet;
    }

    public GameSession getGameSession() {
        return this.gameSession;
    }

    public void setPacket(GenshinPacket packet) {
        this.packet = packet;
    }

    public GenshinPacket getPacket() {
        return this.packet;
    }
}
