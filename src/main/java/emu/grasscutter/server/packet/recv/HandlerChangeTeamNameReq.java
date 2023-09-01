package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ChangeTeamNameReqOuterClass.ChangeTeamNameReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ChangeTeamNameReq)
public class HandlerChangeTeamNameReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChangeTeamNameReq req = ChangeTeamNameReq.parseFrom(payload);

        session.getPlayer().getTeamManager().setTeamName(req.getTeamId(), req.getTeamName());
    }
}
