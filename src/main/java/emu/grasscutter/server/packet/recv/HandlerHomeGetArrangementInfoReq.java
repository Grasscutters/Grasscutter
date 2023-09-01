package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeGetArrangementInfoReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeGetArrangementInfoRsp;

@Opcodes(PacketOpcodes.HomeGetArrangementInfoReq)
public class HandlerHomeGetArrangementInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeGetArrangementInfoReqOuterClass.HomeGetArrangementInfoReq.parseFrom(payload);

        session.send(
                new PacketHomeGetArrangementInfoRsp(session.getPlayer(), req.getSceneIdListList()));
    }
}
