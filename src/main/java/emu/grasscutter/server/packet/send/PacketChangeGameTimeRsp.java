package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.Player;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeGameTimeRspOuterClass.ChangeGameTimeRsp;

public class PacketChangeGameTimeRsp extends BasePacket {
	
	public PacketChangeGameTimeRsp(Player player) {
		super(PacketOpcodes.ChangeGameTimeRsp);
		
		ChangeGameTimeRsp proto = ChangeGameTimeRsp.newBuilder()
				.setCurGameTime(player.getScene().getTime())
				.build();
		
		this.setData(proto);
	}
}
