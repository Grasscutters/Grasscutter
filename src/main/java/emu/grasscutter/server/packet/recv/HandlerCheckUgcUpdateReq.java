package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CheckUgcUpdateReqOuterClass.CheckUgcUpdateReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCheckUgcUpdateRsp;

@Opcodes(PacketOpcodes.CheckUgcUpdateReq)
public class HandlerCheckUgcUpdateReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = CheckUgcUpdateReq.parseFrom(payload);

        session.send(new PacketCheckUgcUpdateRsp(req.getUgcType()));
    }
}
