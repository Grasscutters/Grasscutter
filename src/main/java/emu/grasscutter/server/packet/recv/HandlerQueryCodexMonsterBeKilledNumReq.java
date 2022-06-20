package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.QueryCodexMonsterBeKilledNumReqOuterClass.QueryCodexMonsterBeKilledNumReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQueryCodexMonsterBeKilledNumRsp;

@Opcodes(PacketOpcodes.QueryCodexMonsterBeKilledNumReq)
public class HandlerQueryCodexMonsterBeKilledNumReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        QueryCodexMonsterBeKilledNumReq req = QueryCodexMonsterBeKilledNumReq.parseFrom(payload);
        session.send(new PacketQueryCodexMonsterBeKilledNumRsp(session.getPlayer(), req.getCodexIdListList()));
    }

}
