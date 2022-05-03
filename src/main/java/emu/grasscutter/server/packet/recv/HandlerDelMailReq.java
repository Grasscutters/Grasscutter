package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DelMailReqOuterClass;
import emu.grasscutter.net.proto.DeleteFriendReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDelMailRsp;

@Opcodes(PacketOpcodes.DelMailReq)
public class HandlerDelMailReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        DelMailReqOuterClass.DelMailReq req = DelMailReqOuterClass.DelMailReq.parseFrom(payload);
        
        session.getPlayer().getMailHandler().deleteMail(req.getMailIdListList());
    }

}
