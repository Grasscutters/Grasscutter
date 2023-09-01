package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.AvatarChangeCostumeReqOuterClass.AvatarChangeCostumeReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarChangeCostumeRsp;

@Opcodes(PacketOpcodes.AvatarChangeCostumeReq)
public class HandlerAvatarChangeCostumeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        AvatarChangeCostumeReq req = AvatarChangeCostumeReq.parseFrom(payload);

        boolean success =
                session.getPlayer().getAvatars().changeCostume(req.getAvatarGuid(), req.getCostumeId());

        if (success) {
            session
                    .getPlayer()
                    .sendPacket(new PacketAvatarChangeCostumeRsp(req.getAvatarGuid(), req.getCostumeId()));
        } else {
            session.getPlayer().sendPacket(new PacketAvatarChangeCostumeRsp());
        }
    }
}
