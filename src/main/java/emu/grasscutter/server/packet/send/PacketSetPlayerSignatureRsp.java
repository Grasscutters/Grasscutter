package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetPlayerSignatureRspOuterClass.SetPlayerSignatureRsp;

public class PacketSetPlayerSignatureRsp extends GenshinPacket {
	
	public PacketSetPlayerSignatureRsp(GenshinPlayer player) {
		super(PacketOpcodes.SetPlayerSignatureRsp);

		SetPlayerSignatureRsp proto = SetPlayerSignatureRsp.newBuilder()
				.setSignature(player.getSignature())
				.build();
		
		this.setData(proto);
	}
}
