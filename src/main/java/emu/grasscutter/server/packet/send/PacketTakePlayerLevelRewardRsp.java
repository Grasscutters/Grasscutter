package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TakePlayerLevelRewardRspOuterClass.TakePlayerLevelRewardRsp;

public class PacketTakePlayerLevelRewardRsp extends BasePacket {

    public PacketTakePlayerLevelRewardRsp(int level, int rewardId) {
        super(PacketOpcodes.TakePlayerLevelRewardRsp);

        int retcode = 0;

        if (rewardId == 0) {
            retcode = 1;
        }

        TakePlayerLevelRewardRsp proto = TakePlayerLevelRewardRsp.newBuilder()
            .setLevel(level)
            .setRewardId(rewardId)
            .setRetcode(retcode)
            .build();

        this.setData(proto);
    }
}
