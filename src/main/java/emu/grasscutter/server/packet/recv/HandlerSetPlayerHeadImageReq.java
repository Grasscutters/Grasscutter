package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerHeadImageReqOuterClass.SetPlayerHeadImageReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetPlayerHeadImageRsp;

@Opcodes(PacketOpcodes.SetPlayerHeadImageReq)
public class HandlerSetPlayerHeadImageReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SetPlayerHeadImageReq req = SetPlayerHeadImageReq.parseFrom(payload);

        int id = req.getAvatarId();

        if (session.getPlayer().getAvatars().hasAvatar(id)) {
            session.getPlayer().setHeadImage(id);
            session.send(new PacketSetPlayerHeadImageRsp(session.getPlayer()));
        }
    }
}
