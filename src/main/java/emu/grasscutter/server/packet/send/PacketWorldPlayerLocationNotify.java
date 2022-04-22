package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.game.World;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WorldPlayerLocationNotifyOuterClass.WorldPlayerLocationNotify;

public class PacketWorldPlayerLocationNotify extends GenshinPacket {
	
	public PacketWorldPlayerLocationNotify(World world) {
		super(PacketOpcodes.WorldPlayerLocationNotify);

		WorldPlayerLocationNotify.Builder proto = WorldPlayerLocationNotify.newBuilder();
		
		for (GenshinPlayer p : world.getPlayers()) {
			proto.addPlayerLocList(p.getWorldPlayerLocationInfo());
		}
		
		this.setData(proto);
	}
}
