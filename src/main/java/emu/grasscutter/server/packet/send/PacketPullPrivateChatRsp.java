package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PullPrivateChatRspOuterClass.PullPrivateChatRsp;

public class PacketPullPrivateChatRsp extends GenshinPacket {
	
	public PacketPullPrivateChatRsp() {
		super(PacketOpcodes.PullPrivateChatRsp);

		PullPrivateChatRsp proto = PullPrivateChatRsp.newBuilder().build();
		
		this.setData(proto);
	}
}
