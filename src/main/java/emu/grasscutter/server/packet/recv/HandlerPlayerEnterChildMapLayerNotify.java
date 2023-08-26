package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerEnterChildMapLayerNotify)
public final class HandlerPlayerEnterChildMapLayerNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // var packet = PlayerEnterChildMapLayerNotify.parseFrom(payload);
        // probably
        // Grasscutter.getLogger()
        //         .info("player entered child map layer: id {}", packet.getLayerId());
    }
}
