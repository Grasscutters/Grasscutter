package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ServerTimeNotifyOuterClass.ServerTimeNotify;

public class PacketServerTimeNotify extends GenshinPacket {
	
	public PacketServerTimeNotify() {
		super(PacketOpcodes.ServerTimeNotify);

		ServerTimeNotify proto = ServerTimeNotify.newBuilder()
				.setServerTime(System.currentTimeMillis())
				.build();
		
		this.setData(proto);
	}
}
