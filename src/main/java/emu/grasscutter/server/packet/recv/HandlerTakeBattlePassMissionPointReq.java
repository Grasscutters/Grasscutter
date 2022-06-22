package emu.grasscutter.server.packet.recv;


import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeBattlePassMissionPointReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBattlePassCurScheduleUpdateNotify;
import emu.grasscutter.server.packet.send.PacketBattlePassMissionUpdateNotify;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassMissionPointRsp;

@Opcodes(PacketOpcodes.TakeBattlePassMissionPointReq)
public class HandlerTakeBattlePassMissionPointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req
                = TakeBattlePassMissionPointReqOuterClass.TakeBattlePassMissionPointReq.parseFrom(payload);

        session.send(new PacketBattlePassMissionUpdateNotify(req.getMissionIdListList() , session));
        session.send(new PacketBattlePassCurScheduleUpdateNotify(session.getPlayer()));
        session.send(new PacketTakeBattlePassMissionPointRsp());
    }
}
