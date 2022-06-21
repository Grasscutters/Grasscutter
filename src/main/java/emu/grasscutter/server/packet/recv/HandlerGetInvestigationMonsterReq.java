package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetInvestigationMonsterReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetInvestigationMonsterRsp;

@Opcodes(PacketOpcodes.GetInvestigationMonsterReq)
public class HandlerGetInvestigationMonsterReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetInvestigationMonsterReqOuterClass.GetInvestigationMonsterReq.parseFrom(payload);

        session.send(new PacketGetInvestigationMonsterRsp(
            session.getPlayer(),
            session.getServer().getWorldDataManager(),
            req.getCityIdListList()));

    }

}
