package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarExpeditionAllDataRsp;

@Opcodes(PacketOpcodes.AvatarExpeditionAllDataReq)
public class HandlerAvatarExpeditionAllDataReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var player = session.getPlayer();
        session.send(
                new PacketAvatarExpeditionAllDataRsp(
                        player.getExpeditionInfo(), player.getExpeditionLimit()));
    }
}
