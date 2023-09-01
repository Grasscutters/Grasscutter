package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetUpLunchBoxWidgetReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetUpLunchBoxWidgetRsp;

@Opcodes(PacketOpcodes.SetUpLunchBoxWidgetReq)
public class HandlerSetUpLunchBoxWidgetReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SetUpLunchBoxWidgetReqOuterClass.SetUpLunchBoxWidgetReq.parseFrom(payload);

        session.send(new PacketSetUpLunchBoxWidgetRsp(req.getLunchBoxData()));
    }
}
