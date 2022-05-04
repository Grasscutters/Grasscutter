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

        List<AchievementInfo> a_list = new ArrayList<>();
        a_list.add(AchievementInfo.newBuilder().setId(82044).setStatusValue(2).setCurrent(0).setGoal(1).build());
        a_list.add(AchievementInfo.newBuilder().setId(81205).setStatusValue(2).setCurrent(0).setGoal(1).build());


        TakeAchievementRewardReq proto = TakeAchievementRewardReq.newBuilder()
                .addAllAList(a_list)
                .build();

        this.setData(proto);
    }

}