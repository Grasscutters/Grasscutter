package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AchievementInfoOuterClass;
import emu.grasscutter.net.proto.AchievementInfoOuterClass.AchievementInfo;
import emu.grasscutter.net.proto.TakeAchievementRewardReqOuterClass.TakeAchievementRewardReq;
import emu.grasscutter.server.game.GameSession;

import java.util.ArrayList;
import java.util.List;

public class PacketTakeAchievementRewardReq extends BasePacket {

    public PacketTakeAchievementRewardReq(GameSession session) {
        super(PacketOpcodes.TakeAchievementRewardReq);

        TakeAchievementRewardReq proto = TakeAchievementRewardReq.newBuilder().build();

        this.setData(proto);
    }

}