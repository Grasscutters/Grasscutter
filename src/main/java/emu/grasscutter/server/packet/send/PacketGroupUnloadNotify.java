package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GroupUnloadNotifyOuterClass;

import java.util.List;

public class PacketGroupUnloadNotify extends BasePacket {

	public PacketGroupUnloadNotify(List<Integer> groupList) {
		super(PacketOpcodes.GroupUnloadNotify);

        var proto = GroupUnloadNotifyOuterClass.GroupUnloadNotify.newBuilder();

        proto.addAllGroupList(groupList);

        this.setData(proto);
	}
}
