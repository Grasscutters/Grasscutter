package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.TakeAchievementRewardReqOuterClass.TakeAchievementRewardReq;
import emu.grasscutter.server.game.GameSession;

public class PacketTakeAchievementRewardReq extends BasePacket {

    public PacketTakeAchievementRewardReq(GameSession session) {
        super(PacketOpcodes.TakeAchievementRewardReq);

        TakeAchievementRewardReq proto = TakeAchievementRewardReq.newBuilder().build();

        this.setData(proto);
    }
}
