package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeBattlePassMissionPointReqOuterClass.TakeBattlePassMissionPointReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassMissionPointRsp;

@Opcodes(PacketOpcodes.TakeBattlePassMissionPointReq)
public class HandlerTakeBattlePassMissionPointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TakeBattlePassMissionPointReq.parseFrom(payload);

        session.getPlayer().getBattlePassManager().takeMissionPoint(req.getMissionIdListList());

        session.send(new PacketTakeBattlePassMissionPointRsp());
    }
}
