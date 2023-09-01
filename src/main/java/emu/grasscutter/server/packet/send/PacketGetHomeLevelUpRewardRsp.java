package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetHomeLevelUpRewardRspOuterClass.GetHomeLevelUpRewardRsp;

public class PacketGetHomeLevelUpRewardRsp extends BasePacket {

    public PacketGetHomeLevelUpRewardRsp(int level, int rewardId) {
        super(PacketOpcodes.GetHomeLevelUpRewardRsp);

        int retcode = 0;

        if (rewardId == 0) {
            retcode = 1;
        }

        GetHomeLevelUpRewardRsp proto =
                GetHomeLevelUpRewardRsp.newBuilder().setLevel(level).setRetcode(retcode).build();

        this.setData(proto);
    }
}
