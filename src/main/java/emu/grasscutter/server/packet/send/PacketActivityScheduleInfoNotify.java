package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.activity.ActivityConfigItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ActivityScheduleInfoNotifyOuterClass;
import emu.grasscutter.net.proto.ActivityScheduleInfoOuterClass;
import emu.grasscutter.utils.DateHelper;

import java.util.Collection;

public class PacketActivityScheduleInfoNotify extends BasePacket {

	public PacketActivityScheduleInfoNotify(Collection<ActivityConfigItem> activityConfigItemList) {
		super(PacketOpcodes.ActivityScheduleInfoNotify);

		var proto = ActivityScheduleInfoNotifyOuterClass.ActivityScheduleInfoNotify.newBuilder();

        activityConfigItemList.forEach(item -> {
            proto.addActivityScheduleList(ActivityScheduleInfoOuterClass.ActivityScheduleInfo.newBuilder()
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
