package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskDataNotifyOuterClass;

public class PacketDailyTaskDataNotify extends BasePacket {
    public PacketDailyTaskDataNotify() {
        super(PacketOpcodes.DailyTaskDataNotify);

        //Test
        var notify = DailyTaskDataNotifyOuterClass.DailyTaskDataNotify.newBuilder()
            .setFinishedNum(0)
            .setScoreRewardId(2012)
            .build();

        this.setData(notify);
    }
}
