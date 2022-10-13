package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DailyTaskInfoOuterClass;
import emu.grasscutter.net.proto.WorldOwnerDailyTaskNotifyOuterClass;

public class PacketWorldOwnerDailyTaskNotify extends BasePacket {
    public PacketWorldOwnerDailyTaskNotify() {
        super(PacketOpcodes.WorldOwnerDailyTaskNotify);

        var notify = WorldOwnerDailyTaskNotifyOuterClass.WorldOwnerDailyTaskNotify.newBuilder();

        //Test - from my today's daily tasks.
        notify.addTaskList(DailyTaskInfoOuterClass.DailyTaskInfo.newBuilder()
                .setRewardId(2112)
                .setDailyTaskId(32351)
                .setFinishProgress(1)
            .build());
        notify.addTaskList(DailyTaskInfoOuterClass.DailyTaskInfo.newBuilder()
            .setRewardId(2112)
            .setDailyTaskId(32362)
            .setFinishProgress(1)
            .build());
        notify.addTaskList(DailyTaskInfoOuterClass.DailyTaskInfo.newBuilder()
            .setRewardId(2112)
            .setDailyTaskId(32381)
            .setFinishProgress(1)
            .build());
        notify.addTaskList(DailyTaskInfoOuterClass.DailyTaskInfo.newBuilder()
            .setRewardId(2112)
            .setDailyTaskId(42080)
            .setFinishProgress(1)
            .build());
        notify.setFilterCityId(4);

        this.setData(notify.build());
    }
}
