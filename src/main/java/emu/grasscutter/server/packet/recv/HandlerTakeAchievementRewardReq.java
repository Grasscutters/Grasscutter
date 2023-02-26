package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeAchievementRewardReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.TakeAchievementRewardReq)
public class HandlerTakeAchievementRewardReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = TakeAchievementRewardReqOuterClass.TakeAchievementRewardReq.parseFrom(payload);
        session.getPlayer().getAchievements().takeReward(req.getIdListList());
    }
}
