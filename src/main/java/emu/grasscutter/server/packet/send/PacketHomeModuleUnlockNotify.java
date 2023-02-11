package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeModuleUnlockNotifyOuterClass.HomeModuleUnlockNotify;

public class PacketHomeModuleUnlockNotify extends BasePacket {
	
	public PacketHomeModuleUnlockNotify(int targetModuleId) {
		super(PacketOpcodes.HomeModuleUnlockNotify);

		HomeModuleUnlockNotify proto = HomeModuleUnlockNotify.newBuilder()
				.setModuleId(targetModuleId)
				.build();
		
		this.setData(proto);
	}
}
