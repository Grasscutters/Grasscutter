package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldPlayerInfoNotifyOuterClass.WorldPlayerInfoNotify;

public class PacketWorldPlayerInfoNotify extends GenshinPacket {
	
	public PacketWorldPlayerInfoNotify(World world) {
		super(PacketOpcodes.WorldPlayerInfoNotify);
		
		WorldPlayerInfoNotify.Builder proto = WorldPlayerInfoNotify.newBuilder();
		
		for (int i = 0; i < world.getPlayers().size(); i++) {
			GenshinPlayer p = world.getPlayers().get(i);
			
			proto.addPlayerInfoList(p.getOnlinePlayerInfo());
			proto.addPlayerUidList(p.getUid());
		}
		
		this.setData(proto.build());
	}
}
