package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerGetForceQuitBanInfoRspOuterClass.PlayerGetForceQuitBanInfoRsp;

public class PacketPlayerGetForceQuitBanInfoRsp extends GenshinPacket {
	
	public PacketPlayerGetForceQuitBanInfoRsp(int retcode) {
		super(PacketOpcodes.PlayerGetForceQuitBanInfoRsp);

		PlayerGetForceQuitBanInfoRsp proto = PlayerGetForceQuitBanInfoRsp.newBuilder()
				.setRetcode(retcode)
				.build();
		
		this.setData(proto);
	}
}
