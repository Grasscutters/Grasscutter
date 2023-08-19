package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ReformFireworksReqOuterClass.ReformFireworksReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.ReformFireworksReq)
public final class HandlerReformFireworksReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = ReformFireworksReq.parseFrom(payload);

        session.send(new PacketFireworksReformDataNotify(req.getFireworksReformData()));
        session.send(new PacketFireworkSetRsp());
    }
}
