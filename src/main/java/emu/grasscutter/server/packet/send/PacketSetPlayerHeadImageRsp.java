package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HeadImageOuterClass.HeadImage;
import emu.grasscutter.net.proto.SetPlayerHeadImageRspOuterClass.SetPlayerHeadImageRsp;

public class PacketSetPlayerHeadImageRsp extends BasePacket {
	
	public PacketSetPlayerHeadImageRsp(Player player) {
		super(PacketOpcodes.SetPlayerHeadImageRsp);
		
		SetPlayerHeadImageRsp proto = SetPlayerHeadImageRsp.newBuilder()
				.setAvatarId(HeadImage.newBuilder().setAvatarId(player.getHeadImage()).getAvatarId())
				.build();
		
		this.setData(proto);
	}
}
