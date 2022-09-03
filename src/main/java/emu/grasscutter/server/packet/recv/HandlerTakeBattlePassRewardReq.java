package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeBattlePassRewardReqOuterClass.TakeBattlePassRewardReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTakeBattlePassRewardRsp;

@Opcodes(PacketOpcodes.TakeBattlePassRewardReq)
public class HandlerTakeBattlePassRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TakeBattlePassRewardReq.parseFrom(payload);

        session.getPlayer().getBattlePassManager().takeReward(req.getTakeOptionListList());
    }
}
