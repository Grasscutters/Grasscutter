package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerDataNotifyOuterClass.PlayerDataNotify;
import emu.grasscutter.net.proto.PropValueOuterClass.PropValue;

public class PacketPlayerDataNotify extends GenshinPacket {
	
	public PacketPlayerDataNotify(GenshinPlayer player) {
		super(PacketOpcodes.PlayerDataNotify, 2);
		
		PlayerDataNotify.Builder p = PlayerDataNotify.newBuilder()
				.setNickName(player.getNickname())
				.setClientTime(System.currentTimeMillis())
				.setIsFirstLoginToday(true)
				.setRegionId(player.getRegionId());
				
		player.getProperties().forEach((key, value) -> {
			p.putPropMap(key, PropValue.newBuilder().setType(key).setIval(value).setVal(value).build());
		});

		this.setData(p.build());
	}
}
