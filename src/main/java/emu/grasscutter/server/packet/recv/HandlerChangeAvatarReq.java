package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChangeAvatarReqOuterClass.ChangeAvatarReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ChangeAvatarReq)
public class HandlerChangeAvatarReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChangeAvatarReq req = ChangeAvatarReq.parseFrom(payload);

        session.getPlayer().getTeamManager().changeAvatar(req.getGuid());
    }
}
