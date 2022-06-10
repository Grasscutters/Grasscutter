package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityNPC;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GroupSuiteNotifyOuterClass;

import java.util.List;

public class PacketGroupSuiteNotify extends BasePacket {

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
