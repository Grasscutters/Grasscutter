package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetSceneAreaReqOuterClass.GetSceneAreaReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetSceneAreaRsp;

@Opcodes(PacketOpcodes.GetSceneAreaReq)
public class HandlerGetSceneAreaReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetSceneAreaReq req = GetSceneAreaReq.parseFrom(payload);

        session.send(new PacketGetSceneAreaRsp(req.getSceneId()));
    }

}
