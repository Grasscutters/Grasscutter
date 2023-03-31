package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChooseCurAvatarTeamReqOuterClass.ChooseCurAvatarTeamReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ChooseCurAvatarTeamReq)
public class HandlerChooseCurAvatarTeamReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ChooseCurAvatarTeamReq req = ChooseCurAvatarTeamReq.parseFrom(payload);

        session.getPlayer().getTeamManager().setCurrentTeam(req.getTeamId());
    }

}
