package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterMpNotifyOuterClass.PlayerApplyEnterMpNotify;

public class PacketPlayerApplyEnterMpNotify extends GenshinPacket {
	
	public PacketPlayerApplyEnterMpNotify(GenshinPlayer srcPlayer) {
		super(PacketOpcodes.PlayerApplyEnterMpNotify);

		PlayerApplyEnterMpNotify proto = PlayerApplyEnterMpNotify.newBuilder()
				.setSrcPlayerInfo(srcPlayer.getOnlinePlayerInfo())
				.build();
		
		this.setData(proto);
	}
}
