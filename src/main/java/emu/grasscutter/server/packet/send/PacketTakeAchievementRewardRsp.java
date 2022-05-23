package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakeAchievementRewardRspOuterClass.TakeAchievementRewardRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;

import java.util.List;

public class PacketTakeAchievementRewardRsp extends BasePacket {

    public PacketTakeAchievementRewardRsp(List<Integer> idList, List<ItemParam> items) {
        super(PacketOpcodes.TakeAchievementRewardRsp);
        TakeAchievementRewardRsp proto = TakeAchievementRewardRsp.newBuilder()
                .addAllIdList(idList)
                .addAllItemList(items)
                .build();

        this.setData(proto);
    }
}
