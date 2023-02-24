package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemParamOuterClass;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.TakeAchievementGoalRewardRspOuterClass;

import java.util.List;

public class PacketTakeAchievementGoalRewardRsp extends BasePacket {
    public PacketTakeAchievementGoalRewardRsp() {
        super(PacketOpcodes.TakeAchievementGoalRewardRsp);
        this.setData(TakeAchievementGoalRewardRspOuterClass.TakeAchievementGoalRewardRsp.newBuilder()
            .setRetcode(RetcodeOuterClass.Retcode.RET_REWARD_HAS_TAKEN_VALUE)
            .build());
    }

    public PacketTakeAchievementGoalRewardRsp(List<Integer> ids, List<ItemParamOuterClass.ItemParam> items) {
        super(PacketOpcodes.TakeAchievementGoalRewardRsp);

        var rsp = TakeAchievementGoalRewardRspOuterClass.TakeAchievementGoalRewardRsp.newBuilder()
            .addAllIdList(ids)
            .addAllItemList(items)
            .build();

        this.setData(rsp);
    }
}
