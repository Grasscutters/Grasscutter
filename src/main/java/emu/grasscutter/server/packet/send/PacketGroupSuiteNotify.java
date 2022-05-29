package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityNPC;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GroupSuiteNotifyOuterClass;

import java.util.List;

public class PacketGroupSuiteNotify extends BasePacket {

	public PacketGroupSuiteNotify(int groupid,int suite) {
		super(PacketOpcodes.GroupSuiteNotify);
		var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();
	    proto.putGroupMap(groupid,suite);
		this.setData(proto);
	}

	/**
	 * control which npc suite is loaded
	 */
	public PacketGroupSuiteNotify(List<EntityNPC> list) {
		super(PacketOpcodes.GroupSuiteNotify);

		var proto = GroupSuiteNotifyOuterClass.GroupSuiteNotify.newBuilder();

		list.forEach(item -> proto.putGroupMap(item.getGroupId(), item.getSuiteId()));

		this.setData(proto);

	}
}
