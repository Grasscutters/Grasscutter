package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeGameTimeRspOuterClass.ChangeGameTimeRsp;

public class PacketChangeGameTimeRsp extends GenshinPacket {
	
	public PacketChangeGameTimeRsp(GenshinPlayer player) {
		super(PacketOpcodes.ChangeGameTimeRsp);
		
		ChangeGameTimeRsp proto = ChangeGameTimeRsp.newBuilder()
				.setCurGameTime(player.getScene().getTime())
				.build();
		
		this.setData(proto);
	}
}
