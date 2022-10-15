package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskInfoOuterClass;
import emu.grasscutter.net.proto.DailyTaskProgressNotifyOuterClass;

public class PacketDailyTaskProgressNotify extends BasePacket {
    public PacketDailyTaskProgressNotify(DailyTaskInfoOuterClass.DailyTaskInfo info) {
        super(PacketOpcodes.DailyTaskProgressNotify);

        var notify = DailyTaskProgressNotifyOuterClass.DailyTaskProgressNotify.newBuilder()
            .setInfo(info)
            .build();

        this.setData(notify);
    }
}
