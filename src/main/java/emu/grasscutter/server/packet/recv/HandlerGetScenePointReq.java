package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetScenePointReqOuterClass.GetScenePointReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetScenePointRsp;

@Opcodes(PacketOpcodes.GetScenePointReq)
public class HandlerGetScenePointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetScenePointReq req = GetScenePointReq.parseFrom(payload);

        session.send(new PacketGetScenePointRsp(session.getPlayer(), req.getSceneId()));
    }
}
