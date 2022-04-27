package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockNameCardNotifyOuterClass.UnlockNameCardNotify;

public class PacketUnlockNameCardNotify extends BasePacket {
	
	public PacketUnlockNameCardNotify(int nameCard) {
		super(PacketOpcodes.UnlockNameCardNotify);

		UnlockNameCardNotify proto = UnlockNameCardNotify.newBuilder()
				.setNameCardId(nameCard)
				.build();
		
		this.setData(proto);
	}
}
