package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ActivityInfoNotifyOuterClass;
import emu.grasscutter.net.proto.ActivityInfoOuterClass;

public class PacketActivityInfoNotify extends BasePacket {

	public PacketActivityInfoNotify(ActivityInfoOuterClass.ActivityInfo activityInfo) {
		super(PacketOpcodes.ActivityInfoNotify);

        var proto = ActivityInfoNotifyOuterClass.ActivityInfoNotify.newBuilder();

        proto.setActivityInfo(activityInfo);

        this.setData(proto);
	}
}
