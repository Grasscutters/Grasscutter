package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.GetAllMailReqOuterClass;
import emu.grasscutter.net.proto.GetPlayerTokenReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetAllMailRsp;
import emu.grasscutter.server.packet.send.PacketGetGachaInfoRsp;

@Opcodes(PacketOpcodes.GetAllMailReq)
public class HandlerGetAllMailReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetAllMailReqOuterClass.GetAllMailReq req = GetAllMailReqOuterClass.GetAllMailReq.parseFrom(payload);
        session.send(new PacketGetAllMailRsp(session.getPlayer(), req.getUnk2700OPEHLDAGICF()));
    }
}
