package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DeleteFriendNotifyOuterClass.DeleteFriendNotify;

public class PacketDeleteFriendNotify extends BasePacket {
	
	public PacketDeleteFriendNotify(int targetUid) {
		super(PacketOpcodes.DeleteFriendNotify);

		DeleteFriendNotify proto = DeleteFriendNotify.newBuilder()
				.setTargetUid(targetUid)
				.build();
		
		this.setData(proto);
	}
}
