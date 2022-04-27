package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerNameRspOuterClass.SetPlayerNameRsp;

public class PacketSetPlayerNameRsp extends BasePacket {
	
	public PacketSetPlayerNameRsp(Player player) {
		super(PacketOpcodes.SetPlayerNameRsp);

		SetPlayerNameRsp proto = SetPlayerNameRsp.newBuilder()
				.setNickName(player.getNickname())
				.build();
		
		this.setData(proto);
	}
}
