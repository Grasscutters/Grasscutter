package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAllUnlockNameCardRspOuterClass.GetAllUnlockNameCardRsp;

public class PacketGetAllUnlockNameCardRsp extends GenshinPacket {
	
	public PacketGetAllUnlockNameCardRsp(GenshinPlayer player) {
		super(PacketOpcodes.GetAllUnlockNameCardRsp);
		
		GetAllUnlockNameCardRsp proto = GetAllUnlockNameCardRsp.newBuilder()
				.addAllNameCardList(player.getNameCardList())
				.build();

		this.setData(proto);
	}
}
