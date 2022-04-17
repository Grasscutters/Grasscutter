package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;
import emu.grasscutter.net.proto.SetPlayerHeadImageRspOuterClass.SetPlayerHeadImageRsp;

public class PacketSetPlayerHeadImageRsp extends GenshinPacket {
	
	public PacketSetPlayerHeadImageRsp(GenshinPlayer player) {
		super(PacketOpcodes.SetPlayerHeadImageRsp);
		
		SetPlayerHeadImageRsp proto = SetPlayerHeadImageRsp.newBuilder()
				.setAvatar(HeadImage.newBuilder().setAvatarId(player.getHeadImage()))
				.build();
		
		this.setData(proto);
	}
}
