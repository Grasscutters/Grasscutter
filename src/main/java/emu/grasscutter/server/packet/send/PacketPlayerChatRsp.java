package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerChatRspOuterClass.PlayerChatRsp;

public class PacketPlayerChatRsp extends GenshinPacket {
	
	public PacketPlayerChatRsp() {
		super(PacketOpcodes.PlayerChatRsp);
		
		PlayerChatRsp proto = PlayerChatRsp.newBuilder().build();
		
		this.setData(proto);
	}
}
