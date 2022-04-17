package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerNameRspOuterClass.SetPlayerNameRsp;

public class PacketSetPlayerNameRsp extends GenshinPacket {
	
	public PacketSetPlayerNameRsp(GenshinPlayer player) {
		super(PacketOpcodes.SetPlayerNameRsp);

		SetPlayerNameRsp proto = SetPlayerNameRsp.newBuilder()
				.setNickName(player.getNickname())
				.build();
		
		this.setData(proto);
	}
}
