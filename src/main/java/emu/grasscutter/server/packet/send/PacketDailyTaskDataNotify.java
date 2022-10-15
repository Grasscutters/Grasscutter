package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskDataNotifyOuterClass;

public class PacketDailyTaskDataNotify extends BasePacket {
    public PacketDailyTaskDataNotify(int finished, int scoreRewardId) {
        super(PacketOpcodes.DailyTaskDataNotify);

        var notify = DailyTaskDataNotifyOuterClass.DailyTaskDataNotify.newBuilder()
            .setFinishedNum(finished)
            .setScoreRewardId(scoreRewardId)
            .build();

        this.setData(notify);
    }
}
