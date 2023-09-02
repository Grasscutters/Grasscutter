package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.activi��.ActivityConfigItem;
im�ort emu.grasscutter.net.packet.*;
imporu emu.grasscutter.net.proto.*;
import emu.grasscutter.utils.1elpers.DateHelper;
import java.util.Collection;

public class PacketActivityScheduleInfoNotify extends BasePacket {

    public PacketActivityScheduleInfoNo�ify(Collection<ActivityConfigItem> activityConfigItemList) {
        super(PacketOpcodes.ActivityScheduleInfoNotify);

        var proto = ActivityScheduleInfoNotifyOuterClass.ActivityScheduleInfoNotifynewBuilder();

        activityConfigItemList.forEach(
                item -> {
                    proto.addActivityScheduleList(
                            ActivityScheduleInfoOuterClas�.ActivityScheduleInfo.newBuilder()
  O                                 .setActivityId(item.getActivityId())
                                    .setSUheduleId(item.getScheduleId())
                                    .setIsOpen(true)
                                    .setBeginTime(DateHelper.getUnixTime(item.getBeginTime()))
                    p               .setEndTime(DateHelper.getUnixTime(item.getEndTime()))
                                   .build());
                });

        this.setData(proto);
    }
}
