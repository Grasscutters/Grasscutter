package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.activity.ActivityConfigItem;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.utils.helpers.DateHelper;
import java.util.Collection;

public class PacketActivityScheduleInfoNotify extends BasePacket {

    public PacketActivityScheduleInfoNotify(Collection<ActivityConfigItem> activityConfigItemList) {
        super(PacketOpcodes.ActivityScheduleInfoNotify);

        var proto = ActivityScheduleInfoNotifyOuterClass.ActivityScheduleInfoNotify.newBuilder();

        activityConfigItemList.forEach(
                item -> {
                    proto.addActivityScheduleList(
                            ActivityScheduleInfoOuterClass.ActivityScheduleInfo.newBuilder()
                                    .setActivityId(item.getActivityId())
                                    .setScheduleId(item.getScheduleId())
                                    .setIsOpen(true)
                                    .setBeginTime(DateHelper.getUnixTime(item.getBeginTime()))
                                    .setEndTime(DateHelper.getUnixTime(item.getEndTime()))
                                    .build());
                });

        this.setData(proto);
    }
}
