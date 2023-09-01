package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QueryPathReqOuterClass.QueryPathReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQueryPathRsp;

@Opcodes(PacketOpcodes.QueryPathReq)
public class HandlerQueryPathReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = QueryPathReq.parseFrom(payload);

        /** It is not the actual work */
        if (req.getDestinationPosList().size() > 0) {
            session.send(new PacketQueryPathRsp(req));
        }
    }
}
