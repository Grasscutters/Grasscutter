package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerTimeNotifyOuterClass.PlayerTimeNotify;

public class PacketPlayerTimeNotify extends GenshinPacket {
	
	public PacketPlayerTimeNotify(GenshinPlayer player) {
		super(PacketOpcodes.PlayerTimeNotify);

		PlayerTimeNotify proto = PlayerTimeNotify.newBuilder()
				.setIsPaused(player.isPaused())
				.setPlayerTime(player.getClientTime())
				.setServerTime(System.currentTimeMillis())
				.build();
		
		this.setData(proto);
	}
}
