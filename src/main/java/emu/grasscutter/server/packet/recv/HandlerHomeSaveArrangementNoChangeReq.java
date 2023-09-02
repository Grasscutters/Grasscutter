package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeSaveArrangementNoChangeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeSaveArrangementNoChangeRsp;

@Opcodes(PacketOpcodes.HomeSaveArrangementNoChangeReq)
public class HandlerHomeSaveArrangementNoChangeReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.send(
                new PacketHomeSaveArrangementNoChangeRsp(
                        HomeSaveArrangementNoChangeReqOuterClass.HomeSaveArrangementNoChangeReq.parseFrom(
                                        payload)
                                .getSceneId()));
    }
}
